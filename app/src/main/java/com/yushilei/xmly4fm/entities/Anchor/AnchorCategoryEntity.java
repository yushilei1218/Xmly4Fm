package com.yushilei.xmly4fm.entities.Anchor;

import com.yushilei.xmly4fm.entities.UserInfo;

import java.util.List;

/**
 * Created by yushilei on 2016/2/4.
 */
public class AnchorCategoryEntity {
    private int id;
    private String name;
    private String title;
    private List<UserInfo> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserInfo> getList() {
        return list;
    }

    public void setList(List<UserInfo> list) {
        this.list = list;
    }
}
