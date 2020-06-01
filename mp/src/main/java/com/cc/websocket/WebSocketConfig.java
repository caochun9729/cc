package com.cc.websocket;
import com.cc.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

@Configuration
//@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	@Autowired
	private SocketHandler socketHandler;
	@Autowired
	private RedisUtil redisUtil;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry arg0) {
		//ע�ᴦ��������,����urlΪsocketServer������
		arg0.addHandler(socketHandler, "/socketServer").addInterceptors(new WebSocketInterceptor(redisUtil)).setAllowedOrigins("*") ;

		//ע��SockJs�Ĵ���������,����urlΪ/sockjs/socketServer������
		arg0.addHandler(socketHandler, "/sockjs/socketServer").addInterceptors(new WebSocketInterceptor(redisUtil)).withSockJS();


	}

}
