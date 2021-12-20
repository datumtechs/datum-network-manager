package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.service.LocalDataAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private LocalDataAuthService localDataAuthService;

    //@Scheduled(fixedDelay = 20000)
    @Scheduled(fixedDelayString = "${DataAuthRefreshTask.fixedDelay}")
    public void task() {
        log.debug("启动数据授权申请定时任务...");

        try {
            List<LocalDataAuth> localDataAuthList = authClient.getMetaDataAuthorityList();

            //数据中心同步来的data_auth信息
            if (CollectionUtils.isEmpty(localDataAuthList)) {
                log.warn("RPC获取申请数据授权列表数据为空");
                return;
            }

            //本地已经存在的data_auth信息（本地的都是成功审核的记录）
            List<LocalDataAuth> existingList = dataAuthMapper.listAll();

            Map<String, Boolean> existingMetaAuthIdMap = existingList.parallelStream().collect(Collectors.toMap(LocalDataAuth::getAuthId, value -> true));

            //过滤掉已经存在的
            List<LocalDataAuth> newDataAuthList = localDataAuthList.parallelStream().filter(item -> !existingMetaAuthIdMap.containsKey(item.getAuthId())).collect(Collectors.toList());

            List<LocalDataAuth> autoApprovedList = new ArrayList<>();

            for (LocalDataAuth localDataAuth : newDataAuthList) {
                if (localDataAuthService.autoApproveDataAuth(localDataAuth)) {
                    //新的数据授权申请，需要成功审核后才能加入本地记录。没有成功审核的记录，将在下一次再次尝试。
                    //审核成功的，可能是通过，也可能是拒绝（申请过期了），localDataAuth对象已经包含审核状态
                    autoApprovedList.add(localDataAuth);
                }
            }
            //3、批量更新DB
            if (!CollectionUtils.isEmpty(autoApprovedList)) {
                dataAuthMapper.insertBatch(autoApprovedList);
            }
        }catch(Throwable e){
            log.debug("启动数据授权申请定时任务出错...", e);
        }

        log.debug("启动数据授权申请定时任务结束...");
    }











}
