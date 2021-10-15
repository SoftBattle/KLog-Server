package org.softbattle.klog_server.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

/**
 * token操作工具类
 * @author ygx
 */
public class JwtUtil {

    /**
     * 用户id字段名
     */
    private static final String USERID = "uid";

    /**
     * 根据用户id生成jwt
     */
    public static String jwtCreate(String uid){
        String jwtToken = JWT.create()
                .setPayload(USERID, uid)
                //不签名
                .setSigner(JWTSignerUtil.none())
                //设置签发时间
                .setIssuedAt(DateUtil.date())
                //设置过期时间 1小时以后
                .setExpiresAt(DateUtil.offsetHour(DateUtil.date(), 1))
                .sign();
        return jwtToken;
    }

    /**
     * 从token中取出用户id
     */
    public static String getUid(String jwtToken){
        JWT jwt = JWTUtil.parseToken(jwtToken);
        return jwt.getPayload(USERID).toString();
    }

    /**
     * 将token放入请求头的cookie
     */
    public static void setCookie(HttpServletResponse response, String jwtToken){
        Cookie cookie = new Cookie("token", jwtToken);
        //7天过期
        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);
    }

    /**
     * token过期处理
     * 从请求的requset里拿出token，过期后放入同一个请求的response中
     */
    public static void tokenExpire(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("token");
        if (token != null){
            JWT jwt = JWTUtil.parseToken(token);
            jwt.setExpiresAt(DateUtil.date());
            String newToken = jwt.sign();
            JwtUtil.setCookie(response, newToken);
        }

    }

    /**
     * token验证
     */
    public static boolean validate(String jwtToken){
        try {
            JWTValidator.of(jwtToken).validateDate();
            return true;
        }catch (ValidateException e){
            return false;
        }
    }
}
