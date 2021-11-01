package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.DataAuthResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 数据授权申请定时任务
 */
@Slf4j
@Configuration
public class DataAuthRefreshTask {

    @Resource
    private AuthClient authClient;
    @Resource
    private LocalDataAuthMapper dataAuthMapper;


    //@Scheduled(fixedDelay = 20000)
    @Scheduled(fixedDelayString = "${DataAuthRefreshTask.fixedDelay}")
    public void getRpcDataAuthList() {
        log.info("启动数据授权申请定时任务...");
        DataAuthResp resp = authClient.getMetaDataAuthorityList();
        if (Objects.isNull(resp) || GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取申请数据授权,调度服务调用失败");
            return;
        }

        //数据中心同步来的data_auth信息
        List<LocalDataAuth> dataAuthList =  resp.getDataAuthList();
        if(CollectionUtils.isEmpty(dataAuthList)){
            log.info("RPC获取申请数据授权列表数据为空");
            return;
        }

        //本地已经存在的data_auth信息
        List<LocalDataAuth> existingList = dataAuthMapper.listAll();
        Map<String, Boolean> existingMetaAuthIdMap = existingList.parallelStream().collect(Collectors.toMap(LocalDataAuth::getAuthId, value -> true));

        //过滤掉已经存在的
        List<LocalDataAuth> newDataAuthList = dataAuthList.parallelStream().filter(item -> !existingMetaAuthIdMap.containsKey(item.getAuthId())).collect(Collectors.toList());


        //3、批量更新DB
        if (!CollectionUtils.isEmpty(newDataAuthList)) {
            dataAuthMapper.insertBatch(newDataAuthList);
            log.info("结束执行数据授权申请定时任务，新增数据授权申请：{}...", newDataAuthList.size() );
        }else{
            log.info("结束执行数据授权申请定时任务，新增数据授权申请：0...");
        }
    }











}