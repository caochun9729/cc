package com.cc.aspect;

import com.alibaba.fastjson.JSON;
import com.cc.dao.MyLogMapper;
import com.cc.entity.SysLog;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * 系统日志：切面处理类
 *
 * @author cc
 */
@Aspect
@Component
public class TransactionManageAspect {

    private static final Logger log = LoggerFactory.getLogger(TransactionManageAspect.class);

    /**
     * 操作数据库
     */
    @Autowired
    private MyLogMapper myLogMapper;

    @Pointcut(value = "execution(* *com.alm.controller.*Controller.*(..))")
    public void pointCut() {
    }

    /**
     * 不同的通知,要用指定的参数,如果不用,则会报错error at ::0 formal unbound in pointcut
     *
     * @param joinPoint
     */
    @Around(value = "pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long beginTime = System.currentTimeMillis();
        // 获得注释的值
        ApiOperation annotation =
                ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ApiOperation.class);
        StringBuilder annotationValue = new StringBuilder("");
        if (annotation != null) {
            annotationValue.append(annotation.value().trim());
        }
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 获取请求地址
        Object requestPath = request.getRequestURI();
        // 获得用户名
        String userName = request.getHeader("userName");

        String ip = getIpAddr(request);
        // 格式换开始时间

        // String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date optTime = new Date();
        // 参数
        Object paramObj = joinPoint.getArgs();
        String param =
                paramObj == null || "".equals(paramObj)
                        ? "无参数"
                        : JSON.toJSONString(paramObj);
        // 返回结果
        Object resultObj = joinPoint.proceed();
        String result =
                paramObj == null || "".equals(resultObj)
                        ? "无返回值"
                        : JSON.toJSONString(resultObj);
        // 获取切点方法对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取类
        String className = joinPoint.getTarget().getClass().getName();
        // 获取方法名
        String methodName = method.getName();
        SysLog logs = new SysLog();
        logs.setIp(ip);
        logs.setUsername(userName);
        logs.setMethod(className.concat(".").concat(methodName).concat("()"));
        logs.setOperation(annotationValue.toString());
        logs.setParams(param);
        logs.setCreateDate(optTime);
        myLogMapper.insert(logs);
        return resultObj;
    }

    @AfterThrowing(throwing = "ex", pointcut = "pointCut()")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
        long beginTime = System.currentTimeMillis();
        // 格式换开始时间
        // String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date optTime = new Date();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 获取请求地址
        Object requestPath = request.getRequestURI();
        String ip = getIpAddr(request);
        // 获得注释的值
        ApiOperation annotation =
                ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ApiOperation.class);
        StringBuilder annotationValue = new StringBuilder("");
        if (annotation != null) {
            annotationValue.append(annotation.value());
        }
        // 获得用户id
        String userName = request.getHeader("userName");

        // 参数
        Object paramObj = joinPoint.getArgs();
        String param =
                paramObj == null || "".equals(paramObj)
                        ? "无参数"
                        : JSON.toJSONString(paramObj);
        // 获取切点方法对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取方法名
        String methodName = method.getName();
        SysLog logs = new SysLog();
        logs.setIp(ip);
        logs.setUsername(userName);
        logs.setMethod(className.concat(".").concat(methodName).concat("()"));
        logs.setOperation(annotationValue.toString());
        logs.setParams(param);
        logs.setCreateDate(optTime);
        myLogMapper.insert(logs);
    }

    /**
     * 获取IP地址
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


}
