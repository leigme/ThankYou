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
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.module.account.view.IMyPropsView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountMyPropsManage {

    private String TAG = getClass().getSimpleName();

    private IMyPropsView mIMyPropsView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private String userId, userFlag, recvUserId, propId;
    private ArrayList<AccountPropBean> mBeen;
    private boolean canGive;
    private int give, sum;

    public AccountMyPropsManage(IMyPropsView myPropsView) {
        this.mIMyPropsView = myPropsView;
        this.mActivity = (Activity) myPropsView;
        mIMyPropsView.showLoading(R.string.loading_data);

        mBeen = new ArrayList<>();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mUserInfo = mService.getUserInfo();
                mIMyPropsView.initView();
                Intent goIntent = mActivity.getIntent();
                if (null != goIntent) {
                    give = goIntent.getIntExtra("Give", -1);
                    if (0 == give) {
                        recvUserId = goIntent.getStringExtra("RecvUserId");
                        canGive = true;
//                        mIMyPropsView.setRightText("赠送");
                        mIMyPropsView.initRight(canGive);
                    }
                }
                mIMyPropsView.setTitle("我的道具");
                mIMyPropsView.initEvent();
                getPropsDataForService();
                mIMyPropsView.hiddenLoading();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIMyPropsView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getPropsDataForService() {
        mIMyPropsView.showLoading(R.string.loading_data);
        mService.getUserPropsList(new ICallBackListener<String>() {
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
                            mIMyPropsView.initViewStub(canGive);
                            mIMyPropsView.showList(mBeen, canGive);
                        }
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mIMyPropsView.initViewStub();
                            mIMyPropsView.showDefault(true);
                            mIMyPropsView.showToastMsg(errorMsg);
                        } else {
                            mIMyPropsView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIMyPropsView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIMyPropsView.showToastMsg(R.string.error_connection);
                mIMyPropsView.hiddenLoading();
            }
        });
    }

    public void addNum() {
        int i = mIMyPropsView.getNum();
        if (i < sum) {
            i += 1;
        }
        mIMyPropsView.showNum(i);
    }

    public void subNum() {
        int i = mIMyPropsView.getNum();
        if (i > 1) {
            i -= 1;
        }
        mIMyPropsView.showNum(i);
    }

    public void setGiveNum(int position) {
        mIMyPropsView.showNum(1);
        propId = mBeen.get(position).getPropId();
        sum = Integer.parseInt(mBeen.get(position).getPropNum());
    }

    public void givePropsToPeople() {
        if (null != propId && !"".equals(propId)) {
            if (0 < mIMyPropsView.getNum()) {
                Tools.print(TAG, "开始赠送~~~");
                mService.givePropsToPeople(recvUserId, propId, mIMyPropsView.getNum(), new ICallBackListener<String>() {
                    @Override
                    public void callSuccess(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (!jsonObject.getBoolean("errorFlag")) {
                                Tools.print(TAG, "赠送成功~~");
                                mIMyPropsView.showNum(0);
                                mBeen = new ArrayList<>();
                                getPropsDataForService();
                            } else {
                                Tools.print(TAG, "赠送失败~~");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void callFailure() {
                        Tools.print(TAG, "赠送失败~~");
                    }
                });
            } else {
                mIMyPropsView.showToastMsg("请选择赠送数量");
            }
        } else {
            mIMyPropsView.showToastMsg("请选择道具");
        }
    }
}
