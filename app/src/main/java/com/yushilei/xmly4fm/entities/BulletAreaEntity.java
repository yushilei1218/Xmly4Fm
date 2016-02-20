package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class BulletAreaEntity {
    private String title;
    private boolean hasMore;
    private List<TrackEntity> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<TrackEntity> getList() {
        return list;
    }

    public void setList(List<TrackEntity> list) {
        this.list = list;
    }
}
