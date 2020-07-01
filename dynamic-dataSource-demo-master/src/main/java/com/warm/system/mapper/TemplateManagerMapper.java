package com.warm.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warm.system.entity.TemplateManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板管理 Mapper 接口
 * </p>
 *
 * @author cc
 * @since 2020-06-22
 */
public interface TemplateManagerMapper extends BaseMapper<TemplateManager> {

    /**
     * 获取数据表名
     * @param tableName
     * @return
     */
    List<TemplateManager> getFormManageList(@Param("tableName") String tableName);

}
