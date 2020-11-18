package com.cai.mapper;

import com.cai.pojo.Page;
import com.cai.pojo.VideoInfo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;


public class ColumnResult implements Pipeline {

    private List<VideoInfo> videoInfos;
    private Page page;

    public ColumnResult() {

    }



    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> mapResults = resultItems.getAll();
        videoInfos = (List<VideoInfo>) mapResults.get("videoInfos");
        page = (Page) mapResults.get("page");
    }

    public List<VideoInfo> getVideoInfos() {
        return videoInfos;
    }

    public Page getPage() {
        return page;
    }
}
