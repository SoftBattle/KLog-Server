package org.softbattle.klog_server.user.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户分页查询出参
 * @author ygx
 */
@Data
public class UserSearchInfo {
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
    /**
     * 是否已被搜索者关注
     */
    boolean follow = false;
}
