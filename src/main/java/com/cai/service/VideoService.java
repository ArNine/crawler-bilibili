package com.cai.service;

import com.cai.dao.CommentSpider;
import com.cai.dao.VideoSpider;
import com.cai.mapper.CommentResult;
import com.cai.mapper.VideoResult;
import com.cai.pojo.Page;
import com.cai.pojo.Replies;

import java.util.ArrayList;
import java.util.List;

public class VideoService {

    public List<Replies> getCommentByBVid(String BVid) {
        VideoService apiTest = new VideoService();
        String oid = apiTest.getAidByBVid(BVid);
        List<Replies> replies = apiTest.getComment(oid);
        return replies;
    }

    public String getAidByBVid(String BVid) {
        VideoResult videoResult = VideoSpider.getAidByBVid(BVid);
        return videoResult.getVideoInfo().getAid();
    }

    public List<Replies> getComment(String oid) {
        int cur = 0, all = 0;   //cur 标记当前获取了多少条评论，all，总共有多少评论

        CommentResult result = CommentSpider.getCommentByAID(oid, "1", "40");
        Page page = result.getPage();
        all = page.getAcount();
        List<Replies> baseReplies = new ArrayList<Replies>();
        baseReplies.addAll(result.getRepliesList());
        cur += result.getRepliesList().size();
        System.out.println(Float.parseFloat(String.format("%.4f", (cur * 1.0) / all)) * 100 + "%\tdownload;");
        for (int i = 2;i <= (page.getCount() - 1) / page.getSize() + 1;i ++) {
            try {
                result = CommentSpider.getCommentByAID(oid, i + "", "40");
                baseReplies.addAll(result.getRepliesList());
                cur += result.getRepliesList().size();
                System.out.print("第");
                System.out.println(Float.parseFloat(String.format("%.4f", (cur * 1.0) / all)) * 100 + "%\tdownload;");
                //Thread.sleep(400);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(baseReplies.size());

        //获取楼中楼评论
        for (Replies baseReply : baseReplies) {
            if (baseReply.getRcount() != 0) {
                result = CommentSpider.getCommentByAID(baseReply.getOid() + "", baseReply.getRpid_str(), "1", "20");
                page = result.getPage();
                baseReply.getReReplies().addAll(result.getRepliesList());
                cur += result.getRepliesList().size();
                System.out.println(Float.parseFloat(String.format("%.4f", (cur * 1.0) / all)) * 100 + "%\tdownload;");
                for (int i = 2;i <= (page.getCount() - 1) / page.getSize() + 1;i ++) {
                    try {
                        result = CommentSpider.getCommentByAID(baseReply.getOid() + "", baseReply.getRpid_str(), i + "", "20");
                        //System.out.println("楼中楼评论：" + result.getRepliesList());
                        baseReply.getReReplies().addAll(result.getRepliesList());
                        cur += result.getRepliesList().size();
                        System.out.println(Float.parseFloat(String.format("%.4f", (cur * 1.0) / all)) * 100 + "%\tdownload;");
                        //Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return baseReplies;
    }
}
