package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.module.account.bean.AccountIntegralBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IIntegralView {
    void initView();
    void setTitle(String title);
    void initEvent();
    void showLoading(int msgId);
    void showToastMsg(int msgId);
    void hiddenLoading();
    void showList(ArrayList<AccountIntegralBean> list);
    void setHeadimg(String imageUrl);
    void setRealname(String realname);
    void setUsername(String username);
    void setCoin(String coin);
    void setRule(String rule);
    void showDialog(boolean exchanged, String uCoin, String coin);
    void closeDialog();
    void showDialogMsg();
}
