package org.softbattle.klog_server.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.softbattle.klog_server.config.NeedToken;
import org.softbattle.klog_server.config.PassToken;
import org.softbattle.klog_server.user.dto.input.ChangePassword;
import org.softbattle.klog_server.user.dto.input.FollowSearch;
import org.softbattle.klog_server.user.dto.input.UserSearchParam;
import org.softbattle.klog_server.user.dto.output.AuthRegist;
import org.softbattle.klog_server.user.dto.input.LoginInfo;
import org.softbattle.klog_server.user.dto.output.UserInfo;
import org.softbattle.klog_server.user.dto.output.UserSearchInfo;
import org.softbattle.klog_server.user.result.Result;
import org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl;
import org.softbattle.klog_server.utils.FileUtil;
import org.softbattle.klog_server.utils.JwtUtil;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
     * 预处理(一般用于/api/user/**)
     */
    public String preMethod(){
        //拿到request,response
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        HttpServletResponse httpServletResponse = servletRequestAttributes.getResponse();
        //拿到token
        String token = httpServletRequest.getHeader("token");
        //将token装入cookie
        assert httpServletResponse != null;
        JwtUtil.setCookie(httpServletResponse, token);
        //拿到token主人的uid
        return JwtUtil.getUid(token);
    }

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
        String uid = this.preMethod();
        IPage<UserSearchInfo> userSearchInfoIPage = userService.search(userSearchParam.getKeyword(), userSearchParam.getPageSize(), userSearchParam.getPageIndex(), uid);
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("users", userSearchInfoIPage.getRecords());
        //排除自己
        dataMap.put("total", userSearchInfoIPage.getTotal() - 1);
        return Result.success(null, dataMap);
    }

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @NeedToken
    @PostMapping(value = "/api/user/info")
    public Result getUserInfo(@RequestParam(value = "uid") String uid){
        String currentUid = this.preMethod();
        return Result.success(null, userService.info(uid, currentUid));

    }

    /**
     * 修改密码
     * @param changePassword
     * @return
     */
    @NeedToken
    @PutMapping(value = "/api/user/passwd")
    public Result changePassword(@RequestBody ChangePassword changePassword){
        String uid = this.preMethod();
        return userService.changePassword(uid, changePassword.getOldPasswd(), changePassword.getNewPasswd()) ? Result.success("ok", "密码修改成功") : Result.error();

    }

    /**
     * 修改昵称
     * @param nickname
     * @return
     */
    @NeedToken
    @PutMapping(value = "/api/user/nickname")
    public Result changeNickname(@RequestParam(value = "nickname") String nickname){
        String uid = this.preMethod();
        return userService.changeNickname(uid, nickname) ? Result.success() : Result.error();

    }

    /**
     * 修改头像
     * @param avatar
     * @return
     */
    @NeedToken
    @PutMapping(value = "/api/user/avatar")
    public Result changeAvatar(@RequestParam(value = "avatar") String avatar){
        String uid = this.preMethod();
        //上传过程暂不做测试
        try {
            //File转MultipartFile
            File file = new File(avatar);
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            FileUtil.upload(multipartFile);
        } catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
        return userService.changeAvatar(uid, avatar) ? Result.success() : Result.error();
    }

    /**
     * 获取关注
     * @param followSearch
     * @return
     */
    @NeedToken
    @PostMapping(value = "/api/user/follows")
    public Result getFollows(@RequestBody FollowSearch followSearch){
        IPage<UserInfo> followPage = userService.getFollows(followSearch.getUid(), followSearch.getPageSize(), followSearch.getPageIndex());
        HashMap<String, Object> ResultMap = new HashMap<>();
        ResultMap.put("items", followPage.getRecords());
        ResultMap.put("total", followPage.getTotal());
        return Result.success(null, ResultMap);
    }

    /**
     * 关注某人
     * @param uid
     * @return
     */
    @NeedToken
    @PostMapping(value = "/api/user/follow")
    public Result followUser(@RequestParam(value = "uid")String uid){
        String currentUid = this.preMethod();
        return userService.followUser(uid, currentUid) ? Result.success() : Result.error();
    }

    /**
     * 取消关注
     * @param uid
     * @return
     */
    @NeedToken
    @PostMapping(value = "/api/user/unfollow")
    public Result cancelFollow(@RequestParam(value = "uid")String uid){
        String currentUid = this.preMethod();
        return userService.cancelFollow(uid, currentUid) ? Result.success() : Result.error();
    }
}
