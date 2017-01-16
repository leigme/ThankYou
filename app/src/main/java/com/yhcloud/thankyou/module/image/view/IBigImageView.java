package com.yhcloud.thankyou.module.image.view;

import android.view.View;

import com.yhcloud.thankyou.mInterface.IBaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public interface IBigImageView extends IBaseView {
    void init(ArrayList<View> views);
    void setViewPager(int position);
}
