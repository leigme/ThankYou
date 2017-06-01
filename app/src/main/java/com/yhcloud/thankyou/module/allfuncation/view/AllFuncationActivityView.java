package com.yhcloud.thankyou.module.allfuncation.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10.
 */

public interface AllFuncationActivityView extends BaseActivityView {
    void showAddList(ArrayList<FunctionBean> list);
    void showNoneList(ArrayList<FunctionBean> list);
    void setEditMode(boolean edited);
}
