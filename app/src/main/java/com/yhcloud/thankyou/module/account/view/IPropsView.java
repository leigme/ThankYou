package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.mInterfacea.IBaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IPropsView extends IBaseActivityView {
    void setCoin(String coin);
    void showPropList(ArrayList<AccountPropBean> list);
}
