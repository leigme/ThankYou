package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IClassView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ClassManage {

    private IClassView mIClassView;
    private Fragment mFragment;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private ArrayList<UserInfoBean> mBeen;

    public ClassManage(IClassView iClassView, LogicService service) {
        this.mIClassView = iClassView;
        this.mFragment = (Fragment) mIClassView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mIClassView.initView();
        mUserInfo = mService.getUserInfo();
        getClassPeopleList("");
    }

    public void getClassPeopleList(String classId) {
        mBeen = new ArrayList<>();
        if (null != classId && !"".equals(classId)) {

        } else {
            classId = mUserInfo.getUserInfoBean().getDefaultClassId();
        }
        mService.getClassPeopleList(classId, "-1", new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("userinfo");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<UserInfoBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<UserInfoBean>>(){}.getType());
                            switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
//                                    case 1004:
//                                        break;
//                                    case 1010:
//                                        for (UserInfoBean userInfoBean: list) {
//                                            if (1011 == userInfoBean.getUserRoleId()) {
//                                                mBeen.add(userInfoBean);
//                                            }
//                                        }
//                                        break;
//                                    case 1011:
//                                        for (UserInfoBean userInfoBean: list) {
//                                            if (1011 == userInfoBean.getUserRoleId()) {
//                                                mBeen.add(userInfoBean);
//                                            }
//                                        }
//                                        break;
                                case 1012:
                                    for (UserInfoBean userInfoBean: list) {
                                        if (1012 == userInfoBean.getUserRoleId()) {
                                            mBeen.add(userInfoBean);
                                        }
                                    }
                                    break;
                                default:
                                    for (UserInfoBean userInfoBean: list) {
                                        if (1011 == userInfoBean.getUserRoleId()) {
                                            mBeen.add(userInfoBean);
                                        }
                                    }
                                    break;
                            }
                            mIClassView.showList(mBeen);
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

}
