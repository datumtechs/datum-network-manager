package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.DataTokenMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.DataSync;
import com.platon.metis.admin.dao.entity.DataToken;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.grpc.client.MetaDataClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
            /**
             * 元数据的状态 (0: 未知; 1: 未发布; 2: 已发布; 3: 已撤销;4:已删除;5: 发布中; 6:撤回中;7:凭证发布失败;8:凭证发布中; 9:已发布凭证;10 已绑定凭证)
             * 过滤掉本地状态已变成：7:凭证发布失败;8:凭证发布中; 9:已发布凭证;10 已绑定凭证
             */
            List<LocalMetaData> filterLocalMetaDataList = localMetaDataList.stream().filter(metaData -> {
                Integer status = metaData.getStatus();
                if (status == LocalDataFileStatusEnum.TOKEN_FAILED.getStatus()
                        || status == LocalDataFileStatusEnum.TOKEN_RELEASED.getStatus()
                        || status == LocalDataFileStatusEnum.TOKEN_RELEASING.getStatus()
                        || status == LocalDataFileStatusEnum.TOKEN_BOUND.getStatus()) {
                    return false;
                }
                return true;
            }).collect(Collectors.toList());

            log.debug("过滤后的数量：{}", filterLocalMetaDataList.size());

            if (!CollectionUtils.isEmpty(filterLocalMetaDataList)) {
                localMetaDataMapper.updateStatusByMetaDataIdBatch(filterLocalMetaDataList);
            }

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
