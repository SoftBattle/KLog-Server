package org.softbattle.klog_server.user.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 搜索关注列表入参
 * @author ygx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowSearch {
    /**
     * 用户id
     */
    String uid;
    /**
     * 分页大小
     */
    int pageSize;
    /**
     * 当前页
     */
    int pageIndex;
}
