package com.lpy.model;

import org.springframework.stereotype.Component;

/**
 * Created by lipeiyuan on 2018/7/17.
 * 用来存当前登录的用户
 * 1、方便传参，不用一直在方法中作为参数传下去    2、不同线程的存储区分开，从而支持多线程
 */

@Component
public class HostHolder {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public User getUser() {
        return userThreadLocal.get();
    }

    public void setUser(User user) {
        userThreadLocal.set(user);
    }

    public void clear() {
        userThreadLocal.remove();
    }



}
