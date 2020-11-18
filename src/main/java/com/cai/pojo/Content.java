package com.cai.pojo;

import lombok.Data;

@Data
public class Content {
    private String message;
    private Integer plat;

    public String toHiveString() {
        return message + "\t" + plat;
    }

}
