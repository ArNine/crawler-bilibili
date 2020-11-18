package com.cai.dao;

import com.alibaba.fastjson.JSONArray;
import com.cai.mapper.ColumnResult;
import com.cai.pojo.VideoInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class ColumnSpider implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).setCharset("utf-8");

    public void process(Page page) {
        com.cai.pojo.Page columnPage = JSONArray.parseObject(page.getJson().jsonPath("$.data.page").get(), com.cai.pojo.Page.class);
        List<VideoInfo> videoInfos = JSONArray.parseArray(page.getJson().jsonPath("$.data.list.archives").all().toString(), VideoInfo.class);
        page.putField("videoInfos", videoInfos);
        page.putField("page", columnPage);
    }

    public Site getSite() {
        return this.site;
    }

    /**
     * process the mid and cid, extract all video information in this pn
     * @param mid bilibili user id
     * @param cid bilibili column(专栏) id
     * @param pn bilibili column pageCur
     * @param ps bilibili column pageSize
     */
    public static ColumnResult getColumn(String mid, String cid, String pn, String ps) {
        ColumnResult columnResult = new ColumnResult();
        Spider.create(new ColumnSpider())
                .addUrl("https://api.bilibili.com/x/space/channel/video?mid=" + mid + "&cid=" + cid + "&pn=" + pn + "&ps=" + ps + "&order=0")
                .addPipeline(columnResult)
                .run();
        return columnResult;
    }

}
