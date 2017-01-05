package com.yhcloud.thankyou.module.account.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.module.account.bean.AccountRecordingBean;
import com.yhcloud.thankyou.module.account.view.IRecordingView;
import com.yhcloud.thankyou.service.LogicService;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRecordingManage {

    private String TAG = getClass().getSimpleName();

    private IRecordingView mIRecordingView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private String userId, userFlag;
    private int pageCount, pageNow = 1;
    private ArrayList<AccountRecordingBean> mBeen;

    public AccountRecordingManage(IRecordingView iRecordingView) {
        this.mIRecordingView = iRecordingView;
        this.mActivity = (Activity) iRecordingView;
        mIRecordingView.showLoading();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mUserInfo = mService.getUserInfo();
                mBeen = new ArrayList<>();
                mIRecordingView.initView();
                mIRecordingView.initEvent();
                mIRecordingView.setTitle("账户往来");
                getRecordingData();
                mIRecordingView.hiddenLoading();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIRecordingView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getRecordingData() {
//        if (pageNow <= pageCount || 0 == pageCount) {
//            mService.getUserRecordingList(userId, pageNow, new ICallListener<String>() {
//                @Override
//                public void callSuccess(String s) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//                        if (!jsonObject.getBoolean("errorFlag")) {
//                            String jsonResult = jsonObject.getString("buyRecord");
//                            pageCount = jsonObject.getInt("pageCount");
//                            if (null != jsonResult && !"".equals(jsonResult)) {
//                                Gson gson = new Gson();
//                                ArrayList<AccountRecordingBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<AccountRecordingBean>>(){}.getType());
//                                mBeen.addAll(list);
//                                mIRecordingView.showList(mBeen);
//                                pageNow += 1;
//                                mIRecordingView.defaultText(false);
//                                return;
//                            }
//                        } else {
//                            String errorMsg = jsonObject.getString("errorMsg");
//                            if (null != errorMsg && !"".equals(errorMsg)) {
//                                mIRecordingView.showMsg(errorMsg);
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void callFailure() {
//                    mIRecordingView.showMsg(R.string.error_connection);
//                }
//            });
//        }
    }
}
