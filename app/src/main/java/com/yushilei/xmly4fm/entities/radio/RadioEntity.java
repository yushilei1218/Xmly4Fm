package com.yushilei.xmly4fm.entities.radio;

/**
 * Created by yushilei on 2016/2/4.
 */
public class RadioEntity {
    private RadioPlayUrlEntity radioPlayUrl;
    private long programId;
    private String programName;
    private int programScheduleId;
    private String radioCoverLarge;
    private String radioCoverSmall;
    private long radioId;
    private int radioPlayCount;
    private String rname;

    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public RadioPlayUrlEntity getRadioPlayUrl() {
        return radioPlayUrl;
    }

    public void setRadioPlayUrl(RadioPlayUrlEntity radioPlayUrl) {
        this.radioPlayUrl = radioPlayUrl;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getProgramScheduleId() {
        return programScheduleId;
    }

    public void setProgramScheduleId(int programScheduleId) {
        this.programScheduleId = programScheduleId;
    }

    public String getRadioCoverLarge() {
        return radioCoverLarge;
    }

    public void setRadioCoverLarge(String radioCoverLarge) {
        this.radioCoverLarge = radioCoverLarge;
    }

    public String getRadioCoverSmall() {
        return radioCoverSmall;
    }

    public void setRadioCoverSmall(String radioCoverSmall) {
        this.radioCoverSmall = radioCoverSmall;
    }

    public long getRadioId() {
        return radioId;
    }

    public void setRadioId(long radioId) {
        this.radioId = radioId;
    }

    public int getRadioPlayCount() {
        return radioPlayCount;
    }

    public void setRadioPlayCount(int radioPlayCount) {
        this.radioPlayCount = radioPlayCount;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }
}
