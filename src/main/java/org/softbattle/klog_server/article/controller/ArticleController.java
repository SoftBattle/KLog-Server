package org.softbattle.klog_server.article.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

/**
 * pid: 文章id
 * uid: 作者id
 * title: 文章标题
 * subTitle: 副标题
 * tags: 标签，数组
 * banners: banner图，数组
 * views: 浏览数，数字
 * ctime: 创建时间，date
 * mtime: 最近编辑时间，date
 */
@RestController
@RequestMapping("/post")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    /**
     * 3.1创建文章
     * @param title  文章标题
     * @param subTitle 副标题
     * @param banners banner图
     * @param tags    标签
     * @param content  文章正文内容
     * @return
     */
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

    /**
     * 请求文章列表
     * @param keyword
     * @param pageSize
     * @param pageIndex
     * @param sort
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String queryLists(String keyword, int pageSize, int pageIndex, String sort){
        Map<String, Object> map = new HashMap<>();
        IPage<Article> articleIPage=null;
        try {
            articleIPage = articleService.queryLists(keyword, pageSize, pageIndex, sort);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("post",articleIPage);
            map1.put("total",articleIPage.getSize());
            map.put("stat","ok");
            map.put("data",map1);
        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg","内部错误");
        }
        return JSON.toJSONString(map);
    }

    /**  这个没写好，不知道什么意思
     *   后台需要在在url中取得参数pid，以此返回对应的文章
     * @param pid
     * @return
     */
    @RequestMapping(value = "/pid",method = RequestMethod.GET)
    public String lookPost(String pid){
        Map<String, Object> map = new HashMap<>();
        Article article = articleService.lookPost(pid);
        Integer articleId = article.getArticleId();


        return JSON.toJSONString(map);
    }

}
