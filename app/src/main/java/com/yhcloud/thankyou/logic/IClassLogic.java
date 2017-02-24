package com.yhcloud.thankyou.logic;

import com.yhcloud.thankyou.minterface.ICallListener;

/**
 * Created by Administrator on 2016/12/21.
 */

public interface IClassLogic {
    void getClassPeopleListForService(String userId, String classId, String updateTime, final ICallListener<String> iCallListener);
}
