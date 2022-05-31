package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.LocalMetaDataMapper;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.entity.LocalMetaData;
import com.platon.datum.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.datum.admin.grpc.client.MetaDataClient;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


/**
 * 本地元数据状态更新任务
 * 本地元数据发布，撤消等操作，都要等调度服务的异步响应。
 * 通过定时任务来获取调度服务的异步响应。
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
    @Resource
    private DataTokenMapper dataTokenMapper;

    @Scheduled(fixedDelayString = "${LocalMetaDataStatusRefreshTask.fixedDelay}")
    @Transactional
    public void task() {
        log.debug("刷新本地元数据状态定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.LocalMetaData);
            List<LocalMetaData> localMetaDataList = metaDataClient.getLocalMetaDataList(dataSync.getLatestSynced());
            if (CollectionUtils.isEmpty(localMetaDataList)) {
                break;
            }
            log.debug("本次更新元数据状态数量：{}", localMetaDataList.size());

            localMetaDataMapper.updateStatusByMetaDataIdBatch(localMetaDataList);

            LocalDateTime updateTime = localMetaDataList.stream()
                    .sorted(Comparator.comparing(LocalMetaData::getRecUpdateTime).reversed())
                    .findFirst()
                    .get().getRecUpdateTime();

            dataSync.setLatestSynced(updateTime);

            //把最近更新时间update到数据库
            dataSyncService.updateDataSync(dataSync);

            if (localMetaDataList.size() < GrpcConstant.PageSize) {
                break;
            }
        }
        log.debug("刷新本地元数据状态定时任务结束|||");
    }

    /**
     * v 0.4.0 将元数据发布的 datatoken 合约地址绑定 元数据信息中 (metadata : datatoken == 1 : 1)
     */
    @Scheduled(fixedDelayString = "${LocalMetaDataStatusRefreshTask.fixedDelay}")
    public void bindDataTokenAddressTask() {
        log.debug("合约地址绑定定时任务开始>>>");
        //1.查询出需要绑定的数据，即状态为9的
        List<LocalMetaData> localMetaDataList = localMetaDataMapper.selectByStatus(LocalDataFileStatusEnum.TOKEN_RELEASED.getStatus());

        //2.绑定数据并将数据的状态更新为10-已绑定
        localMetaDataList.forEach(localMetaData -> {
            try {
                DataToken dataToken = dataTokenMapper.selectById(localMetaData.getDataTokenId());
                metaDataClient.bindDataTokenAddress(localMetaData.getMetaDataId(), dataToken.getAddress());
                localMetaDataMapper.updateStatusById(localMetaData.getId(), LocalDataFileStatusEnum.TOKEN_BOUND.getStatus());
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        });
        log.debug("合约地址绑定定时任务结束|||");
    }

}
