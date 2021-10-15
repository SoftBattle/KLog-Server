package org.softbattle.klog_server.user.controller;

import io.swagger.annotations.Api;
import org.softbattle.klog_server.config.PassToken;
import org.softbattle.klog_server.user.dto.AuthRegist;
import org.softbattle.klog_server.user.result.Result;
import org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl;
import org.softbattle.klog_server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户操作控制层
 * @author ygx
 */
@Api(tags = "用户操作管理")
@RestController
public class UserRest {

    @Resource
    private UserServiceImpl userService;


    /**
     * 用户注册
     * @param uid
     * @param passwd
     * @return
     */
    @PassToken
    @PostMapping(value = "/api/auth/regist")
    public Result regist(@RequestParam(value = "uid") String uid, @RequestParam(value = "passwd") String passwd){
        if(!userService.userRegist(uid, passwd)){
            return Result.error();
        }
        else {
            try{
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletResponse httpServletResponse = servletRequestAttributes.getResponse();
                String token = JwtUtil.jwtCreate(uid);
                JwtUtil.setCookie(httpServletResponse, token);
                AuthRegist authRegist = new AuthRegist(uid, token);
                return Result.success("注册成功", authRegist);
            }catch (Exception e){
                return Result.error();
            }
        }
    }
}
