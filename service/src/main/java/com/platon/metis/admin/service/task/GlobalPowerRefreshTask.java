package com.platon.metis.admin.service.task;

import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.common.util.BatchExecuteUtil;
import com.platon.metis.admin.dao.GlobalPowerMapper;
import com.platon.metis.admin.dao.entity.GlobalPower;
import com.platon.metis.admin.grpc.client.PowerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/12 11:48
 * @Version
 * @Desc 全网算力刷新
 */

@Slf4j
@Configuration
public class GlobalPowerRefreshTask {

    @Resource
    private GlobalPowerMapper globalPowerMapper;
    @Resource
    private PowerClient powerClient;


    /**
     * TODO 后续增加补偿和失败重试等机制
     */
    //@Scheduled(fixedDelay = 10000)
    @Scheduled(fixedDelayString = "${GlobalPowerRefreshTask.fixedDelay}")
    @Transactional
    public void task(){
        StopWatch stopWatch = new StopWatch("全网数据刷新计时");
        //### 1.获取全网算力，包括本组织算力
        stopWatch.start("1.获取全网算力，包括本组织算力");
        List<GlobalPower> powerList = null;
        try{
            powerList = powerClient.getGlobalPowerDetailList();
        } catch (ApplicationException exception){
            log.info(exception.getErrorMsg());
            return;
        }
        stopWatch.stop();
        //### 2.将数据归类
        stopWatch.start("2.将数据归类");
        //2.1先获取所有已存在数据库中的IdentityId
        List<String> allPowerId = globalPowerMapper.selectAllPowerId();
        //需要更新的列表
        List<GlobalPower> updateList = new ArrayList<>();
        //需要新增的列表
        List<GlobalPower> addList = new ArrayList<>();

        powerList.stream()
                .forEach(power -> {//数据归类 TODO 性能方面考虑
                    if(allPowerId.contains(power.getId())){//如果已存在数据库，则进行更新
                        updateList.add(power);
                    } else {//不存在，则表示是新增
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
        log.info(stopWatch.prettyPrint());

    }

    private void batchAdd(List<GlobalPower> addList) {
        //批量新增
        BatchExecuteUtil.batchExecute(1000,addList,(tempList)->{
            globalPowerMapper.batchInsert(tempList);
        });
    }

    private void batchUpdate(List<GlobalPower> updateList) {
        //批量更新
        BatchExecuteUtil.batchExecute(1000,updateList,(tempList)->{
            globalPowerMapper.batchUpdate(tempList);
        });
    }
}
