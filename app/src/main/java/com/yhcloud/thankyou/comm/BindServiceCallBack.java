package com.yhcloud.thankyou.comm;

/**
 * Created by leig on 2017/3/17.
 */

public interface BindServiceCallBack {
    void bindBaseServiceSuccess(BaseService baseService);
    void bindBaseServiceFailure();
}
