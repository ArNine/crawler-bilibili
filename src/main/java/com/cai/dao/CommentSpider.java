package com.cai.dao;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cai.mapper.CommentResult;
import com.cai.pojo.Replies;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;


public class CommentSpider implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).setCharset("utf-8");

    public Site getSite() {
        return site;
    }


    public void process(Page page) {
        com.cai.pojo.Page commentPage = JSONObject.parseObject(page.getJson().jsonPath("$.data.page").get(), com.cai.pojo.Page.class);
        List<Replies> repliesList = JSONArray.parseArray(page.getJson().jsonPath("$.data.replies").all().toString(), Replies.class);
        page.putField("page", commentPage);
        page.putField("repliesList", repliesList);
    }

    public static CommentResult getCommentByAID(String oid, String pn, String ps) {
        CommentResult pagePipeline = new CommentResult();
        Spider.create(new CommentSpider())
                .addUrl("https://api.bilibili.com/x/v2/reply?&pn=" + pn + "&type=1&oid=" + oid + "&sort=0&ps=" + ps)
                .addPipeline(pagePipeline)
                //.addPipeline(null)
                .run();
        return pagePipeline;
    }

    public static CommentResult getCommentByAID(String oid, String rpid, String pn, String ps) {
        CommentResult pagePipeline = new CommentResult();
        //System.out.println("地址：" + "https://api.bilibili.com/x/v2/reply/reply?pn=" + pn + "&type=1&oid=" + oid + "&ps=" + ps + "&root=" + rpid);
        Spider.create(new CommentSpider())
                .addUrl("https://api.bilibili.com/x/v2/reply/reply?pn=" + pn + "&type=1&oid=" + oid + "&ps=" + ps + "&root=" + rpid)
                .addPipeline(pagePipeline)
                //.addPipeline(null)
                .run();
        return pagePipeline;
    }

}



