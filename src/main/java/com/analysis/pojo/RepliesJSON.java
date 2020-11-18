package com.analysis.pojo;

import com.cai.pojo.Content;
import com.cai.pojo.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RepliesJSON {

    private String rpid_str;
    private Integer oid;
    private Member member;
    private Content content;
    private String replies;
    private Integer like;
    private Integer rcount;

    public String toHiveString() {
        return  rpid_str + "\t" +
                oid + "\t" +
                member.toHiveString() + "\t" +
                content.toHiveString() + "\t" +
                replies + "\t" +
                like + "\t" +
                rcount;
    }

}
