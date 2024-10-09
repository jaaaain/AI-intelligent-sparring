package com.jaaaain.config;

import com.jaaaain.intercepter.AdminCheckIntercepter;
import com.jaaaain.intercepter.LoginCheckIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckIntercepter loginCheckIntercepter;
    @Autowired
    private AdminCheckIntercepter adminCheckIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle

        registry.addInterceptor(loginCheckIntercepter)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/error");

        registry.addInterceptor(adminCheckIntercepter)
                .addPathPatterns("/admin/**");
    }
}
