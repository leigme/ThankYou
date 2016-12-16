package com.yhcloud.thankyou.manage;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.IMineView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public class MineManage {

    private IMineView mIMineView;
    private LogicService mService;
    private Fragment mFragment;
    private ArrayList<FunctionBean> mBeen;

    public MineManage(IMineView mineView, LogicService service) {
        this.mIMineView = mineView;
        this.mFragment = (Fragment) mIMineView;
        this.mService = service;
        mIMineView.showLoading();
        mBeen = new ArrayList<>();
        mIMineView.initView();
        initMineFunction();
        mIMineView.hiddenLoading();
    }

    public void initMineFunction() {
        SparseArray<FunctionBean> sparseArray = Tools.initFunction();
        for (int i = 0; i < sparseArray.size(); i++) {
            FunctionBean functionBean = new FunctionBean();
            switch (i) {
                case 0:
                    mBeen.add(0, new FunctionBean());
                    break;
                case 2:
                    mBeen.add(new FunctionBean());
                    break;
                case 4:
                    mBeen.add(new FunctionBean());
                    break;

            }
            mBeen.add(sparseArray.get(i));
        }
        mIMineView.showList(mBeen);
    }
}
