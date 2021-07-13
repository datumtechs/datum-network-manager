package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.util.BatchExecuteUtil;
import com.platon.rosettanet.admin.dao.GlobalPowerMapper;
import com.platon.rosettanet.admin.dao.entity.GlobalPower;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/12 11:48
 * @Version
 * @Desc 全网算力刷新
 */

@Slf4j
//@Component
public class GlobalPowerRefreshTask {

    @Resource
    private GlobalPowerMapper globalPowerMapper;
    @Resource
    private PowerClient powerClient;


    /**
     * TODO 后续增加补偿和失败重试等机制
     */
    @Scheduled(fixedDelay = 10000)
    public void task(){
        StopWatch stopWatch = new StopWatch("全网数据刷新计时");
        //### 1.获取全网算力，包括本组织算力
        stopWatch.start("1.获取全网算力，包括本组织算力");
        List<GlobalPower> powerList = powerClient.getPowerTotalDetailList();
        stopWatch.stop();
        //### 2.将数据归类
        stopWatch.start("2.将数据归类");
        //2.1先获取所有已存在数据库中的IdentityId
        List<String> identityIdList = globalPowerMapper.selectAllIdentityId();
        //需要更新的列表
        List<GlobalPower> updateList = new ArrayList<>();
        //需要新增的列表
        List<GlobalPower> addList = new ArrayList<>();
        //需要删除的identityId列表
        List<String> deleteList = new ArrayList<>(identityIdList);
        String localOrg = LocalOrgIdentityCache.getIdentityId();
        powerList.stream()
                .filter(detail -> {//先过滤掉本组织数据
                    if(localOrg.equals(detail.getIdentityId())){
                        return false;
                    } else {
                        return true;
                    }
                })
                .forEach(power -> {//数据归类 TODO 性能方面考虑
                    deleteList.remove(power.getIdentityId());
                    if(identityIdList.contains(power.getIdentityId())){//如果已存在数据库，则进行更新
                        power.setRecUpdateTime(new Date());
                        updateList.add(power);
                    } else {//不存在，则表示是新增
                        Date date = new Date();
                        power.setRecUpdateTime(date);
                        addList.add(power);
                    }
                });
        stopWatch.stop();
        //### 3.入库
        //3.1批量更新
        stopWatch.start("3.1批量更新");
        batchUpdate(updateList);
        stopWatch.stop();
        //3.2批量新增
        stopWatch.start("3.2批量新增");
        batchAdd(addList);
        stopWatch.stop();
        //3.3批量删除
        stopWatch.start("3.3批量删除");
        batchDeleteByMetaDataId(deleteList);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

    }

    private void batchAdd(List<GlobalPower> addList) {
        //批量新增
        BatchExecuteUtil.batchExecute(1000,addList,(tempList)->{
            globalPowerMapper.batchAddSelective(tempList);
        });
    }

    private void batchUpdate(List<GlobalPower> updateList) {
        //批量更新
        BatchExecuteUtil.batchExecute(1000,updateList,(tempList)->{
            globalPowerMapper.batchUpdateByIdentityIdSelective(tempList);
        });
    }

    private void batchDeleteByMetaDataId(List<String> deleteList){
        //批量删除
        BatchExecuteUtil.batchExecute(1000,deleteList,(tempList)->{
            globalPowerMapper.batchDeleteByIdentityId(tempList);
        });
    }



}
