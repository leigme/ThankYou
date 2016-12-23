package com.yhcloud.thankyou.module.classteachers.logic;

import com.yhcloud.thankyou.mInterface.ICallListener;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface IClassTeacherListLogic {
    void getClassTeacherList(String classId, ICallListener<String> iCallListener);
}
