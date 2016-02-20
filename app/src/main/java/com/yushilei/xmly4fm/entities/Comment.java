package com.yushilei.xmly4fm.entities;

/**
 * Created by yushilei on 2016/1/26.
 */
public class Comment {
    private long id;
    private long uid;
    private String nickname;
    private String smallHeader;
    private String content;
    private long createdAt;
    private long track_id;
    private long trackId;
    private long parentId;
    private long startTime;
    private int likes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getSmallHeader() {
        return smallHeader;
    }

    public void setSmallHeader(String smallHeader) {
        this.smallHeader = smallHeader;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getTrack_id() {
        return track_id;
    }

    public void setTrack_id(long track_id) {
        this.track_id = track_id;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
