package com.lpy.intercepter;

import com.lpy.constant.TicketStatus;
import com.lpy.dao.LoginTicketDao;
import com.lpy.dao.UserDao;
import com.lpy.model.HostHolder;
import com.lpy.model.LoginTicket;
import com.lpy.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by lipeiyuan on 2018/7/17.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;


    //请求处理之前（进入Controller之前）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                }
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            //有票但是过期或者无效就当做无事发生 继续执行
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() == TicketStatus.UNVALID) {
                return true;
            }
            //有票且有效的话，则把User保存到ThreadLocal，从而使之后其他的方法都可以用到该用户的信息
            //每次访问登录Controller的都是一个线程
            //1、方便传参，不用一直在方法中作为参数传下去    2、不同线程的存储区分开，从而支持多线程
            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);


        }



        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
