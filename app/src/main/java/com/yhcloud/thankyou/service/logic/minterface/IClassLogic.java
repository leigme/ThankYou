package com.yhcloud.thankyou.service.logic.minterface;

import com.yhcloud.thankyou.comm.ResponseCallBack;

/**
 * Created by Administrator on 2016/12/21.
 */

public interface IClassLogic {
    void getClassPeopleListForService(String userId, String classId, String updateTime, final ResponseCallBack<String> responseCallBack);
}
