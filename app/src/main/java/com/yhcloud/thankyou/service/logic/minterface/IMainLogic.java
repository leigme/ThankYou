package com.yhcloud.thankyou.service.logic.minterface;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.comm.ResponseCallBack;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public interface IMainLogic {
    void getClassInfoList(String userId, ResponseCallBack<ArrayList<ClassInfoBean>> responseCallBack);
    void getFriendList(String userId, String updateTime, final ResponseCallBack<String> responseCallBack);
}
