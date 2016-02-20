package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class HotRecommendsEntity {
    private String title;
    private List<RecommendEntity> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RecommendEntity> getList() {
        return list;
    }

    public void setList(List<RecommendEntity> list) {
        this.list = list;
    }
}
