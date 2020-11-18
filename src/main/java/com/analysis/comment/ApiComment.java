package com.analysis.comment;

import com.alibaba.fastjson.JSON;
import com.analysis.dao.file.FileDao;
import com.analysis.pojo.RepliesJSON;
import com.cai.controller.UserController;
import com.cai.controller.VideoController;
import com.cai.pojo.ColumnInfo;
import com.cai.pojo.Replies;
import com.cai.pojo.VideoInfo;

import java.util.ArrayList;
import java.util.List;

public class ApiComment {

    //按照频道得到某个up所有的视频信息及评论
    public static void getUpVideoAndCommentByColumn(String filePath, String mid) {
        UserController userController = new UserController();
        VideoController videoController = new VideoController();
        FileDao fileDao = new FileDao();
        List<ColumnInfo> userAllColumn = userController.getUserAllColumn(mid);
        for (ColumnInfo columnInfo : userAllColumn) {
            try {
                String jsonString = JSON.toJSONString(columnInfo);
                fileDao.outPutStrToFile(filePath + "column_json.txt", jsonString + "\n");
                fileDao.outPutStrToFile(filePath + "column_hive.txt", columnInfo.toHiveString() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<String> aidList = new ArrayList<String>();
        for (ColumnInfo columnInfo : userAllColumn) {
            List<VideoInfo> columnVideoInfo = userController.getColumnVideoInfo(mid, columnInfo.getCid() + "");
            for (VideoInfo videoInfo : columnVideoInfo) {
                try {
                    String jsonString = JSON.toJSONString(videoInfo);
                    aidList.add(videoInfo.getAid());
                    fileDao.outPutStrToFile(filePath + "videoInfo_json.txt", jsonString + "\n");
                    fileDao.outPutStrToFile(filePath + "videoInfo_hive.txt", videoInfo.toHiveString() + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        int index = 1;
        for (String aid : aidList) {
            try {
                for (Replies replies : videoController.getCommentByAid(aid)) {
                    RepliesJSON repliesJSON = new RepliesJSON();
                    repliesJSON.setContent(replies.getContent())
                            .setLike(replies.getLike())
                            .setMember(replies.getMember())
                            .setOid(replies.getOid())
                            .setRpid_str(replies.getRpid_str())
                            .setReplies(replies.getReplies())
                            .setRcount(replies.getRcount());
                    fileDao.outPutStrToFile(filePath + "comment_json.txt", JSON.toJSONString(repliesJSON) + "\n");
                    fileDao.outPutStrToFile(filePath + "comment_hive.txt", repliesJSON.toHiveString() + "\n");
                    for (Replies reReply : replies.getReReplies()) {
                        repliesJSON.setContent(reReply.getContent())
                                .setLike(reReply.getLike())
                                .setMember(reReply.getMember())
                                .setOid(reReply.getOid())
                                .setRpid_str(reReply.getRpid_str())
                                .setReplies(reReply.getReplies())
                                .setRcount(reReply.getRcount());
                        fileDao.outPutStrToFile(filePath + "comment_json.txt", JSON.toJSONString(repliesJSON) + "\n");
                        fileDao.outPutStrToFile(filePath + "comment_hive.txt", repliesJSON.toHiveString() + "\n");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("已完成：" + index + "个视频， 还有：" + (aidList.size() - index));
        }

    }

    //得到某个up主的所有频道以及所有频道下的视频信息
    public static void getAllColumnAndVideo(String mid) {
        UserController userController = new UserController();
        for (ColumnInfo columnInfo : userController.getUserAllColumn(mid)) {

            System.out.println(columnInfo.toString());
            ApiComment.getColumnVideoInfo(mid, columnInfo.getCid() + "");
            System.out.println();
        }
    }

    //获取某个视频下的评论并打印出来
    public static void getComment(String BVid) {
        List<Replies> replies = new VideoController().getCommentByBVid(BVid);
        for (Replies reply : replies) {
            System.out.println(reply.getMember().getUname() + ": " + reply.getContent().getMessage());
            for (Replies reReply : reply.getReReplies()) {
                System.out.println("\t" + reReply.getMember().getUname() + ":" + reReply.getContent().getMessage());
            }
        }
    }
    //获取某个up主的某个频道下的所有视频基本信息（包括这些频道的观看数，硬币数等）并打印出来
    public static void getColumnVideoInfo(String mid, String cid) {
        int view = 0;
        int coin = 0;
        int share = 0;
        int favorite = 0;
        int like = 0;
        List<VideoInfo> columnVideoInfo = new UserController().getColumnVideoInfo(mid, cid);
        for (VideoInfo videoInfo : columnVideoInfo) {
            view += videoInfo.getStat().getView();
            coin += videoInfo.getStat().getCoin();
            share += videoInfo.getStat().getShare();
            favorite += videoInfo.getStat().getFavorite();
            like += videoInfo.getStat().getLike();
            //System.out.println(videoInfo.toString());
        }
        System.out.println(String.format("view=%d\ncoin=%d\nshare=%d\nfavorite=%d\nlike=%d", view, coin, share, favorite, like));
    }



}
