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

    @Resource
    private UserMapper userMapper;


    /**
     * 默认头像地址
     */
    public static final String DEFAULTAVATAR = "default.png";
    /**
     * 默认用户昵称前缀
     */
    public static final String DEFAULTNICKNAME = "用户";

    /**
     * 用户注册
     *
     * @param uid
     * @param passwd
     * @return
     */
    @Override
    public boolean userRegist(String uid, String passwd) {
        if(userMapper.selectById(uid) != null) {return false;}
        else {
            User user = new User();
            user.setUserid(uid);
            user.setPassword(passwd);
            user.setAvatar(DEFAULTAVATAR);
            user.setNickname(DEFAULTNICKNAME + uid);
            userMapper.insert(user);
            return true;
        }
    }

    /**
     * 用户登录
     * 0：正常
     * 1：用户不存在
     * 2：用户存在但密码错误
     * @param uid
     * @param passwd
     * @return
     */
    @Override
    public int userLogin(String uid, String passwd) {
        User user = userMapper.selectById(uid);
        if(user == null){
            return 1;
        }
        if(!passwd.equals(user.getPassword())){
            return 2;
        }
        return 0;
    }
}
