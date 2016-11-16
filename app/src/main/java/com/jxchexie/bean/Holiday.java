package com.jxchexie.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/11/10.
 */
public class Holiday {
    @SerializedName("_id")
    private String id;//主键
    private String begin_time;//开始时间
    private String end_time;//结束时间
    private String reason;//原因
    private String user_name;//用户
    private String feedback;//反馈
    @SerializedName("__v")
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id='" + id + '\'' +
                ", begin_time='" + begin_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", reason='" + reason + '\'' +
                ", user_name='" + user_name + '\'' +
                ", feedback='" + feedback + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
