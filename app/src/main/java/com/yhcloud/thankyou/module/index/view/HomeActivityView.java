package com.yhcloud.thankyou.module.index.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public interface HomeActivityView extends BaseActivityView {
    void showBanner(ArrayList<String> imageUrls);
    void showFunction(ArrayList<FunctionBean> list);
    void showSpread(ArrayList<SpreadBean> list);
}
