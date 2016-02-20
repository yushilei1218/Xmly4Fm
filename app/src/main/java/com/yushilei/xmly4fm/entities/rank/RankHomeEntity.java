package com.yushilei.xmly4fm.entities.rank;

import com.yushilei.xmly4fm.entities.FocusImagesEntity;

import java.util.List;

/**
 * Created by yushilei on 2016/2/5.
 */
public class RankHomeEntity {
    private List<RankCategoryEntity> datas;
    private int ret;
    private String msg;
    private FocusImagesEntity focusImages;

    public List<RankCategoryEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<RankCategoryEntity> datas) {
        this.datas = datas;
    }

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

    public FocusImagesEntity getFocusImages() {
        return focusImages;
    }

    public void setFocusImages(FocusImagesEntity focusImages) {
        this.focusImages = focusImages;
    }
}
