package com.yhcloud.thankyou.comm;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface ResponseCallBack<T> {
    
    /**
     * 响应成功回调方法
     * @author leig
     * @version 20170301
     */
    void callSuccess(T t);
    
    /**
     * 响应失败回调方法
     * @author leig
     * @version 20170301
     */
    void callFailure();
}
