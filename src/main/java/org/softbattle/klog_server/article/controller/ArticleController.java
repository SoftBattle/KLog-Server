package org.softbattle.klog_server.article.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.softbattle.klog_server.article.entity.Article;
import org.softbattle.klog_server.article.service.IArticleService;
import org.softbattle.klog_server.user.dto.input.LoginInfo;
import org.softbattle.klog_server.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private LoginInfo loginInfo;

    /**
     * 3.2创建文章
     *
     * @param title    文章标题
     * @param subTitle 副标题
     * @param banners  banner图    为啥你给的是string数组，真的疑惑，不是文件流吗，难道传个图片名字就好了？
     * @param tags     标签
     * @param content  文章正文内容
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newArticle(String title, String subTitle, @RequestParam(required = false) MultipartFile banners, String[] tags, String content) {
        Map<String, Object> map = new HashMap<>();
        try {
            Article article = new Article();

            //解析token，根据token取到userid
            article.setArthor(loginInfo.getUid());

            //上传图片
            if (banners != null) {
                FileUtil.upload(banners);
                article.setBanner(banners.getOriginalFilename());
            }
            article.setTags(tags.toString());
            article.setContent(content);
            article.setTitle(title);
            article.setSubTitle(subTitle);
            article.setViews("0");//设置初始的观看值为0
            LocalDate posttime = LocalDate.now();//取现在
            article.setCtim(posttime);


            articleService.newArticle(article);
            map.put("stat", "ok");
        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg", "内部错误");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 3.0请求文章列表
     *
     * @param keyword
     * @param pageSize
     * @param pageIndex
     * @param sort
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String queryLists(String keyword, int pageSize, int pageIndex, String sort) {
        Map<String, Object> map = new HashMap<>();
        IPage<Article> articleIPage = null;
        try {
            articleIPage = articleService.queryLists(keyword, pageSize, pageIndex, sort);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("post", articleIPage);
            map1.put("total", articleIPage.getSize());
            map.put("stat", "ok");
            map.put("data", map1);
        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg", "内部错误");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 3.1浏览文章
     * 这个没写好，不知道什么意思，，，最后浏览数目要+1
     * 后台需要在在url中取得参数pid，以此返回对应的文章
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String lookPost(@PathVariable String pid) {
        Map<String, Object> map = new HashMap<>();
        try {
            Map<String, Object> data = new HashMap<>();
            Article article = articleService.lookPost(pid);
            Integer articleId = article.getArticleId();//文章id
            String uid = article.getArthor();//作者id
            /**接下来根据这个作者id来获得
             nickname: 'cmkangkang',
             avatar: '/demo.png',
             follow: false // 是否关注了该作者
             然后装在一个叫做author的map里面
             **/
            Map<String, Object> author = new HashMap<>();
            author.put("uid", uid);
            //接下来就把别的放进来


            String title = article.getTitle();//
            String subTitle = article.getSubTitle();
            String content = article.getContent();
            String banner = article.getBanner();//图片的数组？ 肯定不能这样返回的
            String tags = article.getTags();
            String views = article.getViews();//不明白为啥是string类型的？好奇怪
            LocalDate ctim = article.getCtim();
            //mtime没有这个属性，自己乱加的

            //star是否被收藏，需要从作者表里面读，判断这个articleID是不是在那个数组里面

            //然后再把这个所有装在data的一个map里面
            data.put("pid", articleId);
            data.put("author", author);
            data.put("title", title);
            data.put("subTitle", subTitle);
            data.put("content", content);
            data.put("banner", banner);
            data.put("tags", tags);
            data.put("views", views);
            data.put("ctim", ctim);
            data.put("star", "  ");//是否被收藏

            map.put("stat", "ok");
            map.put("data", data);
        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg", "内部错误");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 3.3编辑文章
     *
     * @param title
     * @param subTitle
     * @param banners     图片不是一个string的数组，只是图片的名字是，所以你要给我图片文件MultipartFile类型的，这个fileutil方法里面有自动解析图片名字
     * @param tags
     * @param content
     * @return
     */
    @RequestMapping(value = "/{pid}", method = RequestMethod.PUT)
    public String updateArticle(@PathVariable String pid,String title, String subTitle, @RequestParam(required = false) MultipartFile banners, String[] tags, String content) {
        Map<String, Object> map = new HashMap<>();
        try {

            //解析token得到作者，判断是否有权限；在url中获取pid，找到需要更新的的文章。
            String uid = loginInfo.getUid();
            Article article = articleService.getById(pid);
            String arthor = article.getArthor();
            if(arthor.equals(uid)){//相等说明有权限
                articleService.updateArticle(title, subTitle, banners, tags, content);
            }

        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg", "内部错误");
        }

        return JSON.toJSONString(map);
    }

    /**
     * 3.4删除文章
     *
     * @param pid 文章号
     *            主要要获得userid，来判断文章是否能够删除
     * @return
     */
    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public String deleteArticle(@PathVariable String pid) {
        Map<String, Object> map = new HashMap<>();
        try {
            //解析token得到作者，在url中获取pid，判断是否具有权限删除，能则删除。
            String uid = loginInfo.getUid();
            Article article = articleService.getById(pid);
            String arthor = article.getArthor();
            if(arthor.equals(uid)){
                articleService.deleteArticle(pid);
            }

        } catch (Exception e) {
            map.put("stat", "Internal_Server_Error");
            map.put("msg", "内部错误");
        }

        return JSON.toJSONString(map);
    }
}
