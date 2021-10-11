package org.softbattle.klog_server.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.softbattle.klog_server.article.entity.Article;
import org.softbattle.klog_server.article.mapper.ArticleMapper;
import org.softbattle.klog_server.article.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
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
    public IPage<Article> queryLists(String keyword, int pageSize, int pageIndex, String sort) {
        /**
         * 获取所有文章，按照keyword搜索(文章内容、tags、标题、副标题)，
         * 通过pageSize与pageIndex返回对应页数据，当keyword为空时，搜索全部
         */
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        //根据关键字来查询
        if(keyword != null){
            wrapper.like("tags",keyword);
            wrapper.like("content",keyword);
            wrapper.like("title",keyword);
            wrapper.like("subTitle",keyword);
        }
        Page<Article> page = new Page<>(pageIndex, pageSize);
        wrapper.orderByDesc(sort);
        IPage<Article> articleIPage = articleMapper.selectPage(page, wrapper);

        return articleIPage;
    }

    @Override
    public Article lookPost(String pid) {
        /**
         * pid来获得文章
         */
        QueryWrapper<Article> wrapper=new QueryWrapper<>();
        wrapper.eq("articleId", pid);
        Article article = articleMapper.selectOne(wrapper);
        return article;
    }

    @Override
    public int newArticle(Article article) {
        int insert = articleMapper.insert(article);
        return insert;
    }
}
