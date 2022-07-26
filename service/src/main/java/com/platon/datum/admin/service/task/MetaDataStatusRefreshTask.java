package com.platon.datum.admin.service.task;

import cn.hutool.core.util.StrUtil;
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
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Transactional(rollbackFor = Exception.class)
    public void task() {
        log.debug("刷新本地元数据状态定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.LocalMetaData);
            List<Pair<MetaData, DataToken>> metaDataPairList = metaDataClient.getLocalMetaDataList(dataSync.getLatestSynced());
            if (CollectionUtils.isEmpty(metaDataPairList)) {
                break;
            }
            log.debug("本次更新元数据状态数量：{}", metaDataPairList.size());

            List<MetaData> metaDataList = new ArrayList<>();
            List<DataToken> dataTokenList = new ArrayList<>();
            metaDataPairList.forEach(metaDataDataTokenPair -> {
                MetaData left = metaDataDataTokenPair.getLeft();
                metaDataList.add(left);
                DataToken right = metaDataDataTokenPair.getRight();
                dataTokenList.add(right);
            });

            //更新元数据
            metaDataMapper.updateByMetaDataIdBatch(metaDataList);

            for (int i = 0; i < metaDataList.size(); i++) {
                MetaData metaData = metaDataList.get(i);
                DataToken newDataToken = dataTokenList.get(i);
                //更新dataToken
                updateDataToken(metaData, newDataToken);
                //更新atrribute dataToken
                updateAttriButeToken(metaData);
            }

            //把最近更新时间update到数据库
            LocalDateTime updateTime = metaDataList.stream()
                    .sorted(Comparator.comparing(MetaData::getRecUpdateTime).reversed())
                    .findFirst()
                    .get()
                    .getRecUpdateTime();

            dataSync.setLatestSynced(updateTime);
            dataSyncService.updateDataSync(dataSync);

            if (metaDataList.size() < GrpcConstant.PageSize) {
                break;
            }
        }
        log.debug("刷新本地元数据状态定时任务结束|||");
    }

    private void updateAttriButeToken(MetaData metaData) {
        //不为空说明已绑定dataToken成功
        String attributeDataTokenAddress = metaData.getAttributeDataTokenAddress();
        if (StrUtil.isNotBlank(attributeDataTokenAddress)) {
            AttributeDataToken dataToken = attributeDataTokenMapper.selectByAddress(attributeDataTokenAddress);
            if (dataToken.getStatus() == AttributeDataToken.StatusEnum.BINDING.getStatus()) {
                //将状态修改为绑定成功
                attributeDataTokenMapper.updateStatus(dataToken.getId(), AttributeDataToken.StatusEnum.BIND_SUCCESS.getStatus());
            }
        }
    }

    private void updateDataToken(MetaData metaData, DataToken newDataToken) {
        //不为空说明已绑定dataToken成功
        String dataTokenAddress = metaData.getDataTokenAddress();
        if (StrUtil.isNotBlank(dataTokenAddress)) {
            DataToken dataToken = dataTokenMapper.selectByAddress(dataTokenAddress);
            if (dataToken.getStatus() == DataToken.StatusEnum.BINDING.getStatus()) {
                //将状态修改为绑定成功
                dataTokenMapper.updateStatus(dataToken.getId(), DataToken.StatusEnum.BIND_SUCCESS.getStatus());
            }
            //将旧的消费量更新
            dataTokenMapper.updateFeeById(dataToken.getId(), newDataToken.getCiphertextFee(), newDataToken.getPlaintextFee());
        }
    }

}
