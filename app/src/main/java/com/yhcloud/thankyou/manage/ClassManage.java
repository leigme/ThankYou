package com.yhcloud.thankyou.manage;

import android.support.v4.app.Fragment;

import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IClassView;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ClassManage {
    private IClassView mIClassView;
    private Fragment mFragment;
    private LogicService mService;

    public ClassManage(IClassView iClassView, LogicService service) {
        this.mIClassView = iClassView;
        this.mFragment = (Fragment) mIClassView;
        this.mService = service;
    }
}
