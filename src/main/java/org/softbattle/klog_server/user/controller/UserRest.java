package org.softbattle.klog_server.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.softbattle.klog_server.config.NeedToken;
import org.softbattle.klog_server.config.PassToken;
import org.softbattle.klog_server.user.dto.input.UserSearchParam;
import org.softbattle.klog_server.user.dto.output.AuthRegist;
import org.softbattle.klog_server.user.dto.input.LoginInfo;
import org.softbattle.klog_server.user.dto.output.UserSearchInfo;
import org.softbattle.klog_server.user.mapper.UserMapper;
import org.softbattle.klog_server.user.result.Result;
import org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl;
import org.softbattle.klog_server.utils.JwtUtil;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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
     * @param loginInfo
     * @return
     */
    @PassToken
    @PostMapping(value = "/api/auth/regist")
    public Result regist(@RequestBody LoginInfo loginInfo){
        String uid = loginInfo.getUid();
        String passwd = loginInfo.getPasswd();
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

    /**
     * 用户登录
     * @param loginInfo
     * @return
     */
    @PassToken
    @PostMapping(value = "/api/auth/login")
    public Result login(@RequestBody LoginInfo loginInfo){
        String uid = loginInfo.getUid();
        String passwd = loginInfo.getPasswd();
        switch (userService.userLogin(uid, passwd)){
            case 1: return Result.error("User_Not_Exist", "用户不存在");

            case 2: return Result.error("User_Passwd_Incorrect", "账户或密码错误");

            case 0:{
                try{
                    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpServletResponse httpServletResponse = servletRequestAttributes.getResponse();
                    String token = JwtUtil.jwtCreate(uid);
                    JwtUtil.setCookie(httpServletResponse, token);
                    AuthRegist authRegist = new AuthRegist(uid, token);
                    return Result.success("登录成功", authRegist);
                }catch (Exception e){
                    return Result.error();
                }
            }
            default: return Result.error();
        }
    }

    /**
     * 登出
     * @return
     */
    @NeedToken
    @PostMapping(value = "/api/auth/logout")
    public Result logout(){
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse httpServletResponse = servletRequestAttributes.getResponse();
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            String oldToken = httpServletRequest.getHeader("token");
            if (oldToken != null) {
                String newToken = JwtUtil.tokenExpire(oldToken);
                JwtUtil.setCookie(httpServletResponse, newToken);
            }
            return Result.success("ok", "登出成功");
        }catch (Exception e){
            return Result.error();
        }
    }

    /**
     * 分页查找用户
     * @param userSearchParam
     * @return
     */
    @NeedToken
    @PostMapping(value = "/api/user/search")
    public Result search(@RequestBody UserSearchParam userSearchParam){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        String uid = JwtUtil.getUid(httpServletRequest.getHeader("token"));
        IPage<UserSearchInfo> userSearchInfoIPage = userService.search(userSearchParam.getKeyword(), userSearchParam.getPageSize(), userSearchParam.getPageIndex(), uid);
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("users", userSearchInfoIPage.getRecords());
        dataMap.put("total", userSearchInfoIPage.getTotal());
        return Result.success(null, dataMap);
    }
}
