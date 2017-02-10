package com.yhcloud.thankyou.module.todayrecipes.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.module.todayrecipes.view.ITodayRecipesInfoView;
import com.yhcloud.thankyou.service.LogicService;

/**
 * Created by leig on 2017/2/10.
 */
public class TodayRecipesInfoManage {

    private String TAG = getClass().getSimpleName();

    private ITodayRecipesInfoView mITodayRecipesInfoView;
    private Activity mActivity;
    private LogicService mService;
    private String dishTitle, imageUrl, dishInfo;

    public TodayRecipesInfoManage(ITodayRecipesInfoView iTodayRecipesInfoView) {
        this.mITodayRecipesInfoView = iTodayRecipesInfoView;
        this.mActivity = (Activity) mITodayRecipesInfoView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                getIntentData();
                mITodayRecipesInfoView.initView();
                mITodayRecipesInfoView.initEvent();
                if (null != dishTitle) {
                    mITodayRecipesInfoView.setTitle(dishTitle);
                }
                if (null != imageUrl) {
                    mITodayRecipesInfoView.setImage(imageUrl);
                }
                if (null != dishInfo) {
                    mITodayRecipesInfoView.setDishInfo(dishInfo);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getIntentData() {
        Intent intent = mActivity.getIntent();
        if (null != intent) {
            dishTitle = intent.getStringExtra(TodayRecipesManage.dishTitle);
            imageUrl = intent.getStringExtra(TodayRecipesManage.imageUrl);
            dishInfo = intent.getStringExtra(TodayRecipesManage.dishInfo);
        }
    }
}
