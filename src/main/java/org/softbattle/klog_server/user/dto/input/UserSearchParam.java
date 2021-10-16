package org.softbattle.klog_server.user.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户分页查询入参
 * @author ygx
 */
@Data
public class UserSearchParam {
    /**
     * 关键词
     */
    String keyword;
    /**
     * 分页大小
     */
    int pageSize;
    /**
     * 请求页
     */
    int pageIndex;

}
