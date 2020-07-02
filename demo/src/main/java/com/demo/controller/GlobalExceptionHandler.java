package com.demo.controller;

import com.demo.constants.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cc
 * @Date 2020/6/24 16:54
 * @Version 1.0
 * 全局异常处理
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Response exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("出现异常！原因是:",e);
        return Response.error(e.toString());
    }
}
