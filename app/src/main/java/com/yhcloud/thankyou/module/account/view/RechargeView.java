package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountRechargeBean;
import com.yhcloud.thankyou.module.account.bean.AccountRechargePayBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface RechargeView extends BaseActivityView {
    void showRechargeList(ArrayList<AccountRechargeBean> list);
    void setHeadimg(String imageUrl);
    void setRealname(String realname);
    void setUsername(String username);
    void setCoin(String coin);
    void setRule(String rule);
    void showDialog(ArrayList<AccountRechargePayBean> list, String payNum);
    void closeDialog();
}
