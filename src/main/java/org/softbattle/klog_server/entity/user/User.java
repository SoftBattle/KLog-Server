package org.softbattle.klog_server.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表实体类
 * @author ygx
 */
@ApiModel(value = "用户表实体类")
@Data
@NoArgsConstructor
@TableName(value = "t_user")
public class User implements Serializable {
    /**
     * 用户
     */
    @TableId(value = "userId", type = IdType.INPUT)
    private String userid;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 头像地址
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 昵称（自己先生成，后续可以改）
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 关注的人（一个数组转成json）
     */
    @TableField(value = "follows")
    private String follows;

    /**
     * 关注我的人（一个数组）
     */
    @TableField(value = "followers")
    private String followers;

    /**
     * 收藏的文章（一个数组）
     */
    @TableField(value = "stars")
    private String stars;

    private static final long serialVersionUID = 1L;

    public static final String COL_USERID = "userId";

    public static final String COL_PASSWORD = "password";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_FOLLOWS = "follows";

    public static final String COL_FOLLOWERS = "followers";

    public static final String COL_STARS = "stars";
}