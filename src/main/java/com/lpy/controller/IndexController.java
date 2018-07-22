package com.lpy.controller;

import com.lpy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by lipeiyuan on 2018/7/5.
 *
 * 用来测试各种请求
 */
@Controller
public class IndexController {


    @RequestMapping(path = {"/hello"})
    @ResponseBody
    public String hello(HttpSession session) {
        return "Hello World , jump from " + session.getAttribute("msg");
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

    @RequestMapping(value = "/template")
    public String news() {
        return "news";
    }

    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request ,
                          HttpServletResponse response ,
                          HttpSession session) {

        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            stringBuilder.append(name + " : " +request.getHeader(name) + "<br>");
        }

        for (Cookie cookie : request.getCookies()) {
            stringBuilder.append("Cookie : ");
            stringBuilder.append(cookie.getName() + " : ");
            stringBuilder.append(cookie.getValue());
            stringBuilder.append("<br/>");
        }

        return stringBuilder.toString();
    }

    //@CookieValue注解可以读取cookie中的键值对
    //并且@CookieValue必须要有defaultValue，不然因为第一次请求cookie中没有该键值对，会出现路径匹配的函数参数错误
    @RequestMapping(value = "/response")
    @ResponseBody
    public String response(@CookieValue(value = "nowCoderId" , defaultValue = "a") String nowCoderId ,
                           @RequestParam(value = "key" , defaultValue = "key0") String key ,
                           @RequestParam(value = "value" , defaultValue = "value0") String value ,
                           HttpServletResponse response) {
        response.addHeader(key , value);
        response.addCookie(new Cookie(key , value));
        return "NowCoderID from Cookie : " + nowCoderId;
    }

    //重定向方式一
    @RequestMapping(value = "/redirect/{code}")
    public RedirectView redirect(@PathVariable(value = "code") int code) {
        RedirectView redirectView = new RedirectView("/",true);
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
            return redirectView;
        } else {
            return redirectView;
        }
    }

    //重定向方式二
    //用redirect前缀来表示重定向 此时不能再添加@ResponseBody注解
    //session 是打开浏览器访问到关掉浏览器的期间回话，如果新开无痕窗口相当于重新与服务器建立联系
    //新建标签页也相当于是同一次会话
    @RequestMapping(value = "/redirect2")
    public String redirect2(HttpSession httpSession) {
        httpSession.setAttribute("msg" , "jump from redirect");
        return "redirect:/";
    }

    @RequestMapping(value = "/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key" , required = true) String key) {
        if (key.equals("admin")) {
            return "Hello admin";
        } else {
            throw new IllegalArgumentException("key 错误");
        }
    }

    //统一的异常处理
    @ExceptionHandler
    @ResponseBody
    public String error(Exception exception) {
        return "出现错误：" + exception.getMessage();
    }
}
