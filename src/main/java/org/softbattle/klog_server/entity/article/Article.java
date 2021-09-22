package org.softbattle.klog_server.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "文章表")
@Data
@NoArgsConstructor
@TableName(value = "t_article")
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId(value = "articleId", type = IdType.INPUT)
    private Integer articleid;

    /**
     * 作者id  userId
     */
    @TableField(value = "arthor")
    private String arthor;

    /**
     * 主标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 副标题
     */
    @TableField(value = "subTitle")
    private String subtitle;

    /**
     * 文章banner图，图片连接,是个数组
     */
    @TableField(value = "banner")
    private String banner;

    /**
     * 标签数组
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "ctim")
    private Date ctim;

    /**
     * 浏览数
     */
    @TableField(value = "views")
    private String views;

    private static final long serialVersionUID = 1L;

    public static final String COL_ARTICLEID = "articleId";

    public static final String COL_ARTHOR = "arthor";

    public static final String COL_TITLE = "title";

    public static final String COL_SUBTITLE = "subTitle";

    public static final String COL_BANNER = "banner";

    public static final String COL_TAGS = "tags";

    public static final String COL_CONTENT = "content";

    public static final String COL_CTIM = "ctim";

    public static final String COL_VIEWS = "views";
}