package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    public void task() {
        log.debug("定时获取元数据授权申请...");
        //数据中心同步来的data_auth信息
        try {
            List<LocalDataAuth> localDataAuthList = authClient.getMetaDataAuthorityList();
            if (CollectionUtils.isEmpty(localDataAuthList)) {
                log.warn("获取元数据授权申请列表数据为空");
                return;
            }

            //本地已经存在的data_auth信息
            List<LocalDataAuth> existingList = dataAuthMapper.listAll();
            Map<String, Boolean> existingMetaAuthIdMap = existingList.parallelStream().collect(Collectors.toMap(LocalDataAuth::getAuthId, value -> true));

            //过滤掉已经存在的
            List<LocalDataAuth> newDataAuthList = localDataAuthList.parallelStream().filter(item -> !existingMetaAuthIdMap.containsKey(item.getAuthId())).collect(Collectors.toList());

            //3、批量更新DB
            if (!CollectionUtils.isEmpty(newDataAuthList)) {
                dataAuthMapper.insertBatch(newDataAuthList);
                log.debug("新增数据授权申请：{}", newDataAuthList.size());
            } else {
                log.debug("新增数据授权申请：0");
            }
        }catch (Exception e){
            log.error("定时获取元数据授权申请出错", e);
        }
        log.debug("定时获取元数据授权申请结束...");
    }











}
