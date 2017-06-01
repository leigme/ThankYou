package com.yhcloud.thankyou.module.account.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.account.bean.AccountRecordingBean;
import com.yhcloud.thankyou.module.account.view.RecordingView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRecordingManage {

    private String TAG = getClass().getSimpleName();

    private RecordingView mIRecordingView;
    private Activity mActivity;
    private LogicService mService;
    private int pageCount = -1, pageNow = 1;
    private ArrayList<AccountRecordingBean> mBeen;

    public AccountRecordingManage(RecordingView iRecordingView) {
        this.mIRecordingView = iRecordingView;
        this.mActivity = (Activity) iRecordingView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mBeen = new ArrayList<>();
                mIRecordingView.initView();
                mIRecordingView.initEvent();
                mIRecordingView.setTitle("账户往来");
                getRecordingData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getRecordingData() {
        if (pageNow < pageCount || -1 == pageCount) {
            mIRecordingView.showLoading(R.string.loading_data);
            Tools.print(TAG, "下拉刷新");
            mService.getUserRecordingList(pageNow, new ResponseCallBack<String>() {
                @Override
                public void callSuccess(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (!jsonObject.getBoolean("errorFlag")) {
                            String jsonResult = jsonObject.getString("buyRecord");
                            pageCount = jsonObject.getInt("pageCount");
                            if (null != jsonResult && !"".equals(jsonResult)) {
                                Gson gson = new Gson();
                                ArrayList<AccountRecordingBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<AccountRecordingBean>>(){}.getType());
                                mBeen.addAll(list);
                                mIRecordingView.showList(mBeen);
                                pageNow += 1;
                                mIRecordingView.defaultText(false);
                            }
                        } else {
                            String errorMsg = jsonObject.getString("errorMsg");
                            if (null != errorMsg && !"".equals(errorMsg)) {
                                mIRecordingView.showToastMsg(errorMsg);
                            } else {
                                mIRecordingView.showToastMsg(R.string.error_connection);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mIRecordingView.hiddenLoading();
                }

                @Override
                public void callFailure() {
                    mIRecordingView.showToastMsg(R.string.error_connection);
                    mIRecordingView.hiddenLoading();
                }
            });
        }
    }
}
