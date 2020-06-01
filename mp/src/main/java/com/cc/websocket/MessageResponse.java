package com.cc.websocket;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    //消息
    private String msg;
    //提醒时间
    private Date remindTime;

    public MessageResponse(){

    }

    public MessageResponse(String userName,String msg,Date remindTime){
        this.userName=userName;
        this.msg=msg;
        this.remindTime=remindTime;
    }

}
