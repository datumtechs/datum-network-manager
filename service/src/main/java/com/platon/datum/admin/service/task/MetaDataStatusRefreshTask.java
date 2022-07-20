package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.AttributeDataTokenMapper;
import com.platon.datum.admin.dao.DataTokenMapper;
import com.platon.datum.admin.dao.MetaDataMapper;
import com.platon.datum.admin.dao.entity.AttributeDataToken;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dao.entity.MetaData;
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
//@Configuration
public class MetaDataStatusRefreshTask {

    @Resource
    private DataSyncService dataSyncService;
    @Resource
    private MetaDataMapper metaDataMapper;
    @Resource
    private MetaDataClient metaDataClient;
    @Resource
    private DataTokenMapper dataTokenMapper;
    @Resource
    private AttributeDataTokenMapper attributeDataTokenMapper;

    @Scheduled(fixedDelayString = "${MetaDataStatusRefreshTask.fixedDelay}")
    @Transactional
    public void task() {
        log.debug("刷新本地元数据状态定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.LocalMetaData);
            List<MetaData> metaDataList = metaDataClient.getLocalMetaDataList(dataSync.getLatestSynced());
            if (CollectionUtils.isEmpty(metaDataList)) {
                break;
            }
            log.debug("本次更新元数据状态数量：{}", metaDataList.size());

            metaDataMapper.updateByMetaDataIdBatch(metaDataList);

            LocalDateTime updateTime = metaDataList.stream()
                    .sorted(Comparator.comparing(MetaData::getRecUpdateTime).reversed())
                    .findFirst()
                    .get().getRecUpdateTime();

            dataSync.setLatestSynced(updateTime);

            //把最近更新时间update到数据库
            dataSyncService.updateDataSync(dataSync);

            if (metaDataList.size() < GrpcConstant.PageSize) {
                break;
            }
        }
        log.debug("刷新本地元数据状态定时任务结束|||");
    }

    /**
     * v 0.4.0 将元数据发布的 dataToken 合约地址绑定 元数据信息中 (metadata : dataToken == 1 : 1)
     */
    @Scheduled(fixedDelayString = "${MetaDataStatusRefreshTask.fixedDelay}")
    public void bindDataTokenAddressTask() {
        log.debug("合约地址绑定定时任务开始>>>");
        //1.查询出需要绑定的数据
        List<MetaData> metaDataList = metaDataMapper.selectUnBindDataToken();

        //2.绑定数据
        metaDataList.forEach(metaData -> {
            try {
                DataToken dataToken = dataTokenMapper.selectByMetaDataId(metaData.getId());
                metaDataClient.bindDataTokenAddress(metaData.getMetaDataId(), dataToken.getAddress());
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        });
        log.debug("合约地址绑定定时任务结束|||");
    }

    /**
     * v 0.5.0 将元数据发布的 attributeDataToken 合约地址绑定 元数据信息中 (metadata : attributeDataToken == 1 : 1)
     */
    @Scheduled(fixedDelayString = "${MetaDataStatusRefreshTask.fixedDelay}")
    public void bindAttributeDataTokenAddressTask() {
        log.debug("绑定有属性合约定时任务开始>>>");
        //1.查询出需要绑定的数据
        List<MetaData> metaDataList = metaDataMapper.selectUnBindAttributeDataToken();

        //2.绑定数据
        metaDataList.forEach(metaData -> {
            try {
                AttributeDataToken attributeDataToken = attributeDataTokenMapper.selectByMetaDataId(metaData.getId());
                metaDataClient.bindAttributeDataTokenAddress(metaData.getMetaDataId(), attributeDataToken.getAddress());
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        });
        log.debug("绑定有属性合约定时任务结束|||");
    }

}
