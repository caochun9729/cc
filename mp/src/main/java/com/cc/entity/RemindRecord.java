package com.cc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cc
 * @since 2019-12-05
 */
@ApiModel(value = "提醒记录")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("REMIND_RECORD")
public class RemindRecord extends Model<RemindRecord> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")

    @TableId("ID")
    private String id;
    @ApiModelProperty(value = "提醒类型编码  （ 提醒[ 分项目提醒2、事务提醒1 ] 、项目审批、项目警示、任务检查、未读事项 ）")

    @TableField("TYPE_CODE")
    private String typeCode;
    @ApiModelProperty(value = "提醒类型名称")

    @TableField("TYPE_NAME")
    private String typeName;
    @ApiModelProperty(value = "用户ID")

    @TableField("USER_ID")
    private Integer userId;
    @ApiModelProperty(value = "管理的 ID")

    @TableField("OTHER_ID")
    private String otherId;
    @ApiModelProperty(value = "角色编码（项目负责人、项目终止人···）")

    @TableField("ROLE_CODE")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")

    @TableField("ROLE_NAME")
    private String roleName;
    @ApiModelProperty(value = "提醒内容")

    @TableField("CONTENT")
    private String content;
    @ApiModelProperty(value = "是否是 @ 我的")

    @TableField("IS_AT")
    private String isAt;
    @ApiModelProperty(value = "是否已读")

    @TableField("IS_READ")
    private String isRead;
    @TableField("CREATE_TIME")
    private Date createTime;
    @TableField("CREATE_BY")
    private String createBy;
    @TableField("UPDATE_TIME")
    private Date updateTime;
    @TableField("UPDATE_BY")
    private String updateBy;
    @TableField("DELETE_TIME")
    private Date deleteTime;
    @TableField("DELETE_BY")
    private String deleteBy;
    @TableField("DELETE_FLAG")
    private String deleteFlag;
    @TableField("IS_VISIBLE")
    private String isVisible;
    @TableField("REPEAT_REMIND_TIME")
    private Date repeatRemindTime;
    @TableField("REMIND_TIME_ID")
    private String remindTimeId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
