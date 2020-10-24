package bilibili.comment;

import lombok.Data;

@Data
public class Member {

    private String mid;             //uid
    private String uname;           //用户名
    private String Rank;
    private Level_info level_info;  //等级信息

}
