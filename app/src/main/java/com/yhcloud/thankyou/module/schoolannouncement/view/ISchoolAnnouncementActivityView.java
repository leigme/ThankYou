package com.yhcloud.thankyou.module.schoolannouncement.view;

import com.yhcloud.thankyou.minterface.IBaseActivityView;
import com.yhcloud.thankyou.module.schoolannouncement.bean.SchoolAnnouncementBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/6.
 */

public interface ISchoolAnnouncementActivityView extends IBaseActivityView {
    void completeRefreshList();
    void showList(ArrayList<SchoolAnnouncementBean> list);
}
