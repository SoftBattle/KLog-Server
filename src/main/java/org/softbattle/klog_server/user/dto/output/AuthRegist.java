package org.softbattle.klog_server.user.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import static org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl.DEFAULTAVATAR;
import static org.softbattle.klog_server.user.service.serviceimpl.UserServiceImpl.DEFAULTNICKNAME;

/**
 * 注册和登录部分出参
 * @author ygx
 */
@Data
@AllArgsConstructor
public class AuthRegist {
    /**
     * 用户名
     */
    private String uid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * token
     */
    private String token;
    /**
     * 头像地址
     */
    private String avatar;

    public AuthRegist(String uid, String token){
        this.uid = uid;
        this.token = token;
        this.nickname = DEFAULTNICKNAME;
        this.avatar = DEFAULTAVATAR;
    }
}
