package com.yhcloud.thankyou.logic;

import com.yhcloud.thankyou.minterface.ICallListener;

/**
 * Created by leig on 2016/11/20.
 */

public interface IHomeLogic {
    public void getSpreadList(String type, String flag, String updateTime, ICallListener<String> iCallListener);
}
