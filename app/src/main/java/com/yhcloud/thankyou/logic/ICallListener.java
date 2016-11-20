package com.yhcloud.thankyou.logic;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ICallListener<T> {
    void callSuccess(T t);
    void callFailed();
}
