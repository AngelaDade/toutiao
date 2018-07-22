package com.lpy.configuration;

import com.lpy.intercepter.LoginRequiredInterceptor;
import com.lpy.intercepter.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by lipeiyuan on 2018/7/18.
 * 配置加入passport拦截器
 *
 */
@Component
public class ToutiaoWebConfiguaration implements WebMvcConfigurer {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册自定义拦截器，添加拦截路径和排除拦截路径（不添加路径的话就是全局的，所有uri都起作用）
        registry.addInterceptor(passportInterceptor);


        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/settings");
        

    }
}
