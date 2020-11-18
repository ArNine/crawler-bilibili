package com.cai.dao;

import com.cai.mapper.VideoResult;
import com.cai.pojo.VideoInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoSpider implements PageProcessor {



    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).setCharset("utf-8");

    public void process(Page page) {
        VideoInfo videoInfo = new VideoInfo();
        Document document = page.getHtml().getDocument();
        Elements script = document.getElementsByTag("script");
        String aid = findAidInStr(script);
        videoInfo.setAid(aid);
        page.putField("videoInfo", videoInfo);
    }

    public Site getSite() {
        return this.site;
    }

    private String findAidInStr(Elements script) {
        //通过正则提取出aid
        String pattern = "\"aid\":\\d+";
        Pattern r = Pattern.compile(pattern);
        for (Element element : script) {
            try {
                String content = element.toString();
                Matcher m = r.matcher(content);
                m.find();
                if (m.group(0) != null) {
                    return m.group(0).substring(6);
                }
            } catch (Exception e) {

            }
        }
        return null;
    }

    public static VideoResult getAidByBVid(String BVid) {
        VideoResult videoResult = new VideoResult();
        Spider.create(new VideoSpider())
                .addUrl("https://bilibili.com/" + BVid)
                .addPipeline(videoResult)
                .run();
        return videoResult;
    }

}
