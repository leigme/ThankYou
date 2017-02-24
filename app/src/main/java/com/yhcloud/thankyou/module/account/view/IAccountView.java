package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.minterface.IBaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IAccountView extends IBaseActivityView {
    void showBanner(ArrayList<String> list);
    void showFunction(ArrayList<AccountFunctionBean> list);
}
