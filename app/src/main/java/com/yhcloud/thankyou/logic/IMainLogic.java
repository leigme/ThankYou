package com.yhcloud.thankyou.logic;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.mInterfacea.ICallListener;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public interface IMainLogic {
    void getClassInfoList(String userId, ICallListener<ArrayList<ClassInfoBean>> iCallListener);
    void getFriendList(String userId, String updateTime, final ICallListener<String> iCallListener);
}
