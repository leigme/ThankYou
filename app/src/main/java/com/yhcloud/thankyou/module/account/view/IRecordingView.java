package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.module.account.bean.AccountRecordingBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IRecordingView {
    void initView();
    void initEvent();
    void defaultText(boolean showed);
    void showLoading();
    void hiddenLoading();
    void setTitle(String title);
    void showList(ArrayList<AccountRecordingBean> list);
    void showMsg(int msgId);
    void showMsg(String msg);
}
