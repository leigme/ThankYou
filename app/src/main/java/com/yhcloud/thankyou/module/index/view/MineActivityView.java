package com.yhcloud.thankyou.module.index.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface MineActivityView extends BaseActivityView {
    void showList(ArrayList<FunctionBean> list);
}
