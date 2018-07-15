package com.lpy.controller;

import com.lpy.aspect.LogAspect;
import com.lpy.service.UserService;
import com.lpy.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by lipeiyuan on 2018/7/15.
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/reg" , method = RequestMethod.GET)
    @ResponseBody
    public String reg(@RequestParam(value = "username") String username ,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int rememberMe  ) {



        try {
            Map<String , Object> map = userService.register(username,password);
            if (map.isEmpty()) {
                return ToutiaoUtil.getJsonString(200,"注册成功");
            } else {
                return ToutiaoUtil.getJsonString(500,map);
            }


        } catch (Exception e) {
            logger.error("注册异常"+e.getMessage());
            return ToutiaoUtil.getJsonString(500,"注册异常");
        }




    }

}
