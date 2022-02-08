package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.DataSync;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.grpc.client.MetaDataClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;


/**
 * 本地元数据状态更新任务
 * 本地元数据发布，撤消等操作，都要等调度服务的异步响应。
 * 通过定时任务来获取调度服务的异步响应。
 *
 */

@Slf4j
@Configuration
public class LocalMetaDataStatusRefreshTask {

    @Resource
    private DataSyncService dataSyncService;
    @Resource
    private LocalMetaDataMapper localMetaDataMapper;
    @Resource
    private MetaDataClient metaDataClient;

    @Scheduled(fixedDelayString = "${LocalMetaDataStatusRefreshTask.fixedDelay}")
    @Transactional
    public void task() throws Exception {
        log.debug("刷新本地元数据状态定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.LocalMetaData);
            List<LocalMetaData> localMetaDataList = metaDataClient.getLocalMetaDataList(dataSync.getLatestSynced());
            if (CollectionUtils.isEmpty(localMetaDataList)) {
                break;
            }

            log.debug("本次更新元数据状态数量：{}", localMetaDataList.size());

            localMetaDataMapper.updateStatusByMetaDataIdBatch(localMetaDataList);

            LocalMetaData localMetaData = localMetaDataList.stream()
                    .sorted(Comparator.comparing(LocalMetaData::getRecUpdateTime).reversed())
                    .findFirst()
                    .get();
            dataSync.setLatestSynced(localMetaData.getRecUpdateTime());

            //把最近更新时间update到数据库
            dataSyncService.updateDataSync(dataSync);

            if(localMetaDataList.size() < GrpcConstant.PageSize){
                break;
            }
        }
        log.debug("刷新本地元数据状态定时任务结束|||");
    }

}
