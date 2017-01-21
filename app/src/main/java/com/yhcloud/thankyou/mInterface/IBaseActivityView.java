package com.yhcloud.thankyou.mInterface;

import android.view.View;

/**
 * Created by leig on 2016/12/12.
 */

public interface IBaseActivityView extends IBaseView {
    void initView();
    void initEvent();
    void showDefault(boolean showed);
    void setTitle(String title);
    void setRightTitle(String title);
}
