package com.yhcloud.thankyou.module.aboutus.bean;

public class AboutUsBean {
    private String Id;
    private String Content;
    private String BarcodeURL;
    private String BarcodeSavePath;
    private String UpdateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getBarcodeURL() {
        return BarcodeURL;
    }

    public void setBarcodeURL(String barcodeURL) {
        BarcodeURL = barcodeURL;
    }

    public String getBarcodeSavePath() {
        return BarcodeSavePath;
    }

    public void setBarcodeSavePath(String barcodeSavePath) {
        BarcodeSavePath = barcodeSavePath;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
