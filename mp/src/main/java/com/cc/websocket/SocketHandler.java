package com.cc.websocket;

import com.cc.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;


@Service
public class SocketHandler implements WebSocketHandler{
	private static final Logger logger;
	private static final ArrayList<WebSocketSession> users;
	@Autowired
	private RedisUtil redisUtil;
	static{
		users = new ArrayList<WebSocketSession>();
		logger = LoggerFactory.getLogger(SocketHandler.class);
	}
	
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
		logger.debug("关闭连接");
		users.remove(session);
		
	}

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		logger.info("建立连接socket");
		users.add(session);
		//String username = session.getAttributes().get("user").toString();
		String username = redisUtil.get("user").toString();
		if(username != null){
			session.sendMessage(new TextMessage("socket_"+username+"用户登录"));
		}

		
		
	}

	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {

		if(session.isOpen()){
			session.close();
		}
		logger.error("���ӳ��ִ���:"+error.toString());
		users.remove(session);
		
		
		
		
	}

	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 用户登录后检查有没有消息存在
	 *
	 * @param userName
	 */
	public void isExistsendMessage(String userName) {
		List<Object> allMessage = redisUtil.lGet("allMessage",0,-1);
		List<String> msg=new ArrayList<>();
		for(Object m:allMessage){
			List<MessageResponse> messageResponseList=(List<MessageResponse>) m;
			for(MessageResponse message:messageResponseList){
				if(userName.equalsIgnoreCase(message.getUserName())){
					if(null == message.getRemindTime()){
						msg.add(message.getMsg());
					}else{
						if((new Date()).getTime() > message.getRemindTime().getTime()){
							msg.add(message.getMsg());
						}
					}
				}
			}
		}
		for (WebSocketSession user : users) {
			if (user.getAttributes().get("user").equals(userName)) {
				try {
					if (user.isOpen()) {
						System.out.println(userName+":"+msg.toString());
						user.sendMessage( new TextMessage(msg.toString()));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				//break;
			}
		}
	}

	/**
     * 群发消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
		System.out.println("群发消息");
        for (WebSocketSession user : users) {
			//System.out.println(user.getAttributes().get("user"));
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 单个用户发消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("user").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;
            }
        }
    }


	/**
	 * 指定给多个用户发消息
	 *
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUserList(List<String> userName, TextMessage message) {

		for (WebSocketSession user : users) {

			if (userName.contains(user.getAttributes().get("user"))) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				//break;
			}
		}
	}


	/**
	 * 指定给多个用户发消息
	 *
	 */
	public void sendMessageToUserLists(List<String> userIds) {

		for (WebSocketSession user : users) {

			if (userIds.contains(user.getAttributes().get("user"))) {
				try {
					if (user.isOpen()) {
						List<Map<String,Object>> user1 = (List<Map<String, Object>>) redisUtil.get("remind:" + user.getAttributes().get("user"));
						if(CollectionUtils.isEmpty(user1)){
							user.sendMessage(new TextMessage("0"));
						}else{
							user.sendMessage(new TextMessage(String.valueOf(user1.size())));
						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				//break;
			}
		}
	}


	
	
	
	
}
