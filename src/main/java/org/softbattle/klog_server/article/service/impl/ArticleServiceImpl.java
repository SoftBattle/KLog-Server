package org.softbattle.klog_server.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.softbattle.klog_server.article.entity.Article;
import org.softbattle.klog_server.article.mapper.ArticleMapper;
import org.softbattle.klog_server.article.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.softbattle.klog_server.user.dto.input.LoginInfo;
import org.softbattle.klog_server.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private LoginInfo loginInfo;

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

    @Override
    public int updateArticle(String title, String subTitle, @RequestParam(required = false) MultipartFile banners, String[] tags, String content) {
        UpdateWrapper<Article> wrapper=new UpdateWrapper<Article>();
        //这里要获得用户作者
        wrapper.eq("arthor",loginInfo.getUid());//解析token得到作者
        //然后在URL里面获得pid
        wrapper.eq("articleId","pid");//解析URL获得文章iD
        Article article=new Article();
        article.setTitle(title);
        article.setSubTitle(subTitle);
        article.setBanner(banners.getOriginalFilename());
        article.setTags(tags.toString());
        article.setContent(content);
        return articleMapper.update(article,wrapper);
    }

    @Override
    public int deleteArticle(String pid) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("articleId",pid);
        int delete = articleMapper.delete(wrapper);
        return delete;
    }


}
