package org.softbattle.klog_server.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.softbattle.klog_server.entity.user.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}