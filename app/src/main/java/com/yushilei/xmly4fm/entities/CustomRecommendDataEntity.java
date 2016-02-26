package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/2/24.
 */
public class CustomRecommendDataEntity {
    private boolean hasMore;
    private List<AlbumEntity> list;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<AlbumEntity> getList() {
        return list;
    }

    public void setList(List<AlbumEntity> list) {
        this.list = list;
    }
}
