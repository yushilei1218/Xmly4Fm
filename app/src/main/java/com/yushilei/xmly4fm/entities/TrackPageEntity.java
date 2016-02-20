package com.yushilei.xmly4fm.entities;

import java.util.List;

/**
 * Created by yushilei on 2016/1/26.
 */
public class TrackPageEntity {
    private TrackEntity trackInfo;
    private AlbumEntity albumInfo;
    private List<AlbumEntity> associationAlbumsInfo;
    private UserInfo userInfo;
    private CommentInfo commentInfo;

    public TrackEntity getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(TrackEntity trackInfo) {
        this.trackInfo = trackInfo;
    }

    public AlbumEntity getAlbumInfo() {
        return albumInfo;
    }

    public void setAlbumInfo(AlbumEntity albumInfo) {
        this.albumInfo = albumInfo;
    }

    public List<AlbumEntity> getAssociationAlbumsInfo() {
        return associationAlbumsInfo;
    }

    public void setAssociationAlbumsInfo(List<AlbumEntity> associationAlbumsInfo) {
        this.associationAlbumsInfo = associationAlbumsInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public CommentInfo getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentInfo commentInfo) {
        this.commentInfo = commentInfo;
    }
}
