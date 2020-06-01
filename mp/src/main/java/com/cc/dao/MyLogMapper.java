package com.cc.dao;

import com.cc.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MyLogMapper {
    @Insert("insert into SYSLOG" +
            " (id,username,ip,url,operation,method,params,createdate,EXCEPTIONNAME,EXCEPTIONMSG)" +
            " values" +
            " (#{id,jdbcType=VARCHAR},#{username,jdbcType=VARCHAR},#{ip,jdbcType=VARCHAR},#{url,jdbcType=VARCHAR}," +
            "#{operation,jdbcType=VARCHAR},#{method,jdbcType=VARCHAR},#{params,jdbcType=VARCHAR},sysdate,#{exceptionName,jdbcType=VARCHAR}" +
            ",#{exceptionMsg,jdbcType=VARCHAR})")
    void insert(SysLog adminLog);
}
