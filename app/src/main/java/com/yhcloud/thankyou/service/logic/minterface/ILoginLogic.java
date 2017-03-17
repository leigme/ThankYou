package com.yhcloud.thankyou.service.logic.minterface;

import com.yhcloud.thankyou.minterface.ICallBackListener;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ILoginLogic {
    public void login(String username, String password, ICallBackListener<String> iCallBackListener);
}
