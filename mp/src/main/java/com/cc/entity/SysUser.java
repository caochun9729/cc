package com.cc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cc
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
@ApiModel
public class SysUser extends Model<SysUser> implements  Serializable{

    private static final long serialVersionUID = 1L;

    public SysUser() {
    }

    /**
     * 主键ID
     */
    @TableId("USER_ID")
    @ApiModelProperty(value = "用户Id")
    private Integer userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 0-正常，9-锁定
     */
    @TableField("LOCK_FLAG")
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    @TableField("DEL_FLAG")
    private String delFlag;

    /**
     * 邮箱
     */
    @TableField("MAILBOX")
    private String mailbox;

    /**
     * 姓名
     */
    @TableField("REALNAME")
    private String realname;

    /**
     * 排序
     */
    @TableField("SORT")
    private Long sort;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
