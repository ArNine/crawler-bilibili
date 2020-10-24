package bilibili.comment;

import lombok.Data;

@Data
public class Page {

    private Integer num;    //第几页
    private Integer size;   //一页大小
    private Integer count;  //一共多少评论，不包括楼中楼
    private Integer acount; //一共多少评论，包括楼中楼

}
