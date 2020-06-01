package com.scheduled.dynamic.task;

import com.scheduled.dynamic.service.ScheduledTaskJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务 05
 *
 * @author 码农猿
 */

public class ScheduledTaskAdd implements ScheduledTaskJob {
    private String taskKey;

    public ScheduledTaskAdd(String taskKey){
        this.taskKey=taskKey;
    }

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTaskAdd.class);

    @Override
    public void run() {

        LOGGER.info("ScheduledTask => "+taskKey+"  run  当前线程名称 {} ", Thread.currentThread().getName());
    }
}