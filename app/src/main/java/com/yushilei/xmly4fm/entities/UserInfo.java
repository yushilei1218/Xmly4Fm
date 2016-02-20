package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/24.
 */
public class UserInfo {
    //新增主播


    //新增 用户详情
    private int ret;
    private String msg;
    private String backgroundLogo;
    private String mobileSmallLogo;
    private String mobileLargeLogo;
    private String mobileMiddleLogo;
    private String personalSignature;
    //新增 suggest 列表
    private String smallPic;
    private String logoPic;
    private String intro;
    private int verifyType;
    private int tracks_counts;
    private int followers_counts;

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

    public String getBackgroundLogo() {
        return backgroundLogo;
    }

    public void setBackgroundLogo(String backgroundLogo) {
        this.backgroundLogo = backgroundLogo;
    }

    public String getMobileSmallLogo() {
        return mobileSmallLogo;
    }

    public void setMobileSmallLogo(String mobileSmallLogo) {
        this.mobileSmallLogo = mobileSmallLogo;
    }

    public String getMobileLargeLogo() {
        return mobileLargeLogo;
    }

    public void setMobileLargeLogo(String mobileLargeLogo) {
        this.mobileLargeLogo = mobileLargeLogo;
    }

    public String getMobileMiddleLogo() {
        return mobileMiddleLogo;
    }

    public void setMobileMiddleLogo(String mobileMiddleLogo) {
        this.mobileMiddleLogo = mobileMiddleLogo;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public void setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(int verifyType) {
        this.verifyType = verifyType;
    }

    public int getTracks_counts() {
        return tracks_counts;
    }

    public void setTracks_counts(int tracks_counts) {
        this.tracks_counts = tracks_counts;
    }

    public int getFollowers_counts() {
        return followers_counts;
    }

    public void setFollowers_counts(int followers_counts) {
        this.followers_counts = followers_counts;
    }

    //原本
    private long uid;
    private String nickname;
    private String smallLogo;
    private String ptitle;
    private String personDescribe;
    private boolean isVerified;
    private boolean isFollowed;
    private int followers;
    private int followings;
    private int tracks;
    private int albums;


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPersonDescribe() {
        return personDescribe;
    }

    public void setPersonDescribe(String personDescribe) {
        this.personDescribe = personDescribe;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }
}
