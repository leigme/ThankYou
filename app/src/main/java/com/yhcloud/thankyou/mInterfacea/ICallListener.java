package com.yhcloud.thankyou.mInterfacea;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ICallListener<T> {
    void callSuccess(T t);
    void callFailed();
}
