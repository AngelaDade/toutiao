package com.lpy.configuration;

import com.lpy.intercepter.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by lipeiyuan on 2018/7/18.
 * 配置加入passport拦截器
 *
 */
public class ToutiaoWebConfiguaration implements WebMvcConfigurer {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        

    }
}
