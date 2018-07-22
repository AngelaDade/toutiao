package com.lpy.controller;

import com.lpy.model.HostHolder;
import com.lpy.model.News;
import com.lpy.model.ViewObject;
import com.lpy.service.NewsService;
import com.lpy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by lipeiyuan on 2018/7/14.
 */
@Controller
public class HomeController {


    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(value = {"/thymeleaf"})
    public String testThymeleaf(Model model) {
        model.addAttribute("value1" , "lpy");
        List<String> colors = Arrays.asList(new String[]{"red","green","blue"});
        Map user= new HashMap();
        user.put("name", "姓名");
        user.put("age", "年龄");
        user.put("sex", "性别");
        user.put("birthday", "生日");
        user.put("phonenumber", "手机");
        model.addAttribute("userhead", user);
        List userinfo =new ArrayList();
        userinfo.add("周美玲");
        userinfo.add("32");
        userinfo.add("女");
        userinfo.add("1985");
        userinfo.add("19850014665");
        model.addAttribute("userinfo", userinfo);
        List outerList=new ArrayList<>();
        Map innerMap=new HashMap<>();
        for (int i = 0; i < 10; i++) {
            innerMap.put("1", i);
            innerMap.put("1", i++);
            i++;
            outerList.add(innerMap);
        }
        model.addAttribute("listmap", outerList);

        model.addAttribute("colors",colors);

        return "thymeleafTest";
    }

    @RequestMapping(value = {"/","/home"})
    @ResponseBody
    public Map<String , Object> index() {
        List<News> newsList = newsService.getLatestNews(0,0,10);
//        List<ViewObject> viewObjects = new ArrayList<>();
        //把主页上一条数据关联的都放到ViewObject里面
//        for (News news : newsList) {
//            ViewObject viewObject = new ViewObject();
//            viewObject.set("news" , news);
//            viewObject.set("user" , userService.getUserById(news.getUserId()));
//
//            viewObjects.add(viewObject);
//        }
//        model.addAttribute("vos" , viewObjects);
        Map<String , Object> map = new HashMap<>();
        map.put("news" , newsList.get(0));
        if (hostHolder.getUser() != null)
        map.put("user",hostHolder.getUser().getName());


        return map;
    }
}
