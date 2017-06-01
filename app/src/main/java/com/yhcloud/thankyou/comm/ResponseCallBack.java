package com.yhcloud.thankyou.comm;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ResponseCallBack<T> {
    void callSuccess(T t);
    void callFailure();
}
