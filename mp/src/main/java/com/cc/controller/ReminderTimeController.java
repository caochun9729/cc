package com.cc.controller;


import com.cc.constant.Response;
import com.cc.entity.RemindRecord;
import com.cc.pojo.RemindRecordParams;
import com.cc.pojo.RemindRecordPojo;
import com.cc.redis.RedisUtil;
import com.cc.service.IRemindRecordService;
import com.cc.websocket.SocketHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cc
 * @since 2019-11-21
 */
@Api(value = "消息提醒", description = "消息提醒")
@RestController
    @RequestMapping("/reminderTime")
public class ReminderTimeController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SocketHandler socketHandler;

    @Autowired
	private IRemindRecordService remindRecordService;
    public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");


    @ApiOperation(value = "消息提醒统计", notes = "消息提醒统计")
    @RequestMapping(value = "/getReminderTimeSum", method = RequestMethod.POST)
    public Response getReminderTimeSum(@RequestBody RemindRecordParams remindRecordParams) {
        //项目警示 df0abd14886b4fa8b5026da6ffa129ab--3
        //事务提醒 1
        //项目提醒 2
        //项目审批 4
        //任务检查 5
        //未读事项 6
        //指标提醒 7
        //List<RemindRecordPojo> reminderPojoByUserId = remindRecordService.getReminderPojoByUserId(remindRecordParams);
        List<RemindRecordPojo> reminderPojoByUserId = remindRecordService.getReminderCount(remindRecordParams);
        Map<String,Object> map=new HashMap<>();

        if(!StringUtils.isEmpty(reminderPojoByUserId)){
            map.put("count", reminderPojoByUserId.size());
            //map.put("data", reminderPojoByUserId);
        }else{
            map.put("count", 0);
            //map.put("data", null);
        }
        return new Response(map);
    }


    @ApiOperation(value = "消息提醒设为已读")
    @PostMapping("/setRemindRecordRead")
    public Response setRemindRecordRead(@RequestBody RemindRecordParams remindRecordParams){
        List<Map<String,Object>> o = (List<Map<String, Object>>) redisUtil.get("remind:init");
        List<String> userIds=new ArrayList<>();
        for(int i=0;i<o.size();i++){
            if(o.get(i).get("ID").toString().equals("328ee0bec846469299c8ff9a0519e536")){
                userIds.add(o.get(i).get("USER_ID").toString());
                o.remove(i);
                i--;
            }
        }
        redisUtil.delLike("remind");
        redisUtil.set("remind:init", o);
        //解析成单个用户
        remindRecordService.userRemindRecordInit(o);
        //给用户发消息
        socketHandler.sendMessageToUserLists(userIds);
        return new Response();
    }


    @ApiOperation(value = "再次提醒", notes = "再次提醒")
	@RequestMapping(value = "/againReminderTime", method = RequestMethod.POST)
	public Response againReminderTime(@RequestBody RemindRecordParams remindRecordParams) {
        List<Map<String,Object>> o = (List<Map<String, Object>>) redisUtil.get("remind:init");
        Map<String,Object> map=new HashMap<>();
        List<String> userIds=new ArrayList<>();
        userIds.add("10000086");
        map.put("ID","asdddd");
        map.put("USER_ID","10000086");
        map.put("IS_PUBLIC",null);
        map.put("REPEAT_REMIND_TIME",null);
        map.put("REMIND_TIME",null);
        map.put("IS_VISIBLE","0");
        o.add(map);
        redisUtil.delLike("remind");
        redisUtil.set("remind:init", o);
        //解析成单个用户
        remindRecordService.userRemindRecordInit(o);
        //给用户发消息
        socketHandler.sendMessageToUserLists(userIds);
        return new Response();
	}

}

