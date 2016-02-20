package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/2/1.
 */
public class CategoryListEntity {
    private int ret;
    private String msg;
    private List<CategoryEntity> list;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CategoryEntity> getList() {
        return list;
    }

    public void setList(List<CategoryEntity> list) {
        this.list = list;
    }
}
