package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/26.
 */
public class BulletCommentsEntity {
    private int ret;
    private String msg;
    private CommentData data;

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

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }
}
