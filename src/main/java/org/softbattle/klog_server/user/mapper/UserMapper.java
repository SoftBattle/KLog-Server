package org.softbattle.klog_server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.softbattle.klog_server.user.entity.User;

/**
 * 用户表
 * @author ygx
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}