package com.yhcloud.thankyou.logic;

import com.yhcloud.thankyou.bean.UserInfo;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ICallListener<T> {
    void callSuccess(T t);
    void callFailed();
}
