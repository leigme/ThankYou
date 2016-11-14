package com.yhcloud.thankyou.logic;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ILoginLogic {
    public void login(String username, String password, ICallListener iCallListener);
}
