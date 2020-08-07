package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* @author cc
* @since 2020-08-07
*/
@ApiModel(value ="用户管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("ID")
    private String id;
    @ApiModelProperty(value = "登录账号")
    @TableField("LOGIN_CODE")
    private String loginCode;
    @ApiModelProperty(value = "姓名")
    @TableField("USER_NAME")
    private String userName;
    @ApiModelProperty(value = "联系方式")
    @TableField("TELEPHONE")
    private String telephone;
    @ApiModelProperty(value = "角色编码")
    @TableField("ROLE_CODE")
    private String roleCode;
    @ApiModelProperty(value = "角色名称")
    @TableField("ROLE_NAME")
    private String roleName;
    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;
    @TableField("CREATE_TIME")
    private Date createTime;
    @ApiModelProperty(value = "状态 0:未开启 1:开启")
    @TableField("STATUS")
    private String status;
    @ApiModelProperty(value = "性别名")
    @TableField("SEX_NAME")
    private String sexName;
    @ApiModelProperty(value = "所属组织")
    @TableField("BELONG_ORGANIZATION_CODE")
    private String belongOrganizationCode;
    @ApiModelProperty(value = "所属组织名称")
    @TableField("BELONG_ORGANIZATION_NAME")
    private String belongOrganizationName;
    @TableField("SURVEY_CODE")
    private String surveyCode;
    @TableField("SURVEY_NAME")
    private String surveyName;
    @TableField("SEX_CODE")
    private String sexCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
