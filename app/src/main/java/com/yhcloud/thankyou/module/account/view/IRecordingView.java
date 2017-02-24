package com.yhcloud.thankyou.module.account.view;

import com.yhcloud.thankyou.mInterfacea.IBaseActivityView;
import com.yhcloud.thankyou.module.account.bean.AccountRecordingBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface IRecordingView extends IBaseActivityView {
    void defaultText(boolean showed);
    void showList(ArrayList<AccountRecordingBean> list);
}
