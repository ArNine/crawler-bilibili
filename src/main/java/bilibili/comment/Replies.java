package bilibili.comment;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Replies {

    private String rpid_str;
    private Integer opid;
    private Member member;
    private Content content;
    private String replies;
    private List<Replies> reReplies = new ArrayList<Replies>();
}
