package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterfacea.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.module.classteachers.view.ClassTeacherListActivity;
import com.yhcloud.thankyou.module.detailinfo.view.DetailPeopleActivity;
import com.yhcloud.thankyou.view.IClassActivityView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ClassManage {

    private IClassActivityView mIClassView;
    private Fragment mFragment;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private ArrayList<UserInfoBean> mBeen;
    private int refreshNum;

    public ClassManage(IClassActivityView iClassView, LogicService service) {
        this.mIClassView = iClassView;
        this.mFragment = (Fragment) mIClassView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mIClassView.initView();
        mIClassView.initEvent();
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
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIClassView.showToastMsg(msg);
                        } else {
                            mIClassView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIClassView.showToastMsg(R.string.error_connection);
                }
            }

            @Override
            public void callFailed() {
                mIClassView.showToastMsg(R.string.error_connection);
                if (3 > refreshNum) {
                    getClassPeopleList("");
                    refreshNum += 1;
                }
            }
        });
    }

    public void goTeacherList() {
        Intent intent = new Intent(mActivity, ClassTeacherListActivity.class);
        mActivity.startActivity(intent);
    }

    public void goDetailInfo(int position) {
        Intent intent = new Intent(mActivity, DetailPeopleActivity.class);
        intent.putExtra("UID", mBeen.get(position).getUserId());
        mActivity.startActivity(intent);
    }


}
