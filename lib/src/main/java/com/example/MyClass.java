package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.yushilei.xmly4fm.entities");
        schema.setDefaultJavaPackageDao("com.yushilei.xmly4fm.dao");
        // TODO: 2016/1/22 专辑 
        Entity albumEntity = schema.addEntity("AlbumEntity");
        albumEntity.addLongProperty("albumId").primaryKey();
        albumEntity.addLongProperty("categoryId");
        albumEntity.addStringProperty("title");
        albumEntity.addStringProperty("coverOrigin");
        albumEntity.addStringProperty("coverSmall");
        albumEntity.addStringProperty("coverMiddle");
        albumEntity.addStringProperty("coverLarge");
        albumEntity.addStringProperty("coverWebLarge");
        albumEntity.addDateProperty("createdAt");
        albumEntity.addDateProperty("updatedAt");
        albumEntity.addLongProperty("uid");
        albumEntity.addStringProperty("nickname");
        albumEntity.addBooleanProperty("isVerified");
        albumEntity.addStringProperty("avatarPath");
        albumEntity.addStringProperty("intro");
        albumEntity.addStringProperty("introRich");
        albumEntity.addIntProperty("tracks");
        albumEntity.addIntProperty("shares");
        albumEntity.addIntProperty("playTimes");
        albumEntity.addIntProperty("status");
        albumEntity.addIntProperty("serializeStatus");
        albumEntity.addIntProperty("serialState");
        albumEntity.addIntProperty("playTrackId");
        albumEntity.addBooleanProperty("hasNew");
        albumEntity.addBooleanProperty("isFavorite");
        albumEntity.addBooleanProperty("isRecordDesc");
        //后添加
        albumEntity.addStringProperty("trackTitle");
        albumEntity.addStringProperty("tags");
        albumEntity.addLongProperty("trackId");
        albumEntity.addIntProperty("isFinished");
        // TODO: 2016/1/22  推荐
        Entity recommendEntity = schema.addEntity("RecommendEntity");
        recommendEntity.addIdProperty();
        recommendEntity.addStringProperty("title");
        recommendEntity.addStringProperty("contentType");
        recommendEntity.addBooleanProperty("isFinished");
        recommendEntity.addIntProperty("categoryId");
        recommendEntity.addIntProperty("count");
        recommendEntity.addBooleanProperty("hasMore");
        // TODO: 2016/1/22 音频
        Entity trackEntity = schema.addEntity("TrackEntity");
        trackEntity.addLongProperty("trackId").primaryKey();
        trackEntity.addLongProperty("uid");
        trackEntity.addStringProperty("playUrl64");
        trackEntity.addStringProperty("playUrl32");
        trackEntity.addStringProperty("downloadUrl");
        trackEntity.addStringProperty("playPathAacv164");
        trackEntity.addStringProperty("playPathAacv224");
        trackEntity.addStringProperty("downloadAacUrl");
        trackEntity.addStringProperty("title");
        trackEntity.addDoubleProperty("duration");
        trackEntity.addLongProperty("albumId");
        trackEntity.addStringProperty("albumTitle");
        trackEntity.addStringProperty("albumImage");
        trackEntity.addIntProperty("processState");
        trackEntity.addDateProperty("createdAt");
        trackEntity.addStringProperty("coverSmall");
        trackEntity.addStringProperty("coverMiddle");
        trackEntity.addStringProperty("coverLarge");
        trackEntity.addStringProperty("nickname");
        trackEntity.addStringProperty("smallLogo");
        trackEntity.addIntProperty("userSource");
        trackEntity.addIntProperty("orderNum");
        trackEntity.addIntProperty("opType");
        trackEntity.addBooleanProperty("isPublic");
        trackEntity.addIntProperty("likes");
        trackEntity.addIntProperty("playtimes");
        trackEntity.addIntProperty("comments");
        trackEntity.addIntProperty("shares");
        trackEntity.addIntProperty("status");
        trackEntity.addIntProperty("downloadSize");
        trackEntity.addIntProperty("downloadAacSize");
        // TODO: 2016/1/22 热点图
        Entity focusImageEntity = schema.addEntity("FocusImageEntity");
        focusImageEntity.addLongProperty("id").primaryKey();
        focusImageEntity.addStringProperty("shortTitle");
        focusImageEntity.addStringProperty("longTitle");
        focusImageEntity.addStringProperty("pic");
        focusImageEntity.addIntProperty("type");
        focusImageEntity.addLongProperty("uid");
        focusImageEntity.addLongProperty("trackId");
        focusImageEntity.addBooleanProperty("isShare");
        focusImageEntity.addBooleanProperty("is_External_url");
        // TODO: 2016/1/22 发现
        Entity discoveryEntity = schema.addEntity("DiscoveryEntity");
        discoveryEntity.addLongProperty("id").primaryKey();
        discoveryEntity.addIntProperty("orderNum");
        discoveryEntity.addStringProperty("title");
        discoveryEntity.addStringProperty("subtitle");
        discoveryEntity.addStringProperty("coverPath");
        discoveryEntity.addStringProperty("contentType");
        discoveryEntity.addStringProperty("url");
        discoveryEntity.addStringProperty("sharePic");
        discoveryEntity.addBooleanProperty("enableShare");
        discoveryEntity.addBooleanProperty("isHot");
        discoveryEntity.addBooleanProperty("isExternalUrl");
        discoveryEntity.addIntProperty("contentUpdatedAt");
        // TODO: 2016/1/22 精品听单
        Entity specialEntity = schema.addEntity("SpecialEntity");
        specialEntity.addLongProperty("specialId").primaryKey();
        specialEntity.addStringProperty("title");
        specialEntity.addIntProperty("columnType");
        specialEntity.addStringProperty("subtitle");
        specialEntity.addStringProperty("footnote");
        specialEntity.addStringProperty("coverPath");
        specialEntity.addIntProperty("contentType");
        specialEntity.addStringProperty("nickname");
        specialEntity.addStringProperty("smallLogo");
        specialEntity.addLongProperty("uid");
        specialEntity.addStringProperty("intro");
        specialEntity.addStringProperty("coverPathBig");
        specialEntity.addStringProperty("personalSignature");

        try {
            new DaoGenerator().generateAll(schema, "lib/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
