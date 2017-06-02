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
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;
import com.yhcloud.thankyou.module.account.view.AccountView;
import com.yhcloud.thankyou.module.account.view.IntegralActivity;
import com.yhcloud.thankyou.module.account.view.MyPropsActivity;
import com.yhcloud.thankyou.module.account.view.PropsActivity;
import com.yhcloud.thankyou.module.account.view.RechargeActivity;
import com.yhcloud.thankyou.module.account.view.RecordingActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountManage {

    private AccountView mIAccountView;
    private Activity mActivity;
    private LogicService mService;

    private ArrayList<String> imageUrls;
    private ArrayList<AccountFunctionBean> mBeen;
    private int refreshSpreadNum;
    private int RequestCode_AccountManage = 101;

    public AccountManage(AccountView accountView) {
        this.mIAccountView = accountView;
        mActivity = (Activity) mIAccountView;
        imageUrls = new ArrayList<>();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                mService = ((LogicService.MyBinder)service).getService();
                mIAccountView.initView();
                mIAccountView.initEvent();
                mIAccountView.setTitle("我的账户");
                showSpreadList("-1");
                showFunction();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIAccountView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void showSpreadList(String updateTime) {
        mIAccountView.showLoading(R.string.loading_data);
        mService.getBannerImages(updateTime, new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonList = jsonObject.getString("dataList");
                        Type jsonType = new TypeToken<ArrayList<SpreadBean>>() {}.getType();
                        Gson gson = new Gson();
                        ArrayList<SpreadBean> list = gson.fromJson(jsonList, jsonType);
                        for (SpreadBean itemSpread: list) {
                            imageUrls.add(Constant.SERVICEADDRESS + itemSpread.getSummaryPicLink());
                        }
                        mIAccountView.showBanner(imageUrls);
                        mIAccountView.hiddenLoading();
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mIAccountView.showToastMsg(errorMsg);
                        } else {
                            mIAccountView.showToastMsg(R.string.error_connection);
                        }
                    }
                    mIAccountView.hiddenLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIAccountView.hiddenLoading();
                }
            }

            @Override
            public void callFailure() {
                if (3 > refreshSpreadNum) {
                    showSpreadList("-1");
                    refreshSpreadNum += 1;
                } else {
                    mIAccountView.hiddenLoading();
                }
            }
        });
    }

    public void showFunction() {
        mBeen = new ArrayList<>();
        AccountFunctionBean bean1 = new AccountFunctionBean("优点充值", R.mipmap.bg_recharge);
        Intent intent1 = new Intent(mActivity, RechargeActivity.class);
        bean1.setIntent(intent1);
        AccountFunctionBean bean2 = new AccountFunctionBean("积分兑换", R.mipmap.bg_integral);
        Intent intent2 = new Intent(mActivity, IntegralActivity.class);
        bean2.setIntent(intent2);
        AccountFunctionBean bean3 = new AccountFunctionBean("道具兑换", R.mipmap.bg_props);
        Intent intent3 = new Intent(mActivity, PropsActivity.class);
        bean3.setIntent(intent3);
        AccountFunctionBean bean4 = new AccountFunctionBean("我的道具", R.mipmap.icon_myprops);
        Intent intent4 = new Intent(mActivity, MyPropsActivity.class);
        bean4.setIntent(intent4);
        AccountFunctionBean bean5 = new AccountFunctionBean("账户往来", R.mipmap.bg_integral);
        Intent intent5 = new Intent(mActivity, RecordingActivity.class);
        bean5.setIntent(intent5);
        mBeen.add(bean1);
        mBeen.add(bean2);
        mBeen.add(bean3);
        mBeen.add(bean4);
        mBeen.add(bean5);
        mIAccountView.showFunction(mBeen);
    }

    public void goFunction(int position) {
        mActivity.startActivityForResult(mBeen.get(position).getIntent(), RequestCode_AccountManage);
    }
}
