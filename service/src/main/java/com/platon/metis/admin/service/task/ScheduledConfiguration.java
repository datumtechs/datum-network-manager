package com.platon.metis.admin.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduledConfiguration implements SchedulingConfigurer {
    private final ThreadPoolTaskScheduler taskScheduler;

    public ScheduledConfiguration() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setErrorHandler(t -> log.error("Exception in @Scheduled task. ", t));
        taskScheduler.setThreadNamePrefix("@scheduled-");

        taskScheduler.initialize();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
        scheduledTaskRegistrar.setScheduler(taskScheduler);
    }
}
