package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.module.account.bean.AccountPropBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface IMyPropsView {
    void initView();
    void setTitle(String title);
    void setRightText(String text);
    void setCoin(String coin);
    void initRight(boolean canOnClick);
    void initEvent();
    void showLoading(int resId);
    void hiddenLoading();
    void showList(ArrayList<AccountPropBean> list, boolean canOnClick);
    void showMsg(int msgId);
    void showMsg(String msg);
    void initViewStub();
    void showDefault(boolean showed);
    void initViewStub(boolean canGive);
    int getNum();
    void showNum(int num);
}
