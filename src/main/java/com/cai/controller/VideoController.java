package com.cai.controller;


import com.cai.pojo.Replies;
import com.cai.service.VideoService;
import java.util.List;

public class VideoController {


    public List<Replies> getCommentByBVid(String BVid) {
        VideoService apiTest = new VideoService();
        String oid = apiTest.getAidByBVid(BVid);
        List<Replies> replies = apiTest.getComment(oid);
        return replies;
    }

    public List<Replies> getCommentByAid(String aid) {
        VideoService apiTest = new VideoService();
        List<Replies> replies = apiTest.getComment(aid);
        return replies;
    }

}
