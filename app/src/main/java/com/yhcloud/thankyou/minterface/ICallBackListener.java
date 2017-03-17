package com.yhcloud.thankyou.minterface;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ICallBackListener<T> {
    void callSuccess(T t);
    void callFailure();
}
