package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/23.
 */
public class AlbumDetailEntity {
    private String msg;
    private int ret;
    private AlbumEntity album;
    private TrackListEntity tracks;

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

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public TrackListEntity getTracks() {
        return tracks;
    }

    public void setTracks(TrackListEntity tracks) {
        this.tracks = tracks;
    }
}
