package bilibili;

import bilibili.comment.Page;
import bilibili.comment.Replies;
import bilibili.comment.URLUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Comment {

    private int all = 0;
    private int cur = 0;

    public static void main(String[] args) throws Exception {


        String BV = "BV1BK411L7ou";
        new Comment().run(URLUtils.createBVURL(BV));
    }

    public void run(String url) throws Exception {

        //解析网页
        //1.获取aid;
        String aid = getAid(url);
        System.out.println("aid = " + aid);

        //2.获取json格式的评论数据, 字符串形式
        String json = getCommentJson(URLUtils.createRepliesURL(aid, "1", "0"));

        //3.获取基本的page信息
        Page page = parsingJsonPage(json);
        all = page.getAcount();
        System.out.println(page.toString());

        //4.获取所有基楼的数据信息存入List，因为会自加1，所以从0开始
        List<Replies> baseReplies = parsingJsonReplies(page, URLUtils.createRepliesURL(aid, "0", "0"));

        /*5.获取楼中楼
            在allReplies获得了所有评论及楼中楼，并且包含用户等级名称等基本信息
            后面可以用这些信息进行具体业务需求，当然，如果要更多的信息可以在相应
            的类中添加和b站返回的json格式数据相同的属性，小心ip被封哦！
         */
        List<Replies> allReplies = parsingJsonReReplies(baseReplies, aid);
//        for (Replies allReply : allReplies) {
//            System.out.println(allReply.getContent().getMessage());
//            if (allReply.getReplies() != "null") {
//                for (Replies reReply : allReply.getReReplies()) {
//                    System.out.println(reReply.getContent().getMessage());
//                }
//            }
//            System.out.println("==========================================");
//        }

        //6.具体业务
        operation(allReplies);

    }
    public void operation(List<Replies> allReplies) {
        int cnt = 0;
        int[] num  = new int[7];
        for (int i = 0;i < num.length;i ++) {
            num[i] = 0;
        }
        for (Replies allReply : allReplies) {
            cnt ++;
            num[allReply.getMember().getLevel_info().getCurrent_level()] ++;
            if (allReply.getReplies() != "null") {
                for (Replies reReply : allReply.getReReplies()) {
                   cnt ++;
                   num[allReply.getMember().getLevel_info().getCurrent_level()] ++;
                }
            }
        }

        System.out.println("总数评论： " + cnt);
        for (int i = 0;i < num.length;i ++) {
            System.out.println("Level " + i + " 共 " + num[i] + " 条评论   " + Float.parseFloat(String.format("%.4f", (num[i] * 1.0) / cnt)) * 100 + "%");
        }

    }


    //获取楼中楼的基本数据，具体数据调用parsingJsonReplies爬取
    public List<Replies> parsingJsonReReplies(List<Replies> baseReplies, String aid) throws Exception {
        String json;
        for (Replies baseReply : baseReplies) {
            //System.out.println(baseReply.getReplies() + "  |  " + baseReply.getContent().getMessage());
            if (baseReply.getReplies() != null) {
                json = getCommentJson(URLUtils.createReRepliesURL(aid, "1", baseReply.getRpid_str()));
                Page page = parsingJsonPage(json);
                List<Replies> repliesList = parsingJsonReplies(page, URLUtils.createReRepliesURL(aid, "0", baseReply.getRpid_str()));
                baseReply.getReReplies().addAll(repliesList);
            }

        }

        return baseReplies;
    }
    //获取评论，也可以用来获取楼中楼数据
    public List<Replies> parsingJsonReplies(Page page, String url) throws Exception {
        JSONObject jsonObject = null;
        String repliesStr = "";
        String json = "";
        List<Replies> baseReplies = new ArrayList<Replies>();
        for (int i = 1;i <= (page.getCount() - 1) / page.getSize() + 1;i ++) {

            //页数自加1
            url = URLUtils.addURLByPn(url);
            json = getCommentJson(url);
            jsonObject = JSON.parseObject(json);
            repliesStr = ((JSONObject)jsonObject.get("data")).get("replies").toString();

            List<Replies> repliesList = JSONArray.parseArray(repliesStr, Replies.class);
            baseReplies.addAll(repliesList);
            cur += repliesList.size();
            System.out.println(Float.parseFloat(String.format("%.4f", (cur * 1.0) / all)) * 100 + "%  download;");
            //Thread.sleep(1000);
        }
        return baseReplies;
    }
    //获取页数信息
    public Page parsingJsonPage(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        //JSONObject.parseObject(json, Page.class);
        String pageStr = ((JSONObject)jsonObject.get("data")).get("page").toString();
        Page page = JSONObject.parseObject(pageStr, Page.class);
        return page;
    }

    public String getCommentJson(String url) throws Exception {
        //String commentUrl = "https://api.bilibili.com/x/v2/reply?pn=1&type=1&oid=" + aid + "&sort=0&_=1603500190026";
        Document document = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").timeout(5000).get();
        String ss = document.getElementsByTag("body").text();
        //System.out.println(ss);
        return ss;
    }

    //每个视频都对应一个aid，aid指向获取评论json的api
    public String getAid(String url) throws Exception {
        Document document = Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").timeout(5000).get();
        Elements script = document.getElementsByTag("script");
        String aid = findAidInStr(script);
        return aid;
    }


    public String findAidInStr(Elements script) {
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

}
/*

USER_AGENTS = [
    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
    "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
    "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
    "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
    "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
    "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5"
    ]


 */