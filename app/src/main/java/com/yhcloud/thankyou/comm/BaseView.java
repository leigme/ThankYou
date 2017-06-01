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
     * @author leig
     * @version 20170301
     */
    int getLayoutId();

    /**
     * 初始化视图集合
     * @author leig
     * @version 20170301
     */
    void initViews();

    /**
     * 初始化事件集合
     * @author leig
     * @version 20170301
     */
    void initEvents();

    /**
     * 初始化页面数据
     * @author leig
     * @version 20170301
     */
    void initDatas();

    /**
     * 点击事件
     * @author leig
     * @version 20170301
     */
    void processClick(View view);

    /**
     * 显示加载等待
     * @author leig
     * @version 20170301
     */
    void showLoading(int msgId);

    /**
     * 隐藏加载等待
     * @author leig
     * @version 20170301
     */
    void hiddenLoading();

    /**
     * 显示带标题的对话框
     * @author leig
     * @version 20170301
     */
    void showDialog(String title, String msg);

    /**
     * 显示需确认的提示信息
     * @author leig
     * @version 20170301
     */
    void showDialog(String msg);
    
    /**
     * 显示提示资源信息
     * @author leig
     * @version 20170301
     */
    void showToastMsg(int msgId);

    /**
     * 显示提示字符串
     * @author leig
     * @version 20170301
     */
    void showToastMsg(String msg);
}
