package com.yhcloud.thankyou.module.login.view;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.comm.BaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface LoginActivityView extends BaseView {
    /**
     * 获取用户名
     *
     * @return
     */
    String getUserName();
    /**
     * 获取密码
     *
     * @return
     */
    String getPassWord();
    /**
     * 显示信息
     */
    void showMsg(int msg);
    /**
     * 跳入MainActivity
     */
    void pushMainActivity(ArrayList<ClassInfoBean> classInfoBeen);
    void clearUsername();
    void clearPassword();
    void closeActivity();
    void initData(String username, String password);
}
