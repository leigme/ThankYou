package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface PropsView extends BaseActivityView {
    void setCoin(String coin);
    void showPropList(ArrayList<AccountPropBean> list);
}
