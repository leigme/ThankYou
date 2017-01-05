package com.yhcloud.thankyou.module.account.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountPropBean implements Serializable {

    /**
     * propNum : 5
     * propName : 优秀的大红花
     * propPrice : 120
     * propImg : /uploads/prop/-flower_bg.png
     * description : 这是一朵大红花
     */

    private String propId;
    private String propNum;
    private String propName;
    private String propPrice;
    private String propImg;
    private String description;
    private int saleNum;
    private boolean selected;

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public String getPropNum() {
        return propNum;
    }

    public void setPropNum(String propNum) {
        this.propNum = propNum;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropPrice() {
        return propPrice;
    }

    public void setPropPrice(String propPrice) {
        this.propPrice = propPrice;
    }

    public String getPropImg() {
        return propImg;
    }

    public void setPropImg(String propImg) {
        this.propImg = propImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
