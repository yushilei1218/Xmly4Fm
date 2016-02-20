package com.yushilei.xmly4fm.entities.rank;

import java.util.List;

/**
 * Created by yushilei on 2016/2/5.
 */
public class RankEntity {
    private List<FirstKResultEntity> firstKResults;

    private String coverPath;
    private String title;
    private String subtitle;
    private String key;
    private int orderNum;
    private String contentType;
    private String rankingRule;
    private int period;
    private int categoryId;
    private long firstId;
    private String firstTitle;
    private String calcPeriod;
    private int top;

    public List<FirstKResultEntity> getFirstKResults() {
        return firstKResults;
    }

    public void setFirstKResults(List<FirstKResultEntity> firstKResults) {
        this.firstKResults = firstKResults;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRankingRule() {
        return rankingRule;
    }

    public void setRankingRule(String rankingRule) {
        this.rankingRule = rankingRule;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getFirstId() {
        return firstId;
    }

    public void setFirstId(long firstId) {
        this.firstId = firstId;
    }

    public String getFirstTitle() {
        return firstTitle;
    }

    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public String getCalcPeriod() {
        return calcPeriod;
    }

    public void setCalcPeriod(String calcPeriod) {
        this.calcPeriod = calcPeriod;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
