package com.yhcloud.thankyou.comm;

import android.view.View;

/**
 * 基础视图接口
 * @author leig
 * @version 20170301
 */

public interface BaseView {

    /**
     * 返回视图资源编号
     *
     * @author leig
     *
     */
    int getLayoutId();

    /**
     * 初始化视图集合
     *
     * @author leig
     *
     */
    void initViews();

    /**
     * 初始化事件集合
     *
     * @author leig
     *
     */
    void initEvents();

    /**
     * 初始化页面数据
     *
     * @author leig
     *
     */
    void initDatas();

    /**
     * 点击事件
     *
     * @author leig
     *
     */
    void processClick(View view);

    /**
     * 显示加载等待
     *
     * @author leig
     *
     */
    void showLoading(int msgId);

    /**
     * 隐藏加载等待
     *
     * @author leig
     *
     */
    void hiddenLoading();
    
    /**
     * 显示提示资源信息
     *
     * @author leig
     *
     */
    void showToastMsg(int msgId);

    /**
     * 显示提示字符串
     *
     * @author leig
     *
     */
    void showToastMsg(String msg);
}
