package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.ClassInfoBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface ILoginView {
    /**
     * 获取用户名
     *
     * @return
     */
    public String getUserName();

    /**
     * 获取密码
     *
     * @return
     */
    public String getPassWord();

    /**
     * 显示dialog
     */
    public void showDialog();

    /**
     * 隐藏dialog
     */
    public void hideDialog();

    /**
     * 显示信息
     */
    public void showMsg(String msg);

    /**
     * 跳入MainActivity
     */
    public void pushMainActivity(ArrayList<ClassInfoBean> classInfoBeen);

    public void clearUsername();

    public void clearPassword();

    public void closeActivity();
}
