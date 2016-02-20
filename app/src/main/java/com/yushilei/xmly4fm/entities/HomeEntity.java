package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/22.
 */
public class HomeEntity {
    private String msg;
    private int ret;
    private SpecialColumnEntity specialColumn;//精品听单
    private RecommendEntity editorRecommendAlbums; //小编推荐
    private HotRecommendsEntity hotRecommends;//热门推荐
    private FocusImagesEntity focusImages;//热点图
    private DiscoveryColumnsEntity discoveryColumns;//发现
    private BulletAreaEntity bulletArea;

    public BulletAreaEntity getBulletArea() {
        return bulletArea;
    }

    public void setBulletArea(BulletAreaEntity bulletArea) {
        this.bulletArea = bulletArea;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public SpecialColumnEntity getSpecialColumn() {
        return specialColumn;
    }

    public void setSpecialColumn(SpecialColumnEntity specialColumn) {
        this.specialColumn = specialColumn;
    }

    public RecommendEntity getEditorRecommendAlbums() {
        return editorRecommendAlbums;
    }

    public void setEditorRecommendAlbums(RecommendEntity editorRecommendAlbums) {
        this.editorRecommendAlbums = editorRecommendAlbums;
    }

    public HotRecommendsEntity getHotRecommends() {
        return hotRecommends;
    }

    public void setHotRecommends(HotRecommendsEntity hotRecommends) {
        this.hotRecommends = hotRecommends;
    }

    public FocusImagesEntity getFocusImages() {
        return focusImages;
    }

    public void setFocusImages(FocusImagesEntity focusImages) {
        this.focusImages = focusImages;
    }

    public DiscoveryColumnsEntity getDiscoveryColumns() {
        return discoveryColumns;
    }

    public void setDiscoveryColumns(DiscoveryColumnsEntity discoveryColumns) {
        this.discoveryColumns = discoveryColumns;
    }
}
