package com.yhcloud.thankyou.module.propslist.view;

import com.yhcloud.thankyou.mInterface.IBaseView;
import com.yhcloud.thankyou.module.propslist.bean.PropsListViewBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public interface IPropsListView extends IBaseView {
    void initViewPage(ArrayList<PropsListViewBean> list);
    void showViewPage();
//    void showSendPage();
//    void showReceivePage();
}
