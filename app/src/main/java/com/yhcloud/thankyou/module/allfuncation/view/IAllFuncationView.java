package com.yhcloud.thankyou.module.allfuncation.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterface.IBaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10.
 */

public interface IAllFuncationView extends IBaseView {
    void showList(ArrayList<FunctionBean> list);
}
