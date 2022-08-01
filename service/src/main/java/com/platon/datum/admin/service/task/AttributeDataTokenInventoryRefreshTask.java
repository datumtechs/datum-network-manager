package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.AttributeDataToken;
import com.platon.datum.admin.service.AttributeDataTokenInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author liushuyu
 * @Description 刷新有属性数据凭证库存定时任务
 */
@Slf4j
@Configuration
public class AttributeDataTokenInventoryRefreshTask {

    @Resource
    private AttributeDataTokenMapper attributeDataTokenMapper;

    @Resource
    private AttributeDataTokenInventoryService attributeDataTokenInventoryService;

    /**
     * 刷新inventory总量
     */
    @Scheduled(fixedDelayString = "${AttributeDataTokenStatusRefreshTask.fixedDelay}")
    public void refreshAttributeDataTokenInventoryTotal() {
        if(OrgCache.localOrgNotFound()){
            return;
        }
        log.debug("刷新有属性数据凭证库存定时任务开始>>>");
        //更新发布中的凭证状态
        List<AttributeDataToken> dataTokenList = attributeDataTokenMapper.selectListByStatus(AttributeDataToken.StatusEnum.PUBLISH_SUCCESS.getStatus());
        dataTokenList.forEach(dataToken -> {
            try {
                ((AttributeDataTokenInventoryRefreshTask) AopContext.currentProxy()).processPublishedDataToken(dataToken);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("刷新有属性数据凭证库存定时任务结束|||");
    }

    @Transactional(rollbackFor = {Throwable.class})
    public void processPublishedDataToken(AttributeDataToken dataToken) throws IOException {
        attributeDataTokenInventoryService.refresh(dataToken.getAddress());
    }
}
