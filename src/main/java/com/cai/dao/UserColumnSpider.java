package com.cai.dao;

import com.alibaba.fastjson.JSONArray;
import com.cai.mapper.ColumnResult;
import com.cai.mapper.UserColumnResult;
import com.cai.pojo.ColumnInfo;
import com.cai.pojo.VideoInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class UserColumnSpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).setCharset("utf-8");

    public void process(Page page) {

        List<ColumnInfo> columnInfos = JSONArray.parseArray(page.getJson().jsonPath("$.data").all().toString(), ColumnInfo.class);
        page.putField("columnInfos", columnInfos);
    }

    public Site getSite() {
        return this.site;
    }

    public static UserColumnResult getAllColumnsByUser(String mid) {
        UserColumnResult userColumnResult = new UserColumnResult();
        Spider.create(new UserColumnSpider())
                .addUrl("https://api.bilibili.com/x/space/channel/index?mid=" + mid)
                .addPipeline(userColumnResult)
                .run();
        return userColumnResult;
    }

}
