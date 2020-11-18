package com.cai.mapper;

import com.cai.pojo.VideoInfo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class VideoResult implements Pipeline {

    private VideoInfo videoInfo;

    public VideoResult() {
    }

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> mapResults = resultItems.getAll();
        videoInfo = (VideoInfo) mapResults.get("videoInfo");
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }
}
