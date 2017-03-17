package com.yhcloud.thankyou.module.aboutus.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.module.aboutus.bean.AboutUsBean;
import com.yhcloud.thankyou.module.aboutus.view.IAboutUsActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

/**
 * Created by Administrator on 2016/12/26.
 */

public class AboutUsManage {
    private IAboutUsActivityView mIAboutUsView;
    private Activity mActivity;
    private LogicService mService;

    public AboutUsManage(IAboutUsActivityView iAboutUsView) {
        this.mIAboutUsView = iAboutUsView;
        this.mActivity = (Activity) mIAboutUsView;

        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIAboutUsView.initView();
                mIAboutUsView.initEvent();
                setTitle();
                getAboutUsInfo();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void setTitle() {
        String versionName = Tools.getAppVersionName(mActivity.getApplicationContext());
        mIAboutUsView.setTitle(MessageFormat.format("三课优 V{0}", versionName));
    }

    public void getAboutUsInfo() {
        mIAboutUsView.showLoading(R.string.loading_data);
        mService.getAboutUsInfo(new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonData = jsonObject.getString("data");
                        if (null != jsonData) {
                            Gson gson = new Gson();
                            AboutUsBean aub = gson.fromJson(jsonData, AboutUsBean.class);
                            mIAboutUsView.setInfo("        " + aub.getContent());
                            mIAboutUsView.setImageView(aub.getBarcodeURL());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIAboutUsView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIAboutUsView.hiddenLoading();
            }
        });
    }
}
