package com.yushilei.xmly4fm.entities.share;

/**
 * Created by yushilei on 2016/2/20.
 */
public class ShareEntity {

    /**
     * ret : 0
     * weixinPic : http://fdfs.xmcdn.com/group5/M03/B8/40/wKgDtlR8M5mzeuJaAAAZ_Za1Oso023_mobile_small.jpg
     * picUrl : http://fdfs.xmcdn.com/group5/M03/B8/40/wKgDtlR8M5mzeuJaAAAZ_Za1Oso023_web_large.jpg
     * rowKey : 79839779858995818_album_c5975b31a34445c7a20afb1c1ec9856a
     * msg : 0
     * url : http://xima.tv/qO71Bc
     * content : 我觉得《罗辑思维》听起来挺不错的，你觉得呢?http://xima.tv/qO71Bc（分享自@喜马拉雅FM）
     * lengthLimit : 140
     * title : 罗辑思维
     * shareType : album
     */

    private int ret;
    private String weixinPic;
    private String picUrl;
    private String rowKey;
    private String msg;
    private String url;
    private String content;
    private int lengthLimit;
    private String title;
    private String shareType;

    public void setRet(int ret) {
        this.ret = ret;
    }

    public void setWeixinPic(String weixinPic) {
        this.weixinPic = weixinPic;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLengthLimit(int lengthLimit) {
        this.lengthLimit = lengthLimit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public int getRet() {
        return ret;
    }

    public String getWeixinPic() {
        return weixinPic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getRowKey() {
        return rowKey;
    }

    public String getMsg() {
        return msg;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public int getLengthLimit() {
        return lengthLimit;
    }

    public String getTitle() {
        return title;
    }

    public String getShareType() {
        return shareType;
    }
}
