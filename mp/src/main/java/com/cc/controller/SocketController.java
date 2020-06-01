package com.cc.controller;

import com.cc.redis.RedisUtil;
import com.cc.service.IRemindRecordService;
import com.cc.thread.HelloThread;
import com.cc.websocket.SocketHandler;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/main")
public class SocketController {
	private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

	@Autowired
	private SocketHandler socketHandler;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private HelloThread hello;
	@Autowired
	private IRemindRecordService remindRecordService;

	
	@RequestMapping(value="/login")
	public String login(String userName, Model model) throws InterruptedException {
		logger.info("用户登录");
		//前后端分离不适用session
		//session.setAttribute("user", userName);
		redisUtil.set("user",userName);
		model.addAttribute("userName",userName);
		return "home";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String sendMessage(String userName){
		
		socketHandler.sendMessageToUser(userName, new TextMessage("测试消息"));

		return "message";
	}

	@RequestMapping(value = "/message/list", method = RequestMethod.GET)
	public String sendMessageList(@RequestParam("name1") String name1,@RequestParam("name2") String name2){
		List<String> list=new ArrayList<>();
		list.add(name1);
		list.add(name2);
		socketHandler.sendMessageToUserList(list, new TextMessage("测试消息"));

		return "message";
	}

	@RequestMapping(value = "/message/init", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessageInit(String userName){
		System.out.println("用户信息初始化");
		//socketHandler.isExistsendMessage(userName);
		Object o1 = redisUtil.get("remind:" + userName);
		List<Map<String,Object>> o = (List<Map<String, Object>>) o1;
		socketHandler.sendMessageToUser(userName,new TextMessage(String.valueOf(o.size())));
		return null;
	}

	@RequestMapping(value = "/message/all", method = RequestMethod.GET)
	public String sendMessageAll(String userName){

		socketHandler.sendMessageToUsers(new TextMessage("测试消息"));

		return "message";
	}

	public static void main(String[] args) {
		List<String> users=new ArrayList<>();
		System.out.println(!users.contains("1"));
	}
}
