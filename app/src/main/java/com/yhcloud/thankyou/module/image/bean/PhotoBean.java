package com.yhcloud.thankyou.module.image.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/13.
 */

public class PhotoBean implements Serializable {

    private int mId;
    private String mUrl;

    public PhotoBean() {}

    public PhotoBean(String s) {
        this.mUrl = s;
    }

    public PhotoBean(int id, String url) {
        this.mId = id;
        this.mUrl = url;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
