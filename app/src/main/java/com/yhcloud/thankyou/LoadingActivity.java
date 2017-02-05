package com.yhcloud.thankyou;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.view.LoginActivity;
import com.yhcloud.thankyou.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private LogicService mService;
    private UserInfo mUserInfo;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mActivity = this;
        //隐藏系统状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //自动升级判断

        mUserInfo = new UserInfo();
        Intent intent = new Intent(this, LogicService.class);
        this.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                SharedPreferences preferences = mActivity.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
                boolean logined = preferences.getBoolean(Constant.USER_LOGINED, false);
                if (logined) {
                    String username = preferences.getString(Constant.USER_NAME, "");
                    String password = preferences.getString(Constant.USER_PWD, "");
                    if (null != username && !"".equals(username) && null != password && !"".equals(password)) {
                        mUserInfo.setUsername(username);
                        mUserInfo.setPassword(password);
                        mService.login(username, password, new ICallListener<String>() {
                            @Override
                            public void callSuccess(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if (!jsonObject.getBoolean("errorFlag")) {
                                        String key = jsonObject.getString("key");
                                        if (null != key && !"".equals(key)) {
                                            mUserInfo.setKey(key);
                                        }
                                        String jsonUserInfo = jsonObject.getString("userinfo");
                                        String jsonClassInfos = jsonObject.getString("classlist");
                                        if (null != jsonUserInfo && !"".equals(jsonUserInfo) && null != jsonClassInfos && !"".equals(jsonClassInfos)) {
                                            Gson gson = new Gson();
                                            UserInfoBean userInfoBean = gson.fromJson(jsonUserInfo, UserInfoBean.class);
                                            if (null != userInfoBean) {
                                                mUserInfo.setUserInfoBean(userInfoBean);
                                            }
                                            ArrayList<ClassInfoBean> classInfoBeen = gson.fromJson(jsonClassInfos, new TypeToken<ArrayList<ClassInfoBean>>(){}.getType());
                                            if (null != classInfoBeen) {
                                                mUserInfo.setClassInfoBeen(classInfoBeen);
                                            }
                                            mService.saveUserInfo(mUserInfo);
                                            mService.setUserInfo(mUserInfo);
                                            EMClient.getInstance().login(userInfoBean.getHXUserName(), userInfoBean.getHXPwd(), new EMCallBack() {
                                                @Override
                                                public void onSuccess() {
                                                    Tools.print(TAG, "环信登录成功!");
                                                    mService.setCanMessage(true);
                                                }

                                                @Override
                                                public void onError(int i, String s) {
                                                    Tools.print(TAG, MessageFormat.format("环信登录失败... 错误码:{0}, 错误信息:{1}", i, s));
                                                    mService.setCanMessage(false);
                                                }

                                                @Override
                                                public void onProgress(int i, String s) {

                                                }
                                            });
                                            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("ClassInfos", classInfoBeen);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    goLoginActivity();
                                }
                            }

                            @Override
                            public void callFailed() {
                                MyToast.showToast(LoadingActivity.this, R.string.error_connection);
                                goLoginActivity();
                            }
                        });
                    } else {
                        MyToast.showToast(LoadingActivity.this, R.string.error_connection);
                        goLoginActivity();
                    }
                } else {
                    MyToast.showToast(LoadingActivity.this, R.string.error_connection);
                    goLoginActivity();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    //延迟3秒进入登录界面
    private void goLoginActivity() {
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
}
