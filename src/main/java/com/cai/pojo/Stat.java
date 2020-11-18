package com.cai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stat {

    private Integer aid;
    private Integer view;           //观看数
    private Integer danmaku;
    private Integer reply;          //回复数
    private Integer favorite;       //收藏数
    private Integer coin;           //硬币
    private Integer share;          //分享
    private Integer his_rank;
    private Integer like;           //喜欢

    public String toHiveString() {
        return  aid + "\t" +
                view + "\t" +
                danmaku + "\t" +
                reply + "\t" +
                favorite + "\t" +
                coin + "\t" +
                share + "\t" +
                his_rank + "\t" +
                like;
    }

}
