package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.module.account.bean.AccountPropBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IPropsView {
    void initView();
    void initData();
    void initEvent();
    void showLoading();
    void hiddenLoading();
    void setTitle(String title);
    void setCoin(String coin);
    void showPropList(ArrayList<AccountPropBean> list);
    void showMsg(int msgId);
    void showMsg(String msg);
}
