package com.yhcloud.thankyou.module.homework.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.homework.bean.HomeworkInfoViewPagerBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface HomeworkInfoActivityView extends BaseActivityView {
    void showPhoto(boolean showed);
    void showViewPager(ArrayList<HomeworkInfoViewPagerBean> list);
    void selectViewPager(int i);
    void showDialog();
}
