package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.DataAuthResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;



/**
 * 数据授权申请定时任务
 */
@Slf4j
//@Component
public class DataAuthRefreshTask {

    @Resource
    private AuthClient authClient;
    @Resource
    private LocalDataAuthMapper dataAuthMapper;


    @Scheduled(fixedDelay = 20000)
    public void getRpcDataAuthList() {
        log.info("启动数据授权申请定时任务...");
        DataAuthResp resp = authClient.getMetaDataAuthorityList();
        if (Objects.isNull(resp) || GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取申请数据授权,调度服务调用失败");
            return;
        }
        if(CollectionUtils.isEmpty(resp.getDataAuthList())){
            log.info("RPC获取申请数据授权列表数据为空");
            return;
        }

        //1、筛选出需要更新Auth Data
        log.info("1、筛选出需要更新Auth Data");
        List<LocalDataAuth> updateDataAuthList =  resp.getDataAuthList();
        /*List<String> deleteAuthIds = dataAuthMapper.selectDataAuthByStatusWithAuthFinish();
        List<LocalDataAuth> updateDataAuthList = allDataAuthList.stream().filter(new Predicate<LocalDataAuth>() {
                                                                @Override
                                                                public boolean test(LocalDataAuth dataAuth) {
                                                                    return !deleteAuthIds.contains(dataAuth.getAuthId());
                                                                }
                                                        }).collect(Collectors.toList());*/
        //2、整理收集待持久化数据
        log.info("2、整理收集待持久化数据");
        log.info("待持久化数据updateDataAuthList:" + updateDataAuthList.size());

        //3、批量更新DB
        log.info("3、批量更新DB");
        if (!CollectionUtils.isEmpty(updateDataAuthList)) {
            dataAuthMapper.insertBatch(updateDataAuthList);
        }
        log.info("结束执行数据授权申请定时任务...");

    }











}
