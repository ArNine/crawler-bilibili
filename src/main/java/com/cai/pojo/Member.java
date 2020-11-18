package com.cai.pojo;

import lombok.Data;

@Data
public class Member {

    private String mid;             //uid
    private String uname;           //用户名
    private String Rank;
    private Level_info level_info;  //等级信息
    private Vip vip;                //vip信息

    public String toHiveString() {
        return  mid + "\t" +
                uname + "\t" +
                Rank + "\t" +
                level_info.toHiveString() + "\t" +
                vip.toHiveString();
    }

}
