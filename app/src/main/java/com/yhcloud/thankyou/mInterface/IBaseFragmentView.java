package com.yhcloud.thankyou.mInterface;

/**
 * Created by Administrator on 2017/1/22.
 */

public interface IBaseFragmentView extends IBaseView {
    void initView();
    void initEvent();
    void showDefault(boolean showed);
}