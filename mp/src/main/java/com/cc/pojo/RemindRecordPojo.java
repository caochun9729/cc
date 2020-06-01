package com.cc.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class RemindRecordPojo {
    private String remindId;
    private String typeCode;
    private String typeName;
    private String otherId;
    private String userId;
    private String isAt;
    private String isRead;
    private String roleCode;
    private String roleName;
    private String content;
    private Date createTime;
    private String proName;
    private String taskName;
    private Date startTime;
    private Date EndTime;
    private String createBy;
    private String headImageUrl;
    private String whetherFinalReview;
    private String whetherToPass;
}