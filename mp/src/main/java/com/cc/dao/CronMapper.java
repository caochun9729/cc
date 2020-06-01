package com.cc.dao;

import com.cc.entity.Cron;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author cc
 * @Date 2020/4/22 11:43
 * @Version 1.0
 */
@Repository
public interface CronMapper {
    int insert(Cron record);

    List<Cron> selectAll();
}
