package com.yushilei.xmly4fm.entities.rank;

import com.yushilei.xmly4fm.entities.radio.RadioEntity;

import java.util.List;

/**
 * Created by yushilei on 2016/2/5.
 */
public class RankCategoryEntity {
    private List<RankEntity> list;
    private int ret;
    private int count;
    private String title;

    public List<RankEntity> getList() {
        return list;
    }

    public void setList(List<RankEntity> list) {
        this.list = list;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
