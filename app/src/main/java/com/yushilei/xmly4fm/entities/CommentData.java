package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/26.
 */
public class CommentData {
    private int second;
    private List<Comment> comments;

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
