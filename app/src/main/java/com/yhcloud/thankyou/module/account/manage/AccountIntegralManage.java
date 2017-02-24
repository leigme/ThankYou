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
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterfacea.ICallListener;
import com.yhcloud.thankyou.module.account.bean.AccountIntegralBean;
import com.yhcloud.thankyou.module.account.view.IIntegralView;
import com.yhcloud.thankyou.module.account.view.RechargeActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountIntegralManage {

    private String TAG = getClass().getSimpleName();

    private IIntegralView mIIntegralView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private int uCoin, coin;
    private ArrayList<AccountIntegralBean> mBeen;

    public AccountIntegralManage(IIntegralView integralView) {
        this.mIIntegralView = integralView;
        this.mActivity = (Activity) integralView;
        mIIntegralView.showLoading(R.string.loading_data);
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mUserInfo = mService.getUserInfo();
                mIIntegralView.initView();
                mIIntegralView.initEvent();
                mIIntegralView.setTitle("积分兑换");
                mIIntegralView.setHeadimg(Constant.SERVICEADDRESS + mUserInfo.getUserInfoBean().getHeadImageURL());
                mIIntegralView.setRealname(mUserInfo.getUserInfoBean().getRealName());
                mIIntegralView.setUsername("账号: " + mUserInfo.getUserInfoBean().getUserId());
                getUserCurrency();
                getIntegralListData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIIntegralView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getUserCurrency() {
        mIIntegralView.showLoading(R.string.loading_data);
        mService.getUserCurrency(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        coin = jsonObject.getInt("coin");
                        mIIntegralView.setCoin(String.valueOf(coin));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIIntegralView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIIntegralView.hiddenLoading();
            }


        });
    }

    public void getIntegralListData() {
        mIIntegralView.showLoading(R.string.loading_data);
        mBeen = new ArrayList<>();
        mService.getIntegralExchangeList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("RulesList");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<AccountIntegralBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<AccountIntegralBean>>(){}.getType());
                            mBeen.addAll(list);
                            mIIntegralView.showList(mBeen);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIIntegralView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIIntegralView.showToastMsg(R.string.error_connection);
                mIIntegralView.hiddenLoading();
            }
        });
    }

    public void send() {
        mIIntegralView.showLoading(R.string.loading_data);
        mService.getUserCurrency(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        uCoin = jsonObject.getInt("uCoin");
                        if (0 < uCoin) {
                            for (AccountIntegralBean bean: mBeen) {
                                if (bean.isSelected()) {
                                    if (Integer.parseInt(bean.getMoney()) < uCoin) {
                                        mIIntegralView.showDialog(true, bean.getMoney(), bean.getCoin());
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIIntegralView.hiddenLoading();
                mIIntegralView.showDialog(false, "", "");
            }

            @Override
            public void callFailed() {
                mIIntegralView.hiddenLoading();
                mIIntegralView.showDialog(false, "", "");
            }
        });
    }

    public void getUserCoin() {
        mIIntegralView.showLoading(R.string.exchangeing);
        for (AccountIntegralBean bean: mBeen) {
            if (bean.isSelected()) {
                mService.getUserCoin(bean.getMoney(), bean.getCoin(), new ICallListener<String>() {
                    @Override
                    public void callSuccess(String s) {
                        mIIntegralView.showDialogMsg();
                        getUserCurrency();
                        mIIntegralView.hiddenLoading();
                    }

                    @Override
                    public void callFailed() {
                        mIIntegralView.hiddenLoading();
                    }
                });
            }
        }
    }

    public void goRecharge() {
        Intent intent = new Intent(mActivity, RechargeActivity.class);
        mActivity.startActivity(intent);
    }
}
