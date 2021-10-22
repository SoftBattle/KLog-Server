package org.softbattle.klog_server.user.service.serviceimpl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.softbattle.klog_server.user.dto.output.UserInfo;
import org.softbattle.klog_server.user.dto.output.UserSearchInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.softbattle.klog_server.user.mapper.UserMapper;
import org.softbattle.klog_server.user.entity.User;
import org.softbattle.klog_server.user.service.UserService;

/**
 * 用户操作Service层实现类
 *
 * @author ygx
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
        if (userMapper.selectById(uid) != null) {
            return false;
        } else {
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
     *
     * @param uid
     * @param passwd
     * @return
     */
    @Override
    public int userLogin(String uid, String passwd) {
        User user = userMapper.selectById(uid);
        if (user == null) {
            return 1;
        }
        if (!passwd.equals(user.getPassword())) {
            return 2;
        }
        return 0;
    }

    /**
     * 搜索用户
     *
     * @param keyword
     * @param pageSize
     * @param pageIndex
     * @param uid
     * @return
     */
    @Override
    public IPage<UserSearchInfo> search(String keyword, int pageSize, int pageIndex, String uid) {
        Page<UserSearchInfo> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageIndex);
        //拿到搜索结果后逐个判断是否已关注
        IPage<UserSearchInfo> userSearchInfoIPage = userMapper.userPage(page, keyword, uid);
        List<UserSearchInfo> records = userSearchInfoIPage.getRecords();
        if (records == null) {
            return userSearchInfoIPage;
        }
        //把自己排除
        records.removeIf(userSearchInfo -> uid.equals(userSearchInfo.getUid()));
        String follows = userMapper.selectById(uid).getFollows();
        if (follows != null) {
            //用HashSet判断
            HashSet<String> followSet = new HashSet<>(JSON.parseArray(follows, String.class));
            //uid放不进的就是已关注
            for (UserSearchInfo record : records) {
                if (!followSet.add(record.getUid())) {
                    record.setFollow(true);
                }
            }
        }
        //测试
//        IPage<UserSearchInfo> userSearchInfoIPageTest = userMapper.userPage(page, keyword, uid);
//        System.out.println("************************************");
//        System.out.println(userSearchInfoIPageTest.equals(userSearchInfoIPage));

        return userSearchInfoIPage;
    }

    /**
     * 获取用户信息
     *
     * @param uid
     * @param currentUid
     * @return
     */
    @Override
    public UserInfo info(String uid, String currentUid) {
        //uid为空时返回token主人信息
        String searchUid = (uid != null && !uid.isBlank()) ? uid : currentUid;
        User searchUser = userMapper.selectById(searchUid);
        if (searchUser == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(searchUser.getUserid());
        userInfo.setNickname(searchUser.getNickname());
        userInfo.setAvatar(searchUser.getAvatar());
        return userInfo;
    }

    /**
     * 修改密码
     *
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public boolean changePassword(String uid, String oldPassword, String newPassword) {
        User user = userMapper.selectById(uid);
        if (!oldPassword.equals(user.getPassword())) {
            return false;
        }
        user.setPassword(newPassword);
        try {
            userMapper.updateById(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改昵称
     *
     * @param uid
     * @param nickname
     * @return
     */
    @Override
    public boolean changeNickname(String uid, String nickname) {
        User user = userMapper.selectById(uid);
        try {
            user.setNickname(nickname);
            userMapper.updateById(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改头像
     *
     * @param uid
     * @param avatar
     * @return
     */
    @Override
    public boolean changeAvatar(String uid, String avatar) {
        User user = userMapper.selectById(uid);
        try {
            user.setAvatar(avatar);
            userMapper.updateById(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取关注
     *
     * @param uid
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @Override
    public IPage<UserInfo> getFollows(String uid, int pageSize, int pageIndex) {
        User user = userMapper.selectById(uid);
        if (user == null) {
            return null;
        }
        String follows = user.getFollows();
        //setRecords
        List<UserInfo> resultList = new ArrayList<>();
        if (follows != null) {
            //拿到关注uid列表
            List<String> followList = new ArrayList<>(JSON.parseArray(follows, String.class));
            for (String follow : followList) {
                resultList.add(userMapper.userInfoSearch(follow));
            }
        }
        //手动构造IPage以利用自动分页机制
        IPage<UserInfo> userInfoIPage = new Page<>(pageIndex, pageSize, resultList.size());
        userInfoIPage.setRecords(resultList);

        return userInfoIPage;
    }

    /**
     * 关注某人
     *
     * @param uid
     * @param currentUid
     * @return
     */
    @Override
    public boolean followUser(String uid, String currentUid) {
        User user = userMapper.selectById(uid);
        User currentUser = userMapper.selectById(currentUid);
        if (user == null) {
            return false;
        }
        //在自己关注列表里添加对方
        String follows = currentUser.getFollows();
        List<String> followList;
        if (follows != null) {
            followList = new ArrayList<>(JSON.parseArray(follows, String.class));
            //自己还没关注目标
            if (!followList.contains(uid)) {
                followList.add(uid);
            }
        } else {
            followList = new ArrayList<>();
            followList.add(uid);
        }
        currentUser.setFollows(JSONUtil.toJsonStr(followList));
        userMapper.updateById(currentUser);
        //在对方粉丝列表里加入自己
        String followers = user.getFollowers();
        List<String> followerList;
        if (followers != null) {
            followerList = new ArrayList<>(JSON.parseArray(followers, String.class));
            //目标用户还没被你关注
            if (!followerList.contains(currentUid)) {
                followerList.add(currentUid);
            }
        } else {
            followerList = new ArrayList<>();
            followerList.add(currentUid);
        }
        user.setFollowers(JSONUtil.toJsonStr(followerList));
        userMapper.updateById(user);
        return true;

    }

    /**
     * 取关
     *
     * @param uid
     * @param currentUid
     * @return
     */
    @Override
    public boolean cancelFollow(String uid, String currentUid) {
        User user = userMapper.selectById(uid);
        User currentUser = userMapper.selectById(currentUid);
        if (user == null) {
            return false;
        }
        String follows = currentUser.getFollows();
        if (follows != null) {
            List<String> followList = new ArrayList<>(JSON.parseArray(follows, String.class));
            //如果自己关注列表有对方，则删除，否则报错
            if (!followList.remove(uid)) {
                return false;
            }
            //更新
            currentUser.setFollows(JSONUtil.toJsonStr(followList));
            userMapper.updateById(currentUser);
        } else {
            return false;
        }
        String followers = user.getFollowers();
        if (followers != null){
            List<String> followerList = new ArrayList<>(JSON.parseArray(followers, String.class));
            //如果对方粉丝列表有自己，则删除
            if (!followerList.remove(currentUid)){
                return false;
            }
            user.setFollowers(JSONUtil.toJsonStr(followerList));
            userMapper.updateById(user);
        }else {
            return false;
        }
        return true;
    }

    /**
     * 收藏文章
     *
     * @param pid
     * @param currentUid
     * @return
     */
    @Override
    public boolean starArtical(String pid, String currentUid) {
        User currentUser = userMapper.selectById(currentUid);
        if (currentUid == null){
            return false;
        }
        String starArticals = currentUser.getStars();
        List<String> starList;
        if (starArticals != null){
            starList = new ArrayList<>(JSON.parseArray(starArticals, String.class));
            //如果没收藏过则收藏
            if (!starList.contains(pid)){
                starList.add(pid);
            }
        }else {
            starList = new ArrayList<>();
            starList.add(pid);
        }
        return true;
    }
}
