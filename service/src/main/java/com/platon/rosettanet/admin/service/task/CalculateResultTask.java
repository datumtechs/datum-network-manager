package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.dao.StoreCalculateResultMapper;
import com.platon.rosettanet.admin.dao.entity.StoreCalculateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author houz
 * @Description 计算结果定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
@Component
public class CalculateResultTask {

    @Resource
    private StoreCalculateResultMapper storeCalculateResultMapper;

    /**
     *
     * 每月第一天0点
     */
    @Scheduled(cron = "0 10 0 1 * ?")
    public void refreshPowerData(){
        long startTime = System.currentTimeMillis();
        // 查询上月我发布的数据
        Long monthDataSum = storeCalculateResultMapper.queryMyMonthPublishData();
        LocalDate localDate = LocalDate.now();
        StoreCalculateResult storeCalculateResult = new StoreCalculateResult();
        storeCalculateResult.setTimeInterval("month");
        storeCalculateResult.setStoreType("my_publish_data");
        storeCalculateResult.setResideTime(localDate.getYear() + "-" + localDate.getMonthValue());
        storeCalculateResult.setCalculateResult(monthDataSum);
        storeCalculateResultMapper.saveMyPublishDataMonth(storeCalculateResult);
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("refreshPowerData--定时任务执行结束, 执行时间:{}", diffStart+"ms");
    }

}
