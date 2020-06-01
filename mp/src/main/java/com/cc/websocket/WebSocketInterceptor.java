package com.cc.websocket;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.cc.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WebSocketInterceptor implements HandshakeInterceptor{

	private RedisUtil redisUtil;

	public WebSocketInterceptor(RedisUtil redisUtil){
		this.redisUtil=redisUtil;
	}

	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub
		
	}

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> map) throws Exception {
		if(request instanceof ServerHttpRequest){
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession();
			if(session != null){
				//����socket�����Զ�������Ϣ
				//map.put("user", session.getAttribute("user"));
				//System.out.println(redisUtil.get("user"));
				map.put("user", redisUtil.get("user").toString());
			}
		}
		return true;

	}


}
