package org.softbattle.klog_server.user.dto.input;

import lombok.Data;

/**
 * 修改密码入参
 * @author ygx
 */
@Data
public class ChangePassword {
    /**
     * 旧密码
     */
    String oldPasswd;
    /**
     * 新密码
     */
    String newPasswd;
}
