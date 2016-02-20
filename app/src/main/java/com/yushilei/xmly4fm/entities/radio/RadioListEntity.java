package com.yushilei.xmly4fm.entities.radio;

import java.util.List;

/**
 * Created by yushilei on 2016/2/4.
 */
public class RadioListEntity {
    private List<RadioEntity> recommandRadioList;
    private List<RadioEntity> topRadioList;

    public List<RadioEntity> getRecommandRadioList() {
        return recommandRadioList;
    }

    public void setRecommandRadioList(List<RadioEntity> recommandRadioList) {
        this.recommandRadioList = recommandRadioList;
    }

    public List<RadioEntity> getTopRadioList() {
        return topRadioList;
    }

    public void setTopRadioList(List<RadioEntity> topRadioList) {
        this.topRadioList = topRadioList;
    }
}
