package com.cai.mapper;

import com.cai.pojo.Page;
import com.cai.pojo.Replies;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CommentResult implements Pipeline {

    private Page page;
    private List<Replies> repliesList;


    public CommentResult() {
    }

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> mapResults = resultItems.getAll();
        page = (Page) mapResults.get("page");
        repliesList = (List<Replies>) mapResults.get("repliesList");
    }

    public Page getPage() {
        return page;
    }
    public List<Replies> getRepliesList() {
        return repliesList;
    }
}
