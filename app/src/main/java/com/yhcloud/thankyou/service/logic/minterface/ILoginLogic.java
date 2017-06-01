package com.yhcloud.thankyou.service.logic.minterface;

import com.yhcloud.thankyou.comm.ResponseCallBack;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ILoginLogic {
    public void login(String username, String password, ResponseCallBack<String> responseCallBack);
}
