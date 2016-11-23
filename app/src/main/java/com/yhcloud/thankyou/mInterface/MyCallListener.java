package com.yhcloud.thankyou.mInterface;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface MyCallListener<T> {
    void callSuccess(T t);
    void callFailed();
}
