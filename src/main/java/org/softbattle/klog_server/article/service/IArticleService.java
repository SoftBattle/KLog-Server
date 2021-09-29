package org.softbattle.klog_server.article.service;

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
    //请求文章列表
    List<Article> queryLists(String keyword,int pageSize,int pageIndex,String aort);
    //浏览文章

    //新建文章
    int newArticle(Article article);
    //编辑文章

    //删除文章

    //收藏文章

    //取消收藏文章

    //

}
