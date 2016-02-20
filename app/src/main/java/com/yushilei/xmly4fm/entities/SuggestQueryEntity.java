package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/24.
 */
public class SuggestQueryEntity {
    private long id;
    private String keyword;
    private String highlightKeyword;
    private String category;
    private String imgPath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getHighlightKeyword() {
        return highlightKeyword;
    }

    public void setHighlightKeyword(String highlightKeyword) {
        this.highlightKeyword = highlightKeyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
