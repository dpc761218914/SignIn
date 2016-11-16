package com.jxchexie.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/11/10.
 */
public class SignIn {
    @SerializedName("_id")
    private String id;//主键
    private String night_signout;
    private String night_signin;
    private String pm_signout;
    private String pm_signin;
    private String am_signout;
    private String am_signin;
    private String user_name;//用户
    private String unique_id;
    private String create_date;//记录创建时间:
    @SerializedName("__v")
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNight_signout() {
        return night_signout;
    }

    public void setNight_signout(String night_signout) {
        this.night_signout = night_signout;
    }

    public String getNight_signin() {
        return night_signin;
    }

    public void setNight_signin(String night_signin) {
        this.night_signin = night_signin;
    }

    public String getPm_signout() {
        return pm_signout;
    }

    public void setPm_signout(String pm_signout) {
        this.pm_signout = pm_signout;
    }

    public String getPm_signin() {
        return pm_signin;
    }

    public void setPm_signin(String pm_signin) {
        this.pm_signin = pm_signin;
    }

    public String getAm_signout() {
        return am_signout;
    }

    public void setAm_signout(String am_signout) {
        this.am_signout = am_signout;
    }

    public String getAm_signin() {
        return am_signin;
    }

    public void setAm_signin(String am_signin) {
        this.am_signin = am_signin;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
