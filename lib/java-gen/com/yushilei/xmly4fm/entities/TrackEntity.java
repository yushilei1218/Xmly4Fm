package com.yushilei.xmly4fm.entities;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TRACK_ENTITY".
 */
public class TrackEntity {

    private Long trackId;
    private Long uid;
    private String playUrl64;
    private String playUrl32;
    private String downloadUrl;
    private String playPathAacv164;
    private String playPathAacv224;
    private String downloadAacUrl;
    private String title;
    private Double duration;
    private Long albumId;
    private String albumTitle;
    private String albumImage;
    private Integer processState;
    private java.util.Date createdAt;
    private String coverSmall;
    private String coverMiddle;
    private String coverLarge;
    private String nickname;
    private String smallLogo;
    private Integer userSource;
    private Integer orderNum;
    private Integer opType;
    private Boolean isPublic;
    private Integer likes;
    private Integer playtimes;
    private Integer comments;
    private Integer shares;
    private Integer status;
    private Integer downloadSize;
    private Integer downloadAacSize;

    public TrackEntity() {
    }

    public TrackEntity(Long trackId) {
        this.trackId = trackId;
    }

    public TrackEntity(Long trackId, Long uid, String playUrl64, String playUrl32, String downloadUrl, String playPathAacv164, String playPathAacv224, String downloadAacUrl, String title, Double duration, Long albumId, String albumTitle, String albumImage, Integer processState, java.util.Date createdAt, String coverSmall, String coverMiddle, String coverLarge, String nickname, String smallLogo, Integer userSource, Integer orderNum, Integer opType, Boolean isPublic, Integer likes, Integer playtimes, Integer comments, Integer shares, Integer status, Integer downloadSize, Integer downloadAacSize) {
        this.trackId = trackId;
        this.uid = uid;
        this.playUrl64 = playUrl64;
        this.playUrl32 = playUrl32;
        this.downloadUrl = downloadUrl;
        this.playPathAacv164 = playPathAacv164;
        this.playPathAacv224 = playPathAacv224;
        this.downloadAacUrl = downloadAacUrl;
        this.title = title;
        this.duration = duration;
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.albumImage = albumImage;
        this.processState = processState;
        this.createdAt = createdAt;
        this.coverSmall = coverSmall;
        this.coverMiddle = coverMiddle;
        this.coverLarge = coverLarge;
        this.nickname = nickname;
        this.smallLogo = smallLogo;
        this.userSource = userSource;
        this.orderNum = orderNum;
        this.opType = opType;
        this.isPublic = isPublic;
        this.likes = likes;
        this.playtimes = playtimes;
        this.comments = comments;
        this.shares = shares;
        this.status = status;
        this.downloadSize = downloadSize;
        this.downloadAacSize = downloadAacSize;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPlayUrl64() {
        return playUrl64;
    }

    public void setPlayUrl64(String playUrl64) {
        this.playUrl64 = playUrl64;
    }

    public String getPlayUrl32() {
        return playUrl32;
    }

    public void setPlayUrl32(String playUrl32) {
        this.playUrl32 = playUrl32;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPlayPathAacv164() {
        return playPathAacv164;
    }

    public void setPlayPathAacv164(String playPathAacv164) {
        this.playPathAacv164 = playPathAacv164;
    }

    public String getPlayPathAacv224() {
        return playPathAacv224;
    }

    public void setPlayPathAacv224(String playPathAacv224) {
        this.playPathAacv224 = playPathAacv224;
    }

    public String getDownloadAacUrl() {
        return downloadAacUrl;
    }

    public void setDownloadAacUrl(String downloadAacUrl) {
        this.downloadAacUrl = downloadAacUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMiddle() {
        return coverMiddle;
    }

    public void setCoverMiddle(String coverMiddle) {
        this.coverMiddle = coverMiddle;
    }

    public String getCoverLarge() {
        return coverLarge;
    }

    public void setCoverLarge(String coverLarge) {
        this.coverLarge = coverLarge;
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

    public Integer getUserSource() {
        return userSource;
    }

    public void setUserSource(Integer userSource) {
        this.userSource = userSource;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getPlaytimes() {
        return playtimes;
    }

    public void setPlaytimes(Integer playtimes) {
        this.playtimes = playtimes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(Integer downloadSize) {
        this.downloadSize = downloadSize;
    }

    public Integer getDownloadAacSize() {
        return downloadAacSize;
    }

    public void setDownloadAacSize(Integer downloadAacSize) {
        this.downloadAacSize = downloadAacSize;
    }

}
