package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.MineFunctionBean;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface IMineView {
    void initView();
    void initEvent();
    void showList(ArrayList<MineFunctionBean> list);
    void showLoading();
    void hiddenLoading();
    void showMsg(int msgId);
}
