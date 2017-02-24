package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterfacea.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public interface IMineActivityView extends IBaseActivityView {
    void showList(ArrayList<FunctionBean> list);
}
