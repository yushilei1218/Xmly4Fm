package com.yushilei.xmly4fm.entities.radio;

/**
 * Created by yushilei on 2016/2/4.
 */
public class RadioHomeEntity {
    private RadioListEntity result;
    private int ret;
    private String msg;

    public RadioListEntity getResult() {
        return result;
    }

    public void setResult(RadioListEntity result) {
        this.result = result;
    }

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
}
