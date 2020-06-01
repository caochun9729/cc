package com.cc.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cc.entity.SysUser;
import com.cc.service.SysUserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FirstJob extends QuartzJobBean {
    @Autowired
    private SysUserService sysUserService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("user_id","username").eq("del_flag",0).le("create_time",new Date());
        List<SysUser> list = sysUserService.list(queryWrapper);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("TestQuartz01----" + sdf.format(new Date()));
        System.out.println(list);
    }
}
