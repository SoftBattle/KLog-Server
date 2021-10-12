package org.softbattle.klog_server.article.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.softbattle.klog_server.article.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sun
 * @since 2021-09-28
 */
public interface IArticleService extends IService<Article> {
    //3.0请求文章列表
    IPage<Article> queryLists(String keyword, int pageSize, int pageIndex, String sort);
    //3.1浏览文章
    Article lookPost(String pid);
    //3.2新建文章
    int newArticle(Article article);
    //3.3编辑文章
    int updateArticle(String title,String subTitle,String[] banners,String[] tags,String content);
    //3.4删除文章
    int deleteArticle(String pid);
    //3.5收藏文章

    //3.6取消收藏文章

    //


}
