package com.platon.datum.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.entity.AttributeDataToken;
import com.platon.datum.admin.service.AttributeDataTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/13 17:43
 * @Version
 * @Desc
 */


@Service
public class AttributeDataTokenServiceImpl implements AttributeDataTokenService {

    @Resource
    private AttributeDataTokenMapper attributeDataTokenMapper;

    /**
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @param currentUserAddress
     * @return
     */
    @Override
    public Page<AttributeDataToken> page(Integer pageNumber, Integer pageSize, String keyword, String currentUserAddress) {
        Page<AttributeDataToken> dataTokenPage = PageHelper.startPage(pageNumber, pageSize);
        attributeDataTokenMapper.selectListByKeywordAndUser(keyword, currentUserAddress);
        return dataTokenPage;
    }

    /**
     * @param dataToken
     * @return
     */
    @Override
    public Integer publish(AttributeDataToken dataToken) {
        //校验dataToken是否已存在并处于发布中状态
        AttributeDataToken oldDataToken = attributeDataTokenMapper.selectByMetaDataId(dataToken.getMetaDataDbId());
        if (oldDataToken != null) {
            if (oldDataToken.getStatus() == AttributeDataToken.StatusEnum.PUBLISHING.getStatus()) {
                throw new BizException(Errors.DataTokenInPublishing);
            }
            if (oldDataToken.getStatus() == AttributeDataToken.StatusEnum.PUBLISH_SUCCESS.getStatus()) {
                throw new BizException(Errors.DataTokenExists);
            }
        }
        //1.先插入dataToken数据,获取到dataToken id
        dataToken.setStatus(AttributeDataToken.StatusEnum.PUBLISHING.getStatus());
        attributeDataTokenMapper.insertAndReturnId(dataToken);
        return dataToken.getId();
    }

    /**
     * @param dataTokenId
     * @return
     */
    @Override
    public AttributeDataToken getDataTokenById(Integer dataTokenId, String currentUserAddress) {
        AttributeDataToken dataToken = attributeDataTokenMapper.selectDataTokenById(dataTokenId);
        if (dataToken == null || !StrUtil.equals(currentUserAddress, dataToken.getOwner())) {
            return null;
        }
        return dataToken;
    }

    /**
     * @param dataTokenId
     * @param status
     * @param currentUserAddress
     */
    @Override
    public void updateStatus(Integer dataTokenId, int status, String currentUserAddress) {
        attributeDataTokenMapper.updateStatusByCurrentUser(dataTokenId, status, currentUserAddress);
    }
}
