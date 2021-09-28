package org.softbattle.klog_server.article.service.impl;

import org.softbattle.klog_server.article.entity.Article;
import org.softbattle.klog_server.article.mapper.ArticleMapper;
import org.softbattle.klog_server.article.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
