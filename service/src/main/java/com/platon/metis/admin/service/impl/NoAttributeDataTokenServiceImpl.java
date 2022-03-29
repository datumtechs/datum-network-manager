package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.dao.DataTokenMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.DataToken;
import com.platon.metis.admin.service.NoAttributeDataTokenService;
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
    public Page<DataToken> page(Integer pageNumber, Integer pageSize, int status) {
        Page<DataToken> dataTokenPage = PageHelper.startPage(pageNumber, pageSize);
        dataTokenMapper.selectByPricingStatus(status);
        return dataTokenPage;
    }

    @Transactional
    @Override
    public void publish(DataToken dataToken) {
        //1.先插入dataToken数据,获取到dataToken id
        dataToken.setPublishStatus(DataToken.PublishStatusEnum.PUBLISHING.getStatus());//发布中
        dataToken.setPricingStatus(DataToken.PricingStatusEnum.UNPRICED.getStatus());//未定价
        int dataTokenId = dataTokenMapper.insertAndReturnId(dataToken);
        //2.将token id 绑定到meta data中
        localMetaDataMapper.updateDataTokenIdById(dataToken.getMetaDataId(),dataTokenId);
    }

    @Override
    public void up(DataToken dataToken) {
        //1.更新数据的定价状态
        dataToken.setPricingStatus(DataToken.PricingStatusEnum.PRICING.getStatus());//定价中
        dataTokenMapper.updatePricingStatus(dataToken);
    }
}
