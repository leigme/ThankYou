package com.yhcloud.thankyou.module.image.view;

import android.view.View;

import com.yhcloud.thankyou.minterface.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public interface IBigImageActivityView extends IBaseActivityView {
    void init(ArrayList<View> views);
    void setViewPager(int position);
}
