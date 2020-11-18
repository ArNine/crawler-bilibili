package com.cai.service;

import com.cai.dao.ColumnSpider;
import com.cai.dao.UserColumnSpider;
import com.cai.mapper.ColumnResult;
import com.cai.mapper.VideoResult;
import com.cai.pojo.ColumnInfo;
import com.cai.pojo.Page;
import com.cai.pojo.VideoInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<VideoInfo> getColumn(String mid, String cid) {
        List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
        //https://api.bilibili.com/x/space/channel/video?mid=54992199&cid=82529&pn=2&ps=100&order=0
        ColumnResult result = ColumnSpider.getColumn(mid, cid, "1", "100");
        Page page = result.getPage();
        videoInfoList.addAll(result.getVideoInfos());
        for (int i = 2;i <= (page.getCount() - 1) / page.getSize() + 1;i ++) {
            try {
                result = ColumnSpider.getColumn(mid, cid, i + "", "100");
                videoInfoList.addAll(result.getVideoInfos());
                //Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return videoInfoList;
    }

    public List<ColumnInfo> getUserAllColumn(String mid) {
        return UserColumnSpider.getAllColumnsByUser(mid).getColumnInfos();
    }

    public List<Integer> getUserAllColumnCid(String mid) {
        List<Integer> cidList = new ArrayList<Integer>();
        for (ColumnInfo columnInfo : this.getUserAllColumn(mid)) {
            cidList.add(columnInfo.getCid());
        }
        return cidList;
    }


}
