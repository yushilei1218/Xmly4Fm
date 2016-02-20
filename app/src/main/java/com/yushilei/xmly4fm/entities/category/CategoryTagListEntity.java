package com.yushilei.xmly4fm.entities.category;

import java.util.List;

/**
 * Created by yushilei on 2016/2/2.
 */
public class CategoryTagListEntity {
    private int ret;
    private int maxPageId;
    private int count;
    private String msg;
    private boolean hasRecommendedZones;
    private boolean isFinished;
    private List<CategoryTagEntity> list;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isHasRecommendedZones() {
        return hasRecommendedZones;
    }

    public void setHasRecommendedZones(boolean hasRecommendedZones) {
        this.hasRecommendedZones = hasRecommendedZones;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public List<CategoryTagEntity> getList() {
        return list;
    }

    public void setList(List<CategoryTagEntity> list) {
        this.list = list;
    }
}
