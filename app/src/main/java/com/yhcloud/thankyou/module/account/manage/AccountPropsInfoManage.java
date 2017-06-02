package com.yhcloud.thankyou.module.account.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.module.account.view.PropsInfoView;
import com.yhcloud.thankyou.module.account.view.IntegralActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountPropsInfoManage {

    private String TAG = getClass().getSimpleName();

    private PropsInfoView mIPropsInfoView;
    private Activity mActivity;
    private LogicService mService;
    private int coin, sumCoin;
    private AccountPropBean mAccountPropBean;

    public AccountPropsInfoManage(PropsInfoView propsInfoView) {
        this.mIPropsInfoView = propsInfoView;
        this.mActivity = (Activity) propsInfoView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                mService = ((LogicService.MyBinder)service).getService();
                mIPropsInfoView.initView();
                mIPropsInfoView.initEvent();
                mIPropsInfoView.setTitle("道具详情");
                Intent propsInfoIntent = mActivity.getIntent();
                mAccountPropBean = (AccountPropBean) propsInfoIntent.getSerializableExtra("AccountPropBean");
                mAccountPropBean.setPropImg(Constant.SERVICEADDRESS + mAccountPropBean.getPropImg());
                coin = propsInfoIntent.getIntExtra("coin", 0);
                sumCoin = Integer.parseInt(mAccountPropBean.getPropPrice());
                mIPropsInfoView.initData(mAccountPropBean);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void showDialog() {
        if (coin < sumCoin) {
            mIPropsInfoView.showDialog(sumCoin, "您的积分余额不足，请前往积分兑换页面兑换", false);
        } else {
            mIPropsInfoView.showDialog(sumCoin, "", true);
        }
    }

    public void addNum() {
        int i = mIPropsInfoView.getBuynum();
        i += 1;
        mIPropsInfoView.setBuynum(String.valueOf(i));
        int coin = Integer.parseInt(mAccountPropBean.getPropPrice());
        int sum = coin * i;
        sumCoin = sum;
        mIPropsInfoView.setSumnum(String.valueOf(sumCoin));
    }

    public void subNum() {
        int i = mIPropsInfoView.getBuynum();
        if (0 < i) {
            i -= 1;
            mIPropsInfoView.setBuynum(String.valueOf(i));
            int coin = Integer.parseInt(mAccountPropBean.getPropPrice());
            int sum = coin * i;
            sumCoin = sum;
            mIPropsInfoView.setSumnum(String.valueOf(sumCoin));
        }
    }

    public void buyProps() {
        mIPropsInfoView.showLoading(R.string.exchangeing);
        int i = mIPropsInfoView.getBuynum();
        mService.buyProps(mAccountPropBean.getPropId(), String.valueOf(i), new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean result = jsonObject.getBoolean("errorFlag");
                    if (!result) {
                        mIPropsInfoView.showResult("购买成功", "欢迎您再次购买");
                        return;
                    }
                    String errorMsg = jsonObject.getString("errorMsg");
                    mIPropsInfoView.showResult(errorMsg, "请稍候再试");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIPropsInfoView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIPropsInfoView.showResult("购买失败", "网络故障,请稍后再试");
                mIPropsInfoView.hiddenLoading();
            }
        });
    }

    public void goIntegral() {
        Intent intent = new Intent(mActivity, IntegralActivity.class);
        mActivity.startActivity(intent);
    }
}
