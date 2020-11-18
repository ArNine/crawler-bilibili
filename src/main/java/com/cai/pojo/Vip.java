package com.cai.pojo;

import lombok.Data;

@Data
public class Vip {

    private Integer vipType;
    private Integer vipStatus;

    public String toHiveString() {
        return vipType + "\t" + vipStatus;
    }

}
