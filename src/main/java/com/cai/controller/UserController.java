package com.cai.controller;

import com.cai.pojo.ColumnInfo;
import com.cai.pojo.VideoInfo;
import com.cai.service.UserService;
import org.junit.Test;

import java.util.List;

public class UserController {

    public List<VideoInfo> getColumnVideoInfo(String mid, String cid) {
        UserService userService = new UserService();
        List<VideoInfo> column = userService.getColumn(mid, cid);
        return column;
    }

    public List<ColumnInfo> getUserAllColumn(String mid) {
        UserService userService = new UserService();
        return userService.getUserAllColumn(mid);
    }

    public List<Integer> getUserAllColumnCid(String mid) {
        UserService userService = new UserService();
        return userService.getUserAllColumnCid(mid);
    }

}
