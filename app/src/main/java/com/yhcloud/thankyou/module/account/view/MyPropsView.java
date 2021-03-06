package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */

public interface MyPropsView extends BaseActivityView {
    void setCoin(String coin);
    void initRight(boolean canOnClick);
    void showList(ArrayList<AccountPropBean> list, boolean canOnClick);
    void initViewStub();
    void showDefault(boolean showed);
    void initViewStub(boolean canGive);
    int getNum();
    void showNum(int num);
}
