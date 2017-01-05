package com.yhcloud.thankyou.module.account.bean;

/**
 * Created by Administrator on 2016/12/8.
 */

public class AccountRechargePayBean {

    /**
     * pay_id : 0
     * pay_name : 支付宝
     * pay_code : alipay
     * pay_statu : true
     */

    private String pay_id;
    private String pay_name;
    private String pay_code;
    private boolean pay_statu, selected;
    private int iconId;

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public boolean isPay_statu() {
        return pay_statu;
    }

    public void setPay_statu(boolean pay_statu) {
        this.pay_statu = pay_statu;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
