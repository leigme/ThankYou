package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.DetailPeopleInfoBean;
import com.yhcloud.thankyou.bean.RelativeInfoBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserRoleBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IDetailPeopleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class DetailPeopleManage {

    private String TAG = getClass().getSimpleName();

    private IDetailPeopleView mIDetailPeopleView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private String uId;
    private int roleId;
    private boolean edited;

    public DetailPeopleManage(IDetailPeopleView iDetailPeopleView) {
        this.mIDetailPeopleView = iDetailPeopleView;
        this.mActivity = (Activity) mIDetailPeopleView;
        if (null != mActivity.getIntent()) {
            uId = mActivity.getIntent().getStringExtra("UID");
        }
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService =((LogicService.MyBinder)service).getService();
                mUserInfo = mService.getUserInfo();
                mIDetailPeopleView.initView();
                mIDetailPeopleView.initEvent();
                getDetailData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getDetailData() {
        mService.getDetailInfo(mUserInfo.getUserInfoBean().getUserId(), uId, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonData = jsonObject.getString("userInfo");
                        String jsonRelativeInfo = jsonObject.getString("relativeinfo");
                        if (null != jsonData && !"".equals(jsonData)) {
                            Gson gson = new Gson();
                            DetailPeopleInfoBean detailPeopleInfoBean = gson.fromJson(jsonData, DetailPeopleInfoBean.class);
                            switch (detailPeopleInfoBean.getRoleName()) {
                                case "老师":
                                    roleId = 1010;
                                    break;
                                case "学生":
                                    roleId = 1011;
                                    if (null != jsonRelativeInfo && !"".equals(jsonRelativeInfo)) {
                                        ArrayList<RelativeInfoBean> list = gson.fromJson(jsonRelativeInfo, new TypeToken<ArrayList<RelativeInfoBean>>(){}.getType());
                                        if (null != list && 0 != list.size()) {
                                            mIDetailPeopleView.showList(list);
                                        }
                                    }
                                    break;
                                case "家长":
                                    roleId = 1012;
                                    if (null != jsonRelativeInfo && !"".equals(jsonRelativeInfo)) {
                                        ArrayList<RelativeInfoBean> list = gson.fromJson(jsonRelativeInfo, new TypeToken<ArrayList<RelativeInfoBean>>(){}.getType());
                                        if (null != list && 0 != list.size()) {
                                            mIDetailPeopleView.showList(list);
                                        }
                                    }
                                    break;
                            }
                            mIDetailPeopleView.initView(roleId);
                            mIDetailPeopleView.setHeadImage(detailPeopleInfoBean.getHeadImage());
                            mIDetailPeopleView.setName(detailPeopleInfoBean.getRealName());
                            ArrayList<UserRoleBean> list = detailPeopleInfoBean.getUserRole();
                            if (null != list && 0 != list.size()) {
                                mIDetailPeopleView.setOffice(list.get(0).getRoleName());
                            } else {
                                mIDetailPeopleView.setOffice("无");
                            }
                            mIDetailPeopleView.setReMark(detailPeopleInfoBean.getFriendRemark());
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

    public void editReMark() {
        if (!edited) {
            edited = true;
        } else {
            edited = false;
        }
        mIDetailPeopleView.setEdit(edited);
    }

    public void goBigImage() {

    }

    public void sendMessage() {}

    public void sentProps() {}
}
