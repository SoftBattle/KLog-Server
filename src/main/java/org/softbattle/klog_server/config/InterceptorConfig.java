package org.softbattle.klog_server.config;

import org.softbattle.klog_server.user.Handler.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author ygx
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    public AuthenticationInterceptor authenticationInterceptor(){
        //自定义拦截器
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authenticationInterceptor())
                //拦截所有请求
                .addPathPatterns("/**");
    }
}
