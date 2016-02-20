package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/23.
 */
public class TrackListEntity {
    private int pageId;
    private int pageSize;
    private int maxPageId;
    private int totalCount;
    private List<TrackEntity> list;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<TrackEntity> getList() {
        return list;
    }

    public void setList(List<TrackEntity> list) {
        this.list = list;
    }
}
