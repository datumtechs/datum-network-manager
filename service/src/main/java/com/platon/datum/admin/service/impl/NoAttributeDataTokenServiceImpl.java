package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.LocalMetaDataMapper;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.datum.admin.service.NoAttributeDataTokenService;
import lombok.extern.slf4j.Slf4j;
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
    private LocalMetaDataMapper localMetaDataMapper;

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
        DataToken oldDataToken = dataTokenMapper.selectByMetaDataId(dataToken.getMetaDataId());
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
        dataTokenMapper.insertAndReturnId(dataToken);
        log.debug("插入dataToken数据,获取到dataToken id:{}", dataToken.getId());


        //2.将token id 绑定到meta data中
        localMetaDataMapper.updateDataTokenIdById(dataToken.getMetaDataId(), dataToken.getId());
        localMetaDataMapper.updateStatusById(dataToken.getMetaDataId(), LocalDataFileStatusEnum.TOKEN_RELEASING.getStatus());
        return dataToken.getId();
    }

    @Override
    public void up(DataToken dataToken) {
        //1.更新数据的定价状态
        dataToken.setStatus(DataToken.StatusEnum.PRICING.getStatus());//定价中
        dataTokenMapper.updatePricingStatus(dataToken);
    }

    @Override
    public DataToken getDataTokenById(Integer dataTokenId) {
        DataToken dataToken = dataTokenMapper.selectById(dataTokenId);
        return dataToken;
    }

    @Override
    public void updateStatus(Integer dataTokenId, int status, String currentUserAddress) {
        dataTokenMapper.updateStatusByCurrentUser(dataTokenId, status, currentUserAddress);
    }
}
