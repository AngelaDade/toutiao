package com.lpy.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by lipeiyuan on 2018/7/9.
 * 展示切面编程，
 * 目的是想在在调用IndexController的前后打出log
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);



    //这个注解就表示在执行IndexController的所有方法前执行before方法
    @Before("execution( * com.lpy.controller.IndexController.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("before method : " + joinPoint.toString());
        StringBuilder stringBuilder = new StringBuilder();
        for(Object arg : joinPoint.getArgs()) {
            stringBuilder.append("arg:" + arg.toString() + " | ");
        }
        logger.info("args : " + stringBuilder.toString());
        logger.info("getKind : "+joinPoint.getKind());
        logger.info("getSignature : " + joinPoint.getSignature());
    }

    @After("execution( * com.lpy.controller.IndexController.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info("after method : " + joinPoint.toString());
    }

    //*通配符 也可以这样使用   *Controller  表示所有已Controller结尾的类
}
