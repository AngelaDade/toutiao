package com.lpy.controller;

import com.lpy.aspect.LogAspect;
import com.lpy.service.UserService;
import com.lpy.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lipeiyuan on 2018/7/15.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    @ResponseBody
    public String register(@RequestParam(value = "username") String username ,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int rememberMe ,
                      HttpServletResponse response) {



        try {
            Map<String , Object> map = userService.register(username,password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                //设为全站有效
                cookie.setPath("/");
                //如果有rememberMe则吧cookie有效期延长五天,不写的话浏览器关闭就失效了
                if (rememberMe > 0) {
                    cookie.setMaxAge(3600*24*5);
                }

                response.addCookie(cookie);

                return ToutiaoUtil.getJsonString(200,"注册成功");
            } else {
                return ToutiaoUtil.getJsonString(500,map);
            }


        } catch (Exception e) {
            logger.error("注册异常"+e.getMessage());
            return ToutiaoUtil.getJsonString(500,"注册异常");
        }

    }

    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(value = "username") String username ,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int rememberMe ,
                      HttpServletResponse response) {

        Map<String,Object> map = userService.login(username , password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            //设为全站有效
            cookie.setPath("/");
            //如果有rememberMe则吧cookie有效期延长五天,不写的话浏览器关闭就失效了
            if (rememberMe > 0) {
                cookie.setMaxAge(3600*24*5);
            }

            response.addCookie(cookie);

            return ToutiaoUtil.getJsonString(200,"登录成功");
        } else {
            return ToutiaoUtil.getJsonString(500,map);
        }

    }


    @RequestMapping(value = "/logout" , method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";

    }
}
