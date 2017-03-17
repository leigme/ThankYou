package com.yhcloud.thankyou.service.logic.minterface;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.minterface.ICallBackListener;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public interface IMainLogic {
    void getClassInfoList(String userId, ICallBackListener<ArrayList<ClassInfoBean>> iCallBackListener);
    void getFriendList(String userId, String updateTime, final ICallBackListener<String> iCallBackListener);
}
