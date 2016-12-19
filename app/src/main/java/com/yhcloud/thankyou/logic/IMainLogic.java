package com.yhcloud.thankyou.logic;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public interface IMainLogic {
    public void getClassInfoList(String userId, ICallListener<ArrayList<ClassInfoBean>> iCallListener);
}
