package org.softbattle.klog_server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.softbattle.klog_server.user.dto.output.UserSearchInfo;
import org.softbattle.klog_server.user.entity.User;

/**
 * 用户表
 * @author ygx
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 关键词模糊搜索用户
     * id和昵称模糊包含的用户,排除自己
     * @param page
     * @param keyword
     * @param uid
     * @return
     */
    IPage<UserSearchInfo> userPage(Page<?> page, @Param("keyword") String keyword, @Param("uid") String uid);
}