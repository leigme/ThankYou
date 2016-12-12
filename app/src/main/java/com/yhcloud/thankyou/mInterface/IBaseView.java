package com.yhcloud.thankyou.mInterface;

/**
 * Created by leig on 2016/12/12.
 */

public interface IBaseView {
    void initView();
    void initData();
    void initEvent();
    void showDefault(boolean showed);
    void showLoading();
    void hiddenLoading();
    void setTitle(String title);
    void setRightTitle(String title);
    void showToastMsg(int msgId);
    void showToastMsg(String msg);
}
