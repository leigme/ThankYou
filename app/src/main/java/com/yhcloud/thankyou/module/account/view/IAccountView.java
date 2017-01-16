package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IAccountView {
    void initView();
    void initEvent();
    void showLoading();
    void hiddenLoading();
    void setTitle(String title);
    void showBanner(List<String> list);
    void showFunction(ArrayList<AccountFunctionBean> list);
}
