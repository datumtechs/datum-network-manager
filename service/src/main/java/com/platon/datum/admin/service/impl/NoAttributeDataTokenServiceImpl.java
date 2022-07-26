package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.MetaDataMapper;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.grpc.client.MetaDataClient;
import com.platon.datum.admin.service.NoAttributeDataTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author liushuyu
 * @Date 2022/3/24 11:57
 * @Version
 * @Desc
 */


@Service
@Slf4j
public class NoAttributeDataTokenServiceImpl implements NoAttributeDataTokenService {

    @Resource
    private DataTokenMapper dataTokenMapper;
    @Resource
    private MetaDataMapper metaDataMapper;
    @Resource
    private MetaDataClient metaDataClient;

    //消耗量修改时间间隔，单位分钟
    private static final long updateTimeInterval = 60 * 24;

    @Override
    public Page<DataToken> page(Integer pageNumber, Integer pageSize, int status, String address) {
        Page<DataToken> dataTokenPage = PageHelper.startPage(pageNumber, pageSize);
        dataTokenMapper.selectByPricingStatus(status, address);
        return dataTokenPage;
    }

    @Transactional
    @Override
    public Integer publish(DataToken dataToken) {
        //校验dataToken是否已存在并处于发布中状态
        DataToken oldDataToken = dataTokenMapper.selectByMetaDataId(dataToken.getMetaDataDbId());
        if (oldDataToken != null) {
            if (oldDataToken.getStatus() == DataToken.StatusEnum.PUBLISHING.getStatus()) {
                throw new BizException(Errors.DataTokenInPublishing);
            }
            if (oldDataToken.getStatus() == DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus()) {
                throw new BizException(Errors.DataTokenExists);
            }
        }
        //1.先插入dataToken数据,获取到dataToken id
        dataToken.setStatus(DataToken.StatusEnum.PUBLISHING.getStatus());
        dataToken.setFeeUpdateTime(LocalDateTime.now());
        dataTokenMapper.insertAndReturnId(dataToken);
        log.debug("插入dataToken数据,获取到dataToken id:{}", dataToken.getId());
        return dataToken.getId();
    }

    @Override
    public void up(DataToken dataToken) {
        //1.更新数据的定价状态
        dataToken.setStatus(DataToken.StatusEnum.PRICING.getStatus());//定价中
        dataTokenMapper.updatePricingStatus(dataToken);
    }

    @Override
    public DataToken getDataTokenById(Integer id) {
        DataToken dataToken = dataTokenMapper.selectById(id);
        return dataToken;
    }

    @Override
    public void updateToPrinceSuccess(Integer dataTokenId, String currentUserAddress) {
        DataToken dataToken = dataTokenMapper.selectById(dataTokenId);
        if (dataToken == null) {
            throw new BizException(Errors.SysException, "Data token not exist!");
        }

        if (!dataToken.getOwner().equals(currentUserAddress)) {
            throw new BizException(Errors.SysException, "You are not owner!");
        }
        dataTokenMapper.updateStatusByCurrentUser(dataTokenId, DataToken.StatusEnum.PRICE_SUCCESS.getStatus());
    }

    /**
     * @param dataTokenId
     * @param ciphertextFee
     * @param plaintextFee
     * @param currentUserAddress
     */
    @Override
    public void updateFee(Integer dataTokenId, String ciphertextFee, String plaintextFee, String sign, String currentUserAddress) {
        DataToken dataToken = dataTokenMapper.selectById(dataTokenId);
        if (dataToken == null) {
            throw new BizException(Errors.SysException, "Data token not exist!");
        }

        if (!dataToken.getOwner().equals(currentUserAddress)) {
            throw new BizException(Errors.SysException, "You are not owner!");
        }

        dataTokenMapper.updateNewFeeById(dataTokenId, ciphertextFee, plaintextFee);
        //还未绑定的情况下可以任意修改消耗量
        if (dataToken.getStatus() == DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.BIND_FAIL.getStatus()) {
            dataTokenMapper.updateFeeById(dataTokenId, ciphertextFee, plaintextFee);
        } else if (dataToken.getStatus() == DataToken.StatusEnum.BINDING.getStatus()) {
            throw new BizException(Errors.SysException, "Modification is not allowed in binding!");
        } else if (dataToken.getStatus() == DataToken.StatusEnum.BIND_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICING.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICE_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICE_FAIL.getStatus()) {
            //24小时之内只能改一次
            if (dataToken.getFeeUpdateTime().plusMinutes(updateTimeInterval).isAfter(LocalDateTime.now())) {
                throw new BizException(Errors.SysException, "The update time is less than 24 hours!");
            }
            MetaData metaData = metaDataMapper.selectById(dataToken.getMetaDataDbId());
            metaData.setSign(sign);
            metaDataClient.updateMetadata(metaData);
        }
    }

    /**
     * @param dataTokenId
     * @param sign
     * @param currentUserAddress
     */
    @Override
    public void bindMetaData(Integer dataTokenId, String sign, String currentUserAddress) {
        DataToken dataToken = dataTokenMapper.selectById(dataTokenId);
        if (dataToken == null) {
            throw new BizException(Errors.SysException, "Data token not exist!");
        }

        if (!dataToken.getOwner().equals(currentUserAddress)) {
            throw new BizException(Errors.SysException, "You are not owner!");
        }

        MetaData metaData = metaDataMapper.selectById(dataToken.getMetaDataDbId());
        metaData.setSign(sign);
        metaDataClient.updateMetadata(metaData);
        //将dataToken状态修改为绑定中
        dataTokenMapper.updateStatus(dataTokenId, DataToken.StatusEnum.BINDING.getStatus());
    }
}
