package com.warm.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
* @author cc
* @since 2020-06-22
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("TEMPLATE_MANAGER")
public class TemplateManager extends Model<TemplateManager> {

private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    @NotBlank(message = "id必填")
        private String id;
    @TableField("TEMPLATE_NAME")
    private String templateName;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("UPDATE_TIME")
    private Date updateTime;
    @TableField("STATE")
    private String state;

    @TableField("TEMP_TABLE_NAME")
    private String tempTableName;

    @TableField("EXPLANATION")
    private String explanation;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
