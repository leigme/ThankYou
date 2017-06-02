package com.yhcloud.thankyou.comm;

/**
 * Created by leig on 2017/3/17.
 */

public interface BindServiceCallBack {

    /**
     * 服务绑定成功回调方法
     * @author leig
     * @version 20170301
     */
    void bindBaseServiceSuccess(BaseService baseService);

    /**
     * 服务绑定失败回调方法
     * @author leig
     * @version 20170301
     */
    void bindBaseServiceFailure();
}
