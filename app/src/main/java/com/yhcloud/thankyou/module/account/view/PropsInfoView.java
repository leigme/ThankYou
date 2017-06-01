package com.yhcloud.thankyou.module.account.view;


import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface PropsInfoView extends BaseActivityView {
    void initData(AccountPropBean accountPropBean);
    void setPeopleNum(int peopleNum);
    void showDialog(int coin, String msg, boolean canBuy);
    int getBuynum();
    void setBuynum(String num);
    void setSumnum(String sumCoin);
    void showResult(String title, String msg);
    void closePage();
}
