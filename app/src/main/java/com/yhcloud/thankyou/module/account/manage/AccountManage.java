package com.yhcloud.thankyou.module.account.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.module.account.bean.AccountFunctionBean;
import com.yhcloud.thankyou.module.account.view.IAccountView;
import com.yhcloud.thankyou.service.LogicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AccountManage {

    private IAccountView mIAccountView;
    private Activity mActivity;
    private LogicService mService;

    private List<String> imageUrls;
    private ArrayList<AccountFunctionBean> mBeen;

    public AccountManage(IAccountView accountView) {
        this.mIAccountView = accountView;
        mActivity = (Activity) mIAccountView;
        mIAccountView.showLoading();
        imageUrls = new ArrayList<>();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIAccountView.initView();
                mIAccountView.initEvent();
                mIAccountView.setTitle("我的账户");
                getBannerImageUrls();
                showFunction();
                mIAccountView.hiddenLoading();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIAccountView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getBannerImageUrls() {
//        mService.getBannerImageUrlsForService("16", "2", "-1", new ICallListener<String>() {
//            @Override
//            public void callSuccess(String s) {
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (!jsonObject.getBoolean("errorFlag")) {
//                        String jsonList = jsonObject.getString("dataList");
//                        Type jsonType = new TypeToken<List<ItemSpread>>() {}.getType();
//                        Gson gson = new Gson();
//                        List<ItemSpread> list = gson.fromJson(jsonList, jsonType);
//                        for (ItemSpread itemSpread: list) {
//                            imageUrls.add(ServiceAPI.SERVICEADDRESS + itemSpread.getSummaryPicLink());
//                        }
//                        mIAccountView.showBanner(imageUrls);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void callFailure() {
//
//            }
//        });
    }

    public void showFunction() {
        mBeen = new ArrayList<>();
        AccountFunctionBean bean1 = new AccountFunctionBean("优点充值", R.mipmap.bg_recharge);
        AccountFunctionBean bean2 = new AccountFunctionBean("积分兑换", R.mipmap.bg_integral);
        AccountFunctionBean bean3 = new AccountFunctionBean("道具兑换", R.mipmap.bg_props);
        AccountFunctionBean bean4 = new AccountFunctionBean("我的道具", R.mipmap.icon_myprops);
        AccountFunctionBean bean5 = new AccountFunctionBean("账户往来", R.mipmap.bg_integral);
        mBeen.add(bean1);
        mBeen.add(bean2);
        mBeen.add(bean3);
        mBeen.add(bean4);
        mBeen.add(bean5);
        mIAccountView.showFunction(mBeen);
    }
}
