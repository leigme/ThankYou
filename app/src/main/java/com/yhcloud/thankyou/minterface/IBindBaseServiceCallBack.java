package com.yhcloud.thankyou.minterface;

import com.yhcloud.thankyou.service.BaseService;

/**
 * Created by leig on 2017/3/17.
 */

public interface IBindBaseServiceCallBack {
    void bindBaseServiceSuccess(BaseService baseService);
    void bindBaseServiceFailure();
}
