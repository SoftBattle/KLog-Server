package org.softbattle.klog_server.config;

import org.softbattle.klog_server.user.Handler.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置
 * token验证
 * @author ygx
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public AuthenticationInterceptor authenticationInterceptor(){
        //自定义拦截器
        return authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authenticationInterceptor())
                //拦截所有请求
                .addPathPatterns("/**");
    }
}
