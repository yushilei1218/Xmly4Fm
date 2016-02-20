package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/25.
 */
public class SuggestAllEntity {
    private String sq;
    private TrackDosEntity track;
    private AlbumDosEntity album;
    private UserInfoDosEntity user;

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
    }

    public TrackDosEntity getTrack() {
        return track;
    }

    public void setTrack(TrackDosEntity track) {
        this.track = track;
    }

    public AlbumDosEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDosEntity album) {
        this.album = album;
    }

    public UserInfoDosEntity getUser() {
        return user;
    }

    public void setUser(UserInfoDosEntity user) {
        this.user = user;
    }
}
