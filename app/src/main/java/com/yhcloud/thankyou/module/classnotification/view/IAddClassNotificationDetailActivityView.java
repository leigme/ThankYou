package com.yhcloud.thankyou.module.classnotification.view;

import com.yhcloud.thankyou.minterface.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/9.
 */

public interface IAddClassNotificationDetailActivityView extends IBaseActivityView {
    String getEditTitle();
    String getEditContent();
    void showImageList(ArrayList<String> list);
}
