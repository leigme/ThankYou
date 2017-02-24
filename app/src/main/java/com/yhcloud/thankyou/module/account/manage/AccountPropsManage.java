package com.yhcloud.thankyou.module.account.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterfacea.ICallListener;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.module.account.view.IPropsView;
import com.yhcloud.thankyou.module.account.view.PropsInfoActivity;
import com.yhcloud.thankyou.service.LogicService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountPropsManage {

    private String TAG = getClass().getSimpleName();

    private IPropsView mIPropsView;
    private LogicService mService;
    private Activity mActivity;
    private int coin;
    private ArrayList<AccountPropBean> mBeen;

    public AccountPropsManage(IPropsView propsView) {
        this.mIPropsView = propsView;
        this.mActivity = (Activity)propsView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mBeen = new ArrayList<>();
                mIPropsView.initView();
                mIPropsView.initEvent();
                mIPropsView.setTitle("道具商城");
                getUserCurrency();
                getPropData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIPropsView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getPropData() {
        mIPropsView.showLoading(R.string.loading_data);
        mService.getPropsStoreList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("propList");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<AccountPropBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<AccountPropBean>>(){}.getType());
                            mBeen.addAll(list);
                            mIPropsView.showPropList(mBeen);
                        }
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mIPropsView.showToastMsg(errorMsg);
                        } else {
                            mIPropsView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIPropsView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIPropsView.showToastMsg(R.string.error_connection);
                mIPropsView.hiddenLoading();
            }
        });
    }

    public void goPropInfo(int position) {
        AccountPropBean accountPropBean = mBeen.get(position);
        Intent intent = new Intent(mActivity, PropsInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("AccountPropBean", accountPropBean);
        intent.putExtra("coin", coin);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 101);
    }

    public void getUserCurrency() {
        mService.getUserCurrency(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        coin = jsonObject.getInt("coin");
                        mIPropsView.setCoin(String.valueOf(coin));
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mIPropsView.showToastMsg(errorMsg);
                        } else {
                            mIPropsView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {
                mIPropsView.showToastMsg(R.string.error_connection);
            }
        });
    }
}
