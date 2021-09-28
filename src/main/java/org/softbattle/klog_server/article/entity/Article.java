package org.softbattle.klog_server.article.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sun
 * @since 2021-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    @TableId("articleId")
    private Integer articleId;

    /**
     * 作者id  userId
     */
    private String arthor;

    /**
     * 主标题
     */
    private String title;

    /**
     * 副标题
     */
    @TableField("subTitle")
    private String subTitle;

    /**
     * 文章banner图，图片连接,是个数组
     */
    private String banner;

    /**
     * 标签数组
     */
    private String tags;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDate ctim;

    /**
     * 浏览数
     */
    private String views;


}
