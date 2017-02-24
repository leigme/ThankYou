package com.yhcloud.thankyou.module.propslist.view;

import com.yhcloud.thankyou.minterface.IBaseActivityView;
import com.yhcloud.thankyou.module.propslist.bean.PropsListViewBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public interface IPropsListActivityView extends IBaseActivityView {
    void initViewPage(ArrayList<PropsListViewBean> list);
}
