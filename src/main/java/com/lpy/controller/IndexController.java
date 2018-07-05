package com.lpy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lipeiyuan on 2018/7/5.
 */
@Controller
public class IndexController {

    @RequestMapping(path = {"/","/index "})
    @ResponseBody
    public String index() {
        return "Hello World";
    }

    //@RequestMapping的参数value和path是等价的，可以点进去看下两者是互为别名的
    @RequestMapping(value = "/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId ,
                          @PathVariable("userId") int userId ,
                          @RequestParam(value = "type" , defaultValue = "1") int type ,
                          @RequestParam(value = "key" , defaultValue = "lpy") String key) {

        return String.format("GID{%s} , UID{%d} , TYPE{%d} , KEY{%s}" , groupId , userId , type , key);

    }
}
