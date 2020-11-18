package com.cai.pojo;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Replies {

    private String rpid_str;
    private Integer oid;
    private Member member;
    private Content content;
    private String replies;
    private Integer like;
    private Integer rcount;
    private List<Replies> reReplies = new ArrayList<Replies>();

}
