package org.softbattle.klog_server.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.softbattle.klog_server.user.dto.output.UserInfo;
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

    /**
     * 获取用户信息
     * @param uid
     * @param currentUid
     * @return
     */
    UserInfo info(String uid, String currentUid);

    /**
     * 修改密码
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    boolean changePassword(String uid, String oldPassword, String newPassword);

    /**
     * 修改昵称
     * @param uid
     * @param nickname
     * @return
     */
    boolean changeNickname(String uid, String nickname);

    /**
     * 修改头像
     * @param uid
     * @param avatar
     * @return
     */
    boolean changeAvatar(String uid, String avatar);

    /**
     * 获取关注
     * @param uid
     * @param pageSize
     * @param pageIndex
     * @param currentUid
     * @return
     */
    IPage<UserInfo> getFollows(String uid, int pageSize, int pageIndex, String currentUid);

}
