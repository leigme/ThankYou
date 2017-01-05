package com.yhcloud.thankyou.module.account.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRecordingBean implements Serializable {

    /**
     * content : 你赠送了道具宝宝的小书包 -1
     * buyNum : 1
     * coin : 120
     * propName : 宝宝的小书包
     * propImg : /uploads/prop/bag_bg.png
     * time : 2016-12-06
     *
     * / 账户流转类型   道具购买
     define('YH_ACCOUNT_LOG_TYPE_1', 1);
     // 账户流转类型   道具赠送
     define('YH_ACCOUNT_LOG_TYPE_2', 2);
     // 账户流转类型    道具获取
     define('YH_ACCOUNT_LOG_TYPE_3', 3);
     // 账户流转类型   充值优点
     define('YH_ACCOUNT_LOG_TYPE_4', 4);
     // 账户流转类型    购买积分
     define('YH_ACCOUNT_LOG_TYPE_5', 5);
     // 账户流转类型    购买资源
     define('YH_ACCOUNT_LOG_TYPE_6', 6);
     // 账户流转类型    获取优点
     define('YH_ACCOUNT_LOG_TYPE_7', 7);
     *
     */
    private String Id;
    private String propType;
    private String type;
    private String buyNum;
    private String coin;
    private String propName;
    private String propImg;
    private String time;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropImg() {
        return propImg;
    }

    public void setPropImg(String propImg) {
        this.propImg = propImg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
