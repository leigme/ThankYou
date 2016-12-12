package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterface.IBaseView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface IMineView extends IBaseView{
    void showList(ArrayList<FunctionBean> list);
}
