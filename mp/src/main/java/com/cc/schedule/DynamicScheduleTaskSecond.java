package com.cc.schedule;

/**
 * @Author cc
 * @Date 2020/4/23 16:32
 * @Version 1.0
 * 动态修改cron参数，即时生效
 */
import java.time.LocalDateTime;
import java.util.Date;


import com.cc.dao.CronMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;



@Component
@EnableScheduling
@RestController
public class DynamicScheduleTaskSecond implements SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(DynamicScheduleTaskSecond.class);

    @Autowired
    private CronMapper cronMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                // 任务逻辑
                logger.info("dynamicCronTask is running..."+ LocalDateTime.now().toLocalTime());
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 2.1 从数据库获取执行周期
                String cron = cronMapper.selectAll().get(0).getCron();
                // 任务触发，可修改任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        });
    }

}