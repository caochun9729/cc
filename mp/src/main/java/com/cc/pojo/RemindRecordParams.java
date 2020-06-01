package com.cc.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class RemindRecordParams {
    @ApiModelProperty(value = "用户Id 必填")
    private String userId;
    @ApiModelProperty(value = "搜索框内容")
    private String content;
    @ApiModelProperty(value = "提醒")
    private String remind;
    @ApiModelProperty(value = "项目审批")
    private String approval;
    @ApiModelProperty(value = "项目警示")
    private String warning;
    @ApiModelProperty(value = "任务检查")
    private String taskCheck;
    @ApiModelProperty(value = "@我的")
    private String isAt;
    @ApiModelProperty(value = "未读事项")
    private String isRead;
    @ApiModelProperty(value = "未读已读 0未读 1已读")
    private String readOrUnread;
    @ApiModelProperty(value = "事务Id")
    private String proId;
    @ApiModelProperty(value = "事务名")
    private String proName;
    @ApiModelProperty(value = "创建人")
    private String createby;
    @ApiModelProperty(value = "消息提醒主键集合")
    private List<String> remindIds;
}
