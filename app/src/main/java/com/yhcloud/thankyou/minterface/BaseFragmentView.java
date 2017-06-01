package com.yhcloud.thankyou.minterface;

import com.yhcloud.thankyou.comm.BaseView;

/**
 * Created by Administrator on 2017/1/22.
 */

public interface BaseFragmentView extends BaseView {
    void initView();
    void initEvent();
    void showDefault(boolean showed);
}
