package org.softbattle.klog_server.user.service.serviceimpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.softbattle.klog_server.user.mapper.UserMapper;
import org.softbattle.klog_server.user.entity.User;
import org.softbattle.klog_server.user.service.UserService;
/**
 * 用户操作Service层实现类
 * @author ygx
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
