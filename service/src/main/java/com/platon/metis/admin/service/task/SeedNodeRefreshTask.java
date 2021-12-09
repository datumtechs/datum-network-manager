package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalSeedNodeMapper;
import com.platon.metis.admin.dao.entity.LocalSeedNode;
import com.platon.metis.admin.grpc.client.SeedClient;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    public void task(){
        log.debug("定时刷新种子节点信息...");
        try {
            List<YarnRpcMessage.SeedPeer> seedPeerList = seedClient.getJobNodeList();
            localSeedNodeMapper.truncate();
            if (CollectionUtils.isNotEmpty(seedPeerList)) {
                List<LocalSeedNode> localSeedNodeList = seedPeerList.parallelStream().map(seedNode -> {
                    LocalSeedNode localSeedNode = new LocalSeedNode();
                    localSeedNode.setSeedNodeId(seedNode.getAddr());
                    localSeedNode.setInitFlag(seedNode.getIsDefault());
                    localSeedNode.setConnStatus(seedNode.getConnStateValue());
                    return localSeedNode;
                }).collect(Collectors.toList());
                localSeedNodeMapper.insertBatch(localSeedNodeList);
            }
        }catch (Throwable e){
            log.error("定时刷新种子节点信息出错", e);
        }
        log.debug("定时刷新种子节点信息结束");
    }
}
