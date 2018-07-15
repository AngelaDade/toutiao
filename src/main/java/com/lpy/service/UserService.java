package com.lpy.service;

import com.lpy.dao.UserDao;
import com.lpy.model.User;
import com.lpy.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        return map;

    }




}
