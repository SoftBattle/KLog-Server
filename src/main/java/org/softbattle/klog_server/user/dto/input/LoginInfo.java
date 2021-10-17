package org.softbattle.klog_server.user.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注册和登录入参
 * @author ygx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    /**
     * 用户id
     */
    String uid;
    /**
     * 密码
     */
    String passwd;
}
