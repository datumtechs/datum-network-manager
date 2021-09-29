package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.StoreCalculateResultMapper;
import com.platon.metis.admin.dao.entity.StoreCalculateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author houz
 * @Description 计算结果定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
@Configuration
public class CalculateResultTask {

    @Resource
    private StoreCalculateResultMapper storeCalculateResultMapper;

    /**
     *
     * 每月第一天0点
     */
    //@Scheduled(cron = "0 10 0 1 * ?")
    @Scheduled(cron = "${CalculateResultTask.cron}")
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
