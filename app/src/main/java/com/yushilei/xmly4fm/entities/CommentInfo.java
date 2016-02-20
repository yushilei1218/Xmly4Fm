package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/26.
 */
public class CommentInfo {
    private List<Comment> list;
    private int pageId;
    private int pageSize;
    private int maxPageId;
    private int totalCount;

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageId() {
        return pageId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
