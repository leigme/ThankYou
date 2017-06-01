package com.yhcloud.thankyou.module.loading.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.module.loading.manage.LoadingManage;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends BaseActivity {

    private String TAG = LoadingActivity.class.getSimpleName();

    private LogicService mService;
    private UserInfo mUserInfo;
    private Activity mActivity;

    private LoadingManage mLoadingManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.print(TAG, "开始跑程序咯4");
//        setContentView(R.layout.activity_loading);
//        mActivity = this;
        //隐藏系统状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Tools.print(TAG, "开始跑程序咯5");
        //自动升级判断

//        mUserInfo = new UserInfo();
//        Intent intent = new Intent(this, LogicService.class);
//        this.bindService(intent, new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder binder) {
//
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        }, Service.BIND_AUTO_CREATE);
    }

    //延迟3秒进入登录界面
    public void goLoginActivity() {
        Tools.print(TAG, "开始跑程序咯6");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initDatas() {
        mLoadingManage = new LoadingManage(this);
        Tools.print(TAG, "开始跑程序咯7");
    }

    @Override
    public void processClick(View view) {

    }
}
