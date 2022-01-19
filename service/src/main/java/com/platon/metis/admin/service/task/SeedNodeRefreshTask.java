package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalSeedNodeMapper;
import com.platon.metis.admin.dao.entity.LocalSeedNode;
import com.platon.metis.admin.grpc.client.SeedClient;
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
    private LocalSeedNodeMapper localSeedNodeMapper;

    @Resource
    private SeedClient seedClient;

    /**
     * 1.定时刷新种子节点信息, 间隔30秒执行下一次任务
     */
    //@Scheduled(fixedDelay = 30000)
    @Scheduled(fixedDelayString = "${SeedNodeRefreshTask.fixedDelay}")
    public void refreshSeedNode(){
        log.debug("刷新种子节点定时任务开始>>>");

        List<LocalSeedNode> localSeedNodeList = seedClient.getSeedNodeList();
        //localSeedNodeMapper.deleteNotInitialized();

        if(CollectionUtils.isNotEmpty(localSeedNodeList)){
            localSeedNodeMapper.insertBatch(localSeedNodeList);
        }
        log.info("刷新种子节点定时任务结束|||");
    }
}
