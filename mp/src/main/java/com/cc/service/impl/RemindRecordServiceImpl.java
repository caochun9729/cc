package com.cc.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.dao.RemindRecordMapper;
import com.cc.entity.RemindRecord;
import com.cc.pojo.RemindRecordParams;
import com.cc.pojo.RemindRecordPojo;
import com.cc.redis.RedisUtil;
import com.cc.service.IRemindRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>
 * 提醒记录 服务实现类
 * </p>
 *
 * @author cc
 * @since 2019-12-05
 */
@Service
public class RemindRecordServiceImpl extends ServiceImpl<RemindRecordMapper, RemindRecord> implements IRemindRecordService {
    @Autowired
    private RemindRecordMapper remindRecordMapper;
    @Autowired
    private RedisUtil redisUtil;
    private final static Executor executor = Executors.newCachedThreadPool();// 启用多线程

    @Override
    public int updateRemindRecordForProject(List<String> projectReviewIds) {
        return remindRecordMapper.updateRemindRecordForProject(projectReviewIds);
    }

    @Override
    public List<RemindRecordPojo> getReminderCount(RemindRecordParams remindRecordParams) {
        return remindRecordMapper.getReminderCount(remindRecordParams);
    }

    @Override
    public List<Map<String, Object>> remindRecordInit() {
        return remindRecordMapper.remindRecordInit();
    }

    @Override
    public Integer countRemind(String userId) {
        List<Map<String,Object>> objects = (List<Map<String,Object>>)redisUtil.get("remind:init");
        int count=0;//当前用户未读消息总数
        Date now = new Date();
        for(Map<String,Object> map:objects){
            String userCode = map.get("USER_ID").toString();
            String isPublic = null;
            if(null != map.get("IS_PUBLIC")){
                isPublic= map.get("IS_PUBLIC").toString();
            }
            Date repeatRemindTime = (Date) map.get("REPEAT_REMIND_TIME");
            Date remindTime = (Date) map.get("REMIND_TIME");
            String isVisible = map.get("IS_VISIBLE").toString();
            if(userId.equals(userCode)  && isVisible.equals("0")){
                if(null == isPublic){
                    //非事务提醒,可见
                    count++;
                } else {
                    if(null != repeatRemindTime){
                        //重复事务提醒
                        if(repeatRemindTime.getTime() <= now.getTime() ){
                            count++;
                        }
                    }else if(null != remindTime){
                        //不重复事务
                        if(remindTime.getTime() <= now.getTime() ){
                            count++;
                        }
                    }else{
                        //事务再次提醒
                        count++;
                    }
                }
            }

        }
        return count;
    }

    @Override
    public void userRemindRecordInit(List<Map<String, Object>> mapList) {
        //System.out.println(mapList.size());
        List<Map<String,Object>> list=new ArrayList<>();
        Date now = new Date();
        for(Map<String,Object> map:mapList){
                int count=0;
                String userCode = map.get("USER_ID").toString();

                String isPublic = null;
                if(null != map.get("IS_PUBLIC")){
                    isPublic= map.get("IS_PUBLIC").toString();
                }
                Date repeatRemindTime = (Date) map.get("REPEAT_REMIND_TIME");
                Date remindTime = (Date) map.get("REMIND_TIME");
                String isVisible = map.get("IS_VISIBLE").toString();
                if( isVisible.equals("0")){
                    if(null == isPublic){
                        //非事务提醒,可见
                        count++;
                    } else {
                        if(null != repeatRemindTime){
                            //重复事务提醒
                            if(repeatRemindTime.getTime() <= now.getTime() ){
                                count++;
                            }
                        }else if(null != remindTime){
                            //不重复事务
                            if(remindTime.getTime() <= now.getTime() ){
                                count++;
                            }
                        }else{
                            //事务再次提醒
                            count++;
                        }
                    }
                }

                //当前遍历用户
                if(count>0){
                    if(CollectionUtils.isEmpty(list)){
                        //第一次遍历
                        List<Map<String,Object>> reminds= new ArrayList<>();
                        reminds.add(map);
                        Map<String,Object> m1=new HashMap<>();
                        m1.put("userId",userCode);
                        m1.put("userRemind",reminds);
                        list.add(m1);
                    }else{
                        //当前遍历用户存在集合中
                        Boolean falg=false;
                        for(Map<String,Object> m:list){
                            if(userCode .equals( m.get("userId").toString())){
                                falg=true;
                                List<Map<String,Object>> dq=(List<Map<String, Object>>) m.get("userRemind");
                                dq.add(map);
                                m.put("userRemind",dq);
                                break;
                            }
                        }
                        if(!falg){
                            List<Map<String,Object>> reminds= new ArrayList<>();
                            reminds.add(map);
                            Map<String,Object> m1=new HashMap<>();
                            m1.put("userId",userCode);
                            m1.put("userRemind",reminds);
                            list.add(m1);
                        }
                    }
                }
        }
        int count=0;
        for(Map<String,Object> m:list){
            //System.out.println("remind:"+m.get("userId").toString()+"---"+((List<Map<String, Object>>)m.get("userRemind")).size());
            redisUtil.set("remind:"+m.get("userId").toString(),(List<Map<String, Object>>)m.get("userRemind"));
            count+=((List<Map<String, Object>>)m.get("userRemind")).size();
        }
        //System.out.println("计算总数"+count);
    }
}
