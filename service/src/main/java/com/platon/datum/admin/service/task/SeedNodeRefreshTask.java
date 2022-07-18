package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.SeedNodeMapper;
import com.platon.datum.admin.dao.entity.SeedNode;
import com.platon.datum.admin.grpc.client.SeedClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houz
 * @Description 计算节点定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
@Configuration
public class SeedNodeRefreshTask {

    @Resource
    private SeedNodeMapper seedNodeMapper;

    @Resource
    private SeedClient seedClient;

    /**
     * 1.定时刷新种子节点信息, 间隔30秒执行下一次任务
     */
    //@Scheduled(fixedDelay = 30000)
    @Scheduled(fixedDelayString = "${SeedNodeRefreshTask.fixedDelay}")
    public void refreshSeedNode(){
        log.debug("刷新种子节点定时任务开始>>>");

        List<SeedNode> seedNodeList = seedClient.getSeedNodeList();
        //localSeedNodeMapper.deleteNotInitialized();

        if(CollectionUtils.isNotEmpty(seedNodeList)){
            log.debug("本次更新种子节点数量：{}", seedNodeList.size());
            seedNodeMapper.insertBatch(seedNodeList);
        }
        log.info("刷新种子节点定时任务结束|||");
    }
}
