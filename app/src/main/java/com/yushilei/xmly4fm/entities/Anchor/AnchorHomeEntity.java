package com.yushilei.xmly4fm.entities.Anchor;

import java.util.List;

/**
 * Created by yushilei on 2016/2/4.
 */
public class AnchorHomeEntity {
    private int ret;
    private int maxPageId;
    private List<AnchorCategoryEntity> list;
    private AnchorCategoryEntity recommendBozhu;
    private String msg;

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

    public List<AnchorCategoryEntity> getList() {
        return list;
    }

    public void setList(List<AnchorCategoryEntity> list) {
        this.list = list;
    }

    public AnchorCategoryEntity getRecommendBozhu() {
        return recommendBozhu;
    }

    public void setRecommendBozhu(AnchorCategoryEntity recommendBozhu) {
        this.recommendBozhu = recommendBozhu;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
