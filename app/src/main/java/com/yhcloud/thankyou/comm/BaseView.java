package com.yhcloud.thankyou.comm;

/**
 * 基础视图接口
 * @author leig
 * @version 20170301
 */

public interface BaseView {

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
