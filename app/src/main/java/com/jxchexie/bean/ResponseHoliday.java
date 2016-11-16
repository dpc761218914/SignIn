package com.jxchexie.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 */
public class ResponseHoliday implements Serializable {
    private String status;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}


