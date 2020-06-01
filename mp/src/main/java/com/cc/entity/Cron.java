package com.cc.entity;

/**
 * @Author cc
 * @Date 2020/4/22 11:43
 * @Version 1.0
 */

public class Cron {
    private String cronId;

    private String cron;

    public String getCronId() {
        return cronId;
    }

    public void setCronId(String cronId) {
        this.cronId = cronId == null ? null : cronId.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }
}
