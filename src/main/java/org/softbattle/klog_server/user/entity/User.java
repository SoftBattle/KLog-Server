package org.softbattle.klog_server.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 用户表实体类
 * @author ygx
 */
@ApiModel(value="t_user")
@Data
@NoArgsConstructor
@TableName(value = "t_user")
public class User implements Serializable {
    /**
     * 用户
     */
    @TableId(value = "userId", type = IdType.INPUT)
    @ApiModelProperty(value="用户")
    @Version
    @NotNull
    @Size(max = 32)
    private String userid;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @ApiModelProperty(value="密码")
    @NotEmpty
    @Size(max = 32)
    private String password;

    /**
     * 头像地址
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像地址")
    private String avatar;

    /**
     * 昵称（自己先生成，后续可以改）
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value="昵称（自己先生成，后续可以改）")
    @Size(max = 128)
    private String nickname;

    /**
     * 关注的人（一个数组转成json）
     */
    @TableField(value = "follows")
    @ApiModelProperty(value="关注的人（一个数组转成json）")
    private String follows;

    /**
     * 关注我的人（一个数组）
     */
    @TableField(value = "followers")
    @ApiModelProperty(value="关注我的人（一个数组）")
    private String followers;

    /**
     * 收藏的文章（一个数组）
     */
    @TableField(value = "stars")
    @ApiModelProperty(value="收藏的文章（一个数组）")
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