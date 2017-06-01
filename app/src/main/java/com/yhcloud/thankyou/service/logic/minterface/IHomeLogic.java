package com.yhcloud.thankyou.service.logic.minterface;

import com.yhcloud.thankyou.comm.ResponseCallBack;

/**
 * Created by leig on 2016/11/20.
 */

public interface IHomeLogic {
    public void getSpreadList(String type, String flag, String updateTime, ResponseCallBack<String> responseCallBack);
}
