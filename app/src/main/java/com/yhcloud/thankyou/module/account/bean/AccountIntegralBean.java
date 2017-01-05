package com.yhcloud.thankyou.module.account.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountIntegralBean implements Serializable {

    /**
     * id : 1
     * coin : 100
     * money : 10
     */

    private String id;
    private String coin;
    private String money;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
