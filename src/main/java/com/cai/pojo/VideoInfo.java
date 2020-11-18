package com.cai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VideoInfo {

    private String bvid;
    private String aid;
    private Integer cid;
    private Integer tid;
    private String tname;
    private String title;           //标题
    private Integer duration;
    private String desc;            //描述
    private Integer ctime;
    private Integer pubdate;
    private Stat stat;              //一些例如观看数硬币数之类的信息
    private String dynamic;

    public String toHiveString() {
        return  bvid + "\t" +
                aid + "\t" +
                cid + "\t" +
                tid + "\t" +
                tname + "\t" +
                title + "\t" +
                duration + "\t" +
                desc + "\t" +
                ctime + "\t" +
                pubdate + "\t" +
                stat.toHiveString() + "\t" +
                dynamic;
    }

}
