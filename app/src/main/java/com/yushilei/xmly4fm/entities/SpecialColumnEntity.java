package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class SpecialColumnEntity {
    private boolean hasMore;
    private String title;
    private List<SpecialEntity> list;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SpecialEntity> getList() {
        return list;
    }

    public void setList(List<SpecialEntity> list) {
        this.list = list;
    }
}
