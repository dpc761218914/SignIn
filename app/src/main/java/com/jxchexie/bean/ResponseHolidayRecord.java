package com.jxchexie.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class ResponseHolidayRecord implements Serializable {
    private String status;
    private ArrayList<Holiday> msg;

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setMsg(ArrayList<Holiday> msg) {
        this.msg = msg;
    }
    public ArrayList<Holiday> getMsg() {
        return msg;
    }
}


