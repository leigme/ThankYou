package com.yhcloud.thankyou.module.account.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterfacea.ICallListener;
import com.yhcloud.thankyou.module.account.alipay.AuthResult;
import com.yhcloud.thankyou.module.account.alipay.PayResult;
import com.yhcloud.thankyou.module.account.bean.AccountRechargeBean;
import com.yhcloud.thankyou.module.account.bean.AccountRechargePayBean;
import com.yhcloud.thankyou.module.account.view.IRechargeView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRechargeManage {

    private String TAG = getClass().getSimpleName();

    private IRechargeView mIRechargeView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private String userId, userFlag, userName, realName, payNum, productId, payId, orderInfo;
    private int uCoin;
    private ArrayList<AccountRechargeBean> mBeen;
    private ArrayList<AccountRechargePayBean> mList;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.e(TAG, "支付结果:" + resultInfo);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        mIRechargeView.showToastMsg("支付成功");
                        mIRechargeView.closeDialog();
                        getUserCurrency();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        mIRechargeView.showToastMsg("支付失败");
                    }
                    mIRechargeView.hiddenLoading();
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        mIRechargeView.showToastMsg("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        mIRechargeView.showToastMsg("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
            return false;
        }
    });

    public AccountRechargeManage(IRechargeView rechargeView) {
        this.mIRechargeView = rechargeView;
        this.mActivity = (Activity) rechargeView;
        mIRechargeView.showLoading(R.string.loading_data);

        mList = new ArrayList<>();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mUserInfo = mService.getUserInfo();
                mIRechargeView.initView();
                mIRechargeView.initEvent();
                mIRechargeView.setTitle("优点充值");
                mIRechargeView.setHeadimg(Constant.SERVICEADDRESS + mUserInfo.getUserInfoBean().getHeadImageURL());
                mIRechargeView.setRealname(mUserInfo.getUserInfoBean().getRealName());
                mIRechargeView.setUsername("账号: " + mUserInfo.getUserInfoBean().getUserId());
                getPayList();
                getUserCurrency();
                getRechargeData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIRechargeView.hiddenLoading();

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getRechargeData() {
        mIRechargeView.showLoading(R.string.loading_data);
        mBeen = new ArrayList<>();
        mService.getRechargeList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("RulesList");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<AccountRechargeBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<AccountRechargeBean>>(){}.getType());
                            mBeen.addAll(list);
                            mIRechargeView.showRechargeList(mBeen);
                        }
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mIRechargeView.showToastMsg(errorMsg);
                        } else {
                            mIRechargeView.showToastMsg(R.string.error_connection);
                        }
                    }
                    mIRechargeView.hiddenLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIRechargeView.hiddenLoading();
                }
            }

            @Override
            public void callFailed() {
                mIRechargeView.showToastMsg(R.string.error_connection);
                mIRechargeView.hiddenLoading();
            }
        });
    }

    public void getUserCurrency() {
        mService.getUserCurrency(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        uCoin = jsonObject.getInt("uCoin");
                        mIRechargeView.setCoin(String.valueOf(uCoin));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {

            }
        });
    }

    public void getPayList() {
        mService.getPayList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("pay_list");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<AccountRechargePayBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<AccountRechargePayBean>>(){}.getType());
                            if (null != list && 0 != list.size()) {
                                list.get(0).setSelected(true);
                                for (AccountRechargePayBean bean: list) {
                                    if (bean.isPay_statu()) {
                                        switch (bean.getPay_id()) {
                                            case "0":
                                                bean.setIconId(R.mipmap.icon_zfb);
                                                break;
                                            case "1":
                                                break;
                                            case "2":
                                                break;
                                            case "3":
                                                break;
                                        }
                                        mList.add(bean);
                                    }
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {

            }

        });
    }

    public void showPayList() {
        for (AccountRechargeBean arb: mBeen) {
            if (arb.isSelected()) {
                double i = Double.parseDouble(arb.getMoney()) * Double.parseDouble(arb.getDiscount());
                payNum = String.valueOf(i);
                productId = arb.getId();
            }
        }
        mIRechargeView.showDialog(mList, payNum);
    }

    public void payMoney() {
        mIRechargeView.showLoading(R.string.exchangeing);
        for (AccountRechargePayBean payBean: mList) {
            if (payBean.isSelected()) {
                payId = payBean.getPay_id();
            }
        }
        mService.getOrderNum(productId, payId, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String orgdata = jsonObject.getString("orgdata");
                    String sign = jsonObject.getString("signedStr");
                    orderInfo = orgdata + "&sign=" + sign;
                    Log.e(TAG, "订单信息:" + orderInfo);
                    alipay(orderInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIRechargeView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIRechargeView.hiddenLoading();
            }

        });
    }

    /**
     * 支付宝
     */
    private void alipay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
