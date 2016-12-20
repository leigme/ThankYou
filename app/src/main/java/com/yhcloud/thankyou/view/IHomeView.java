package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterface.IBaseView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public interface IHomeView extends IBaseView {
    void showBanner(ArrayList<String> imageUrls);
    void showFunction(ArrayList<FunctionBean> list);
    void setReadIcon(int iconId);
    void setReadTitle(String title);
    void showSpread();
}
