package com.cai.mapper;

import com.cai.pojo.ColumnInfo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

public class UserColumnResult implements Pipeline {

    private List<ColumnInfo> columnInfos;

    public UserColumnResult() {

    }

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> mapResults = resultItems.getAll();
        this.columnInfos = (List<ColumnInfo>) mapResults.get("columnInfos");
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }
}
