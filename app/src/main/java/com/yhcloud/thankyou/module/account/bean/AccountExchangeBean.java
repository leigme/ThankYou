package com.yhcloud.thankyou.module.account.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/9.
 */

public class AccountExchangeBean implements Serializable {

    /**
     * Id : 1
     * coin : 10
     * money : 10
     */

    private String Id;
    private int coin;
    private double money;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
