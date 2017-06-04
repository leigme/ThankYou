package com.yhcloud.thankyou.module.load.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.module.load.manage.LoadManage;
import com.yhcloud.thankyou.module.login.view.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadActivity extends BaseActivity {

    private String TAG = LoadActivity.class.getSimpleName();

    private LoadManage mLoadManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //自动升级判断

    }

    //延迟3秒进入登录界面
    public void goLoginActivity() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadActivity.this, LoginActivity.class);
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
        return R.layout.activity_load;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initDatas() {
        mLoadManage = new LoadManage(this);
    }

    @Override
    public void processClick(View view) {

    }
}
