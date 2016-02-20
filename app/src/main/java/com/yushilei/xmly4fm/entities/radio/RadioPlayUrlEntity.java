package com.yushilei.xmly4fm.entities.radio;

/**
 * Created by yushilei on 2016/2/4.
 */
public class RadioPlayUrlEntity {

    /**
     * radio_24_aac : http://live.xmcdn.com/live/1065/24.m3u8
     * radio_24_ts : http://live.xmcdn.com/live/1065/24.m3u8?transcode=ts
     * radio_64_aac : http://live.xmcdn.com/live/1065/64.m3u8
     * radio_64_ts : http://live.xmcdn.com/live/1065/64.m3u8?transcode=ts
     */

    private String radio_24_aac;
    private String radio_24_ts;
    private String radio_64_aac;
    private String radio_64_ts;

    public void setRadio_24_aac(String radio_24_aac) {
        this.radio_24_aac = radio_24_aac;
    }

    public void setRadio_24_ts(String radio_24_ts) {
        this.radio_24_ts = radio_24_ts;
    }

    public void setRadio_64_aac(String radio_64_aac) {
        this.radio_64_aac = radio_64_aac;
    }

    public void setRadio_64_ts(String radio_64_ts) {
        this.radio_64_ts = radio_64_ts;
    }

    public String getRadio_24_aac() {
        return radio_24_aac;
    }

    public String getRadio_24_ts() {
        return radio_24_ts;
    }

    public String getRadio_64_aac() {
        return radio_64_aac;
    }

    public String getRadio_64_ts() {
        return radio_64_ts;
    }
}
