package org.softbattle.klog_server.module.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.softbattle.klog_server.entity.article.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}