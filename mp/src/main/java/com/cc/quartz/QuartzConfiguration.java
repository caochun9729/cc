package com.cc.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;


@Configuration
public class QuartzConfiguration {
    @Bean
    public JobDetail testQuartz() {
        return JobBuilder.newJob(FirstJob.class).withIdentity("firstJob").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger() {
        //cron方式，每天上午10点、下午两点、下午4点整触发
        return TriggerBuilder.newTrigger().forJob(testQuartz())
                .withIdentity("FirstJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 29 9 * * ?"))
                .build();
    }


    public static void main(String[] args) {
        Date date=new Date();
        String dateFormat="ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        System.out.println(formatTimeStr);
    }

}
