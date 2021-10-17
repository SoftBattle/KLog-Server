package org.softbattle.klog_server.user.Handler;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jetbrains.annotations.NotNull;
import org.softbattle.klog_server.config.NeedToken;
import org.softbattle.klog_server.config.PassToken;
import org.softbattle.klog_server.user.entity.User;
import org.softbattle.klog_server.user.mapper.UserMapper;
import org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl;
import org.softbattle.klog_server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static org.softbattle.klog_server.utils.JwtUtil.USERID;

/**
 * 用户权限拦截器逻辑
 *
 * @author ygx
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(NeedToken.class)) {
            NeedToken needToken = method.getAnnotation(NeedToken.class);
            if (needToken.required()) {
                //无token处理
                if (token == null || token.isBlank()) {
                    throw new RuntimeException("无token，请重新登录");
                }
                //token过期处理
                if (!JwtUtil.validate(token)) {
                    throw new RuntimeException("token已过期，请重新登录");
                }
                JWT jwt = JWTUtil.parseToken(token);
                if (userMapper.selectById(jwt.getPayload(USERID).toString()) == null){
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                return true;
            }
        }
        return true;
    }

}
