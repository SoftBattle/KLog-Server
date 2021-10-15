package org.softbattle.klog_server.user.service;

import org.softbattle.klog_server.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 用户操作Service层接口
 * @author ygx
 */
public interface UserService extends IService<User>{
    /**
     * 用户注册
     * @param uid
     * @param passwd
     * @return
     */
    public boolean userRegist(String uid, String passwd);
}
