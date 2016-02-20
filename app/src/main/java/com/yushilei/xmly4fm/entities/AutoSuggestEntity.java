package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/24.
 */
public class AutoSuggestEntity {
    private int albumResultNum;
    private int queryResultNum;
    private List<SuggestAlbumEntity> albumResultList;
    private List<SuggestQueryEntity> queryResultList;

    public int getAlbumResultNum() {
        return albumResultNum;
    }

    public void setAlbumResultNum(int albumResultNum) {
        this.albumResultNum = albumResultNum;
    }

    public int getQueryResultNum() {
        return queryResultNum;
    }

    public void setQueryResultNum(int queryResultNum) {
        this.queryResultNum = queryResultNum;
    }

    public List<SuggestAlbumEntity> getAlbumResultList() {
        return albumResultList;
    }

    public void setAlbumResultList(List<SuggestAlbumEntity> albumResultList) {
        this.albumResultList = albumResultList;
    }

    public List<SuggestQueryEntity> getQueryResultList() {
        return queryResultList;
    }

    public void setQueryResultList(List<SuggestQueryEntity> queryResultList) {
        this.queryResultList = queryResultList;
    }
}
