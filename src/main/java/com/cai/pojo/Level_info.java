package com.cai.pojo;

import lombok.Data;

@Data
public class Level_info {
    private Integer current_level;
    private Integer current_min;
    private Integer current_exp;
    private Integer next_exp;

    public String toHiveString() {
        return  current_level + "\t" +
                current_min + "\t" +
                current_exp + "\t" +
                next_exp;
    }

}
