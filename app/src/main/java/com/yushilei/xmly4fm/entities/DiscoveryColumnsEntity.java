package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/22.
 */
public class DiscoveryColumnsEntity {
    private String title;
    private int locationInHotRecommend;
    private List<DiscoveryEntity> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLocationInHotRecommend() {
        return locationInHotRecommend;
    }

    public void setLocationInHotRecommend(int locationInHotRecommend) {
        this.locationInHotRecommend = locationInHotRecommend;
    }

    public List<DiscoveryEntity> getList() {
        return list;
    }

    public void setList(List<DiscoveryEntity> list) {
        this.list = list;
    }
}
