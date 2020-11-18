package com.cai.mapper;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.Map;

public class MysqlPipeline implements Pipeline {

    public MysqlPipeline() {
    }

    public void process(ResultItems resultitems, Task task) {
        Map<String, Object> mapResults = resultitems.getAll();
        Iterator<Map.Entry<String, Object>> iter = mapResults.entrySet().iterator();
        Map.Entry<String, Object> entry;
        System.out.println("============================");
        // 输出到控制台
//        while (iter.hasNext()) {
//            entry = iter.next();
//            System.out.println(entry.getKey() + "||||" + entry.getValue());
//        }

    }
}
