package com.yhcloud.thankyou.logic;

import com.yhcloud.thankyou.mInterface.ICallListener;

/**
 * Created by leig on 2016/11/20.
 */

public interface IHomeLogic {
    public void getImageUrls(String updateTime, ICallListener iCallListener);
}
