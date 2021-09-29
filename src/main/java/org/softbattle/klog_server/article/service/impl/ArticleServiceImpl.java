package org.softbattle.klog_server.article.service.impl;

import org.softbattle.klog_server.article.entity.Article;
import org.softbattle.klog_server.article.mapper.ArticleMapper;
import org.softbattle.klog_server.article.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sun
 * @since 2021-09-28
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> queryLists(String keyword, int pageSize, int pageIndex, String aort) {

        return null;
    }

    @Override
    public int newArticle(Article article) {
        int insert = articleMapper.insert(article);
        return insert;
    }
}
