package com.yhcloud.thankyou.module.account.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRechargeBean implements Serializable {


    /**
     * id : 1
     * name : 档位名1
     * coin : 100
     * money : 10.00
     * discount : 0.200
     */

    private String id;
    private String name;
    private String coin;
    private String money;
    private String discount;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
