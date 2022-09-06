package com.github.xzb617.cappuccino.server.aoplog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xzb617.cappuccino.server.security.SubjectContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
public class LoggerAspectj {

    private final LoggerService loggerService;
    private final ObjectMapper objectMapper;

    public LoggerAspectj(LoggerService loggerService, ObjectMapper objectMapper) {
        this.loggerService = loggerService;
        this.objectMapper = objectMapper;
    }

    /**
     * 切入点，针对有 Logger 注解的方法进行切入
     */
    @Pointcut("@annotation(com.github.xzb617.cappuccino.server.aoplog.Logger)")
    public void pointCut(){}


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取注解
        Logger logger = this.getAnnotation(pjp);
        if (logger == null) {
            return pjp.proceed();
        }
        // 相关参数
        String opUser = SubjectContextHolder.getContext().getUsername();
        String opDesc = logger.desc();
        String reqURI = this.getURI();
        Signature signature = pjp.getSignature();
        String opMethod = signature.toString().split(" ")[1];
        String opArgs = null;
        if (!logger.shieldArgs()) {
            Object[] args = pjp.getArgs();
            opArgs = this.objectMapper.writeValueAsString(args);
            if (opArgs!=null && args.length==1) {
                // 当参数只有一个时，去掉首尾的中括号
                opArgs = opArgs.substring(1, opArgs.length()-1);
            }
        } else {
            opArgs = "******";
        }
        Date opTime = new Date();
        // 执行程序
        Object result = pjp.proceed();
        // 处理日志信息
        this.loggerService.saveAopLog(reqURI, opMethod, opArgs, opDesc, opUser, opTime);
        // 返回解析后的结果
        return result;
    }

    /**
     * 获取注解
     * @param point
     * @return
     */
    private Logger getAnnotation(JoinPoint point) {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            return method!=null ? method.getAnnotation(Logger.class) : null;
        }
        return null;
    }

    /**
     * 获取API的URI地址
     * @return
     */
    private String getURI() {
        HttpServletRequest request = this.getRequest();
        return request!=null ? request.getRequestURI():"";
    }

    /**
     * 获取 HttpServletRequest
     * @return
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return attributes!=null ? attributes.getRequest() : null;
    }
}
