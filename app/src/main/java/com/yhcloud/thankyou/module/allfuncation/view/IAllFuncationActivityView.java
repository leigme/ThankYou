package com.yhcloud.thankyou.module.allfuncation.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterface.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10.
 */

public interface IAllFuncationActivityView extends IBaseActivityView {
    void showList(ArrayList<FunctionBean> list);
}
