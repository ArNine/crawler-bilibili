package com.cai.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {

    private Integer cid;
    private Integer mid;
    private String name;
    private String intro;


    public String toHiveString() {
        return cid + "\t" + mid + "\t" + name + "\t" + intro;
    }

}
