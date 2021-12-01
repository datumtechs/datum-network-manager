package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.entity.DataSync;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 数据授权申请定时任务
 * 定期从调度服务获取和本组织有关的数据授权申请。
 *
 * 数据授权，涉及FLow(提出、撤回授权申请），调度服务，Admin（审批授权申请），Flow在撤回授权申请，Admin在同意授权申请时，都要以调度服务返回的结果为准。
 * 1. 管理台只能对处于pending状态的申请进行审批；
 * 2. 对已经同意的授权申请，也可以提前终止授权（todo:需要有前提吗？比如没有任务正在使用）
 *
 */
@Slf4j
@Configuration
public class LocalDataAuthReqRefreshTask {

    @Resource
    private AuthClient authClient;
    @Resource
    private LocalDataAuthMapper dataAuthMapper;
    @Resource
    private DataSyncService dataSyncService;

    @Transactional
    @Scheduled(fixedDelayString = "${SyncLocalDataAuthReqTask.fixedDelay}")
    public void task() {
        log.debug("刷新数据授权申请定时任务开始>>>");
        while(true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.DataAuthReq);

            List<LocalDataAuth> dataAuthList = authClient.getMetaDataAuthorityList(dataSync.getLatestSynced());
            if(CollectionUtils.isEmpty(dataAuthList)){
                break;
            }

            dataAuthMapper.replaceBatch(dataAuthList);

            LocalDataAuth localDataAuth = dataAuthList.get(dataAuthList.size() - 1);
            dataSync.setLatestSynced(localDataAuth.getRecUpdateTime());
            //把最近更新时间update到数据库
            dataSyncService.updateDataSync(dataSync);

            log.debug("本次跟新数据授权申请数量：{}", dataAuthList.size());
            if(dataAuthList.size()<10){
                break;
            }
        }
        log.debug("刷新数据授权申请定时任务结束|||");
    }
}
