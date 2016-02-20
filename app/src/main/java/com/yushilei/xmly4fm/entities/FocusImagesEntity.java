package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class FocusImagesEntity {
    private String title;
    private List<FocusImageEntity> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FocusImageEntity> getList() {
        return list;
    }

    public void setList(List<FocusImageEntity> list) {
        this.list = list;
    }
}
