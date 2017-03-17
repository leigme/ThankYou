package com.yhcloud.thankyou.module.classteachers.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface IClassTeacherListLogic {
    void getClassTeacherList(String classId, ICallBackListener<String> iCallBackListener);
}
