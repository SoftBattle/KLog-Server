package org.softbattle.klog_server.user.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户信息
 * @author ygx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /**
     * 用户id
     */
    String uid;
    /**
     * 昵称
     */
    String nickname;
    /**
     * 头像
     */
    String avatar;
}
