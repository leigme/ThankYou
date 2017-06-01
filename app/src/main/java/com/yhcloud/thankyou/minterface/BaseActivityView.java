package com.yhcloud.thankyou.minterface;

import com.yhcloud.thankyou.comm.BaseView;

/**
 * Created by leig on 2016/12/12.
 */

public interface BaseActivityView extends BaseView {
    void initView();
    void initEvent();
    void showDefault(boolean showed);
    void setTitle(String title);
    void setRightTitle(String title);
}
