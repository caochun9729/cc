package com.cc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cc.entity.RemindRecord;
import com.cc.pojo.RemindRecordParams;
import com.cc.pojo.RemindRecordPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提醒记录 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2019-12-05
 */
@Repository
public interface RemindRecordMapper extends BaseMapper<RemindRecord> {

    //@Update("UPDATE REMIND_RECORD SET DELETE_FLAG=1 WHERE OTHER_ID=#{id}")
    int deleteRemindRecordById(@Param("id") String id);




    /**
     * 根据用户Id查询消息提醒
     *
     * @param remindRecordParams
     * @return
     */
    List<RemindRecordPojo> getReminderPojoByUserId(RemindRecordParams remindRecordParams);

    /**
     * 统计用户提醒消息数量
     * @param remindRecordParams
     * @return
     */
    List<RemindRecordPojo> getReminderCount(RemindRecordParams remindRecordParams);

    /**
     * 项目审核通过改变消息提醒可见状态
     *
     * @param projectReviewIds
     * @return
     */
    int updateRemindRecordForProject(@Param("projectReviewIds") List<String> projectReviewIds);

    /**
     * 删除项目及项目任务的消息提醒
     * @param projectId
     * @return
     */
    int deleteProjectRemindRecord(@Param("projectId") String projectId);

    /**
     * 恢复项目及项目任务的消息提醒
     * @param projectId
     * @return
     */
    int restoreProjectRemindRecord(@Param("projectId") String projectId);

    /**
     * 批量插入消息
     * @param remindRecordList
     * @return
     */
    int batchInsert(@Param("remindRecordList") List<RemindRecord> remindRecordList);

    /**
     * 消息初始化
     * @return
     */
    List<Map<String,Object>> remindRecordInit();
}
