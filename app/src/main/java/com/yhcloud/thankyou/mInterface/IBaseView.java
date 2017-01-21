package com.yhcloud.thankyou.mInterface;

/**
 * Created by Administrator on 2017/1/21.
 */

public interface IBaseView {
    void showLoading(int msgId);
    void hiddenLoading();
    void showDialog(String title, String msg);
    void showDialog(String msg);
    void showToastMsg(int msgId);
    void showToastMsg(String msg);
}
