package org.softbattle.klog_server.article.controller;


import com.alibaba.fastjson.JSON;
import org.softbattle.klog_server.article.entity.Article;
import org.softbattle.klog_server.article.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sun
 * @since 2021-09-28
 */
@RestController
@RequestMapping("/post")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newArticle(String title, String subTitle, String[] banners, String[] tags, String content) {
        Map<String, Object> map = new HashMap<>();
        Article article = new Article();
        article.setArthor("?");//解析token，根据token取
        article.setBanner(banners.toString());
        article.setTags(tags.toString());
        article.setContent(content);
        article.setTitle(title);
        article.setSubTitle(subTitle);
        article.setViews("0");
        LocalDate posttime = LocalDate.now();//取现在
        article.setCtim(posttime);

        try {
            articleService.newArticle(article);
            map.put("stat","ok");
        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg","内部错误");
        }
        return JSON.toJSONString(map);
    }
}
