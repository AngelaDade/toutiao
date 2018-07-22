package com.lpy.service;

import com.lpy.constant.TicketStatus;
import com.lpy.dao.LoginTicketDao;
import com.lpy.dao.UserDao;
import com.lpy.model.LoginTicket;
import com.lpy.model.User;
import com.lpy.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lipeiyuan on 2018/7/10.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;


    public User getUserById(int id) {
        return userDao.selectById(id);
    }

    public Map<String,Object> register(String username , String password) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            map.put("msgName","用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msgPassword","密码不能为空");
            return map;
        }
        if (userDao.selectByname(username) != null) {
            map.put("msgName","用户名已经注册");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));

        //存储数据库的密码为把用户输入的明文密码+salt进行MD5
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);

        //注册成功后直接登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }

    public Map<String,Object> login(String username , String password) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            map.put("msgName","登录用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msgPassword","登录密码不能为空");
            return map;
        }
        User user = userDao.selectByname(username);
        if (user == null) {
            map.put("msgName","用户名不存在");
            return map;
        }
        if (!ToutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msgPwd","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);



        return map;

    }

    public void logout(String ticket ) {
        loginTicketDao.updateStatus(TicketStatus.UNVALID , ticket);

    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        //0表示有效，
        ticket.setStatus(TicketStatus.VALID);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));

        //new Date()后getTime获取的就是当前时间
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }




}
