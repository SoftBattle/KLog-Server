package org.softbattle.klog_server.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.softbattle.klog_server.user.dto.output.UserSearchInfo;
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
    boolean userRegist(String uid, String passwd);

    /**
     * 用户登录
     * @param uid
     * @param passwd
     * @return
     */
    int userLogin(String uid, String passwd);

    /**
     * 搜索用户
     * @param keyword
     * @param pageSize
     * @param pageIndex
     * @return
     */
    IPage<UserSearchInfo> search(String keyword, int pageSize, int pageIndex, String uid);

}
