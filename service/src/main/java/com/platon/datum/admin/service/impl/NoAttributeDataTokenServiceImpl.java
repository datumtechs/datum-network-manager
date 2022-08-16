package com.platon.datum.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.MetaDataMapper;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.grpc.client.MetaDataClient;
import com.platon.datum.admin.service.NoAttributeDataTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    @Value("${dataToken-updateFee-interval:5}")
    private long updateTimeInterval;

    @Override
    public Page<DataToken> page(Integer pageNumber, Integer pageSize, String keyword, String address) {
        Page<DataToken> dataTokenPage = PageHelper.startPage(pageNumber, pageSize);
        dataTokenMapper.selectListByAddress(keyword, address);
        return dataTokenPage;
    }

    @Transactional(rollbackFor = {Throwable.class})
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
        dataToken.setFeeUpdateTime(LocalDateTimeUtil.now());
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
            throw new BizException(Errors.QueryRecordNotExist, "Data token not exist!");
        }

        if (!dataToken.getOwner().equalsIgnoreCase(currentUserAddress)) {
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
            throw new BizException(Errors.QueryRecordNotExist, "Data token not exist!");
        }

        if (!dataToken.getOwner().equalsIgnoreCase(currentUserAddress)) {
            throw new BizException(Errors.SysException, "You are not owner!");
        }

        dataTokenMapper.updateNewFeeById(dataTokenId, ciphertextFee, plaintextFee);
        //还未绑定的情况下可以任意修改消耗量
        if (dataToken.getStatus() == DataToken.StatusEnum.PUBLISH_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.BIND_FAIL.getStatus()) {
            dataTokenMapper.updateFeeById(dataTokenId, ciphertextFee, plaintextFee);
        } else if (dataToken.getStatus() == DataToken.StatusEnum.BINDING.getStatus()) {
            throw new BizException(Errors.SysException, "Data token already in binding!");
        } else if (dataToken.getStatus() == DataToken.StatusEnum.BIND_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICING.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICE_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICE_FAIL.getStatus()) {
            //24小时之内只能改一次
            if (dataToken.getFeeUpdateTime().plusMinutes(updateTimeInterval).isAfter(LocalDateTimeUtil.now())) {
                throw new BizException(Errors.TimeLessThan24H, StrUtil.format("The update time is less than {} minutes!)",updateTimeInterval));
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

        if (!dataToken.getOwner().equalsIgnoreCase(currentUserAddress)) {
            throw new BizException(Errors.SysException, "You are not owner!");
        }

        if (dataToken.getStatus() == DataToken.StatusEnum.PRICING.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICE_FAIL.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PRICE_SUCCESS.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.BINDING.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.BIND_SUCCESS.getStatus()) {
            throw new BizException(Errors.SysException, "Data token already bind!");
        } else if (dataToken.getStatus() == DataToken.StatusEnum.PUBLISHING.getStatus()
                || dataToken.getStatus() == DataToken.StatusEnum.PUBLISH_FAIL.getStatus()) {
            throw new BizException(Errors.SysException, "Data token haven not published success!");
        }

        MetaData metaData = metaDataMapper.selectById(dataToken.getMetaDataDbId());
        metaData.setSign(sign);
        metaDataClient.updateMetadata(metaData);
        //将dataToken状态修改为绑定中
        dataTokenMapper.updateStatus(dataTokenId, DataToken.StatusEnum.BINDING.getStatus());
    }
}
