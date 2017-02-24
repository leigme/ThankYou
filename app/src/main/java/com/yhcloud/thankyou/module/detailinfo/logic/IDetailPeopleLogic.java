package com.yhcloud.thankyou.module.detailinfo.logic;

import com.yhcloud.thankyou.minterface.ICallListener;

/**
 * Created by Administrator on 2016/12/22.
 */

public interface IDetailPeopleLogic {
    void getDetailInfo(String userId, String uId, final ICallListener<String> iCallListener);
}
