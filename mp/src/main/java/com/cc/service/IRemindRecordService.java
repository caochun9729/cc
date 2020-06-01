package com.cc.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.cc.entity.RemindRecord;
import com.cc.pojo.RemindRecordParams;
import com.cc.pojo.RemindRecordPojo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提醒记录 服务类
 * </p>
 *
 * @author cc
 * @since 2019-12-05
 */
public interface IRemindRecordService extends IService<RemindRecord> {


    /**
     * 项目审核通过改变消息提醒可见状态
     * @param projectReviewIds
     * @return
     */
    int updateRemindRecordForProject(List<String> projectReviewIds);

    /**
     * 统计用户提醒消息数量
     * @param remindRecordParams
     * @return
     */
    List<RemindRecordPojo> getReminderCount(RemindRecordParams remindRecordParams);

    /**
     * 消息初始化
     * @return
     */
    List<Map<String,Object>> remindRecordInit();

    Integer countRemind(String userId);

    /**
     * 针对用户的消息初始化
     * @return
     */
    void userRemindRecordInit(List<Map<String, Object>> mapList);

}
