package com.yhcloud.thankyou.module.account.alipay.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/10.
 */

public class PayInfoBean implements Serializable {

    /**
     * user_id : 21
     * user_name : 测试用户
     * order_id : 43
     * dingdan : 1634491244
     * alimoney : 0.01
     * epay_alipay_seller_email : yang.huan@yhcloud.com.cn
     * epay_alipay_partner : 2088711073526189
     * epay_alipay_key : bibt9e5dtmqayw6maawr1fykc2vb4leq
     * site_url : http://192.168.0.139/yhmall
     */

    private String user_id;
    private String user_name;
    private int order_id;
    private String dingdan;
    private double alimoney;
    private String epay_alipay_seller_email;
    private String epay_alipay_partner;
    private String epay_alipay_key;
    private String site_url;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDingdan() {
        return dingdan;
    }

    public void setDingdan(String dingdan) {
        this.dingdan = dingdan;
    }

    public double getAlimoney() {
        return alimoney;
    }

    public void setAlimoney(double alimoney) {
        this.alimoney = alimoney;
    }

    public String getEpay_alipay_seller_email() {
        return epay_alipay_seller_email;
    }

    public void setEpay_alipay_seller_email(String epay_alipay_seller_email) {
        this.epay_alipay_seller_email = epay_alipay_seller_email;
    }

    public String getEpay_alipay_partner() {
        return epay_alipay_partner;
    }

    public void setEpay_alipay_partner(String epay_alipay_partner) {
        this.epay_alipay_partner = epay_alipay_partner;
    }

    public String getEpay_alipay_key() {
        return epay_alipay_key;
    }

    public void setEpay_alipay_key(String epay_alipay_key) {
        this.epay_alipay_key = epay_alipay_key;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }
}
