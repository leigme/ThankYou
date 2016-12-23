package com.yhcloud.thankyou.mInterface;

import android.view.View;

/**
 * Created by leig on 2016/12/12.
 */

public interface IBaseView {
    void initView();
    void initEvent();
    void showDefault(boolean showed);
    void showLoading(int msgId);
    void hiddenLoading();
    void setTitle(String title);
    void setRightTitle(String title);
    void showToastMsg(int msgId);
    void showToastMsg(String msg);
}
