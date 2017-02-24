package com.yhcloud.thankyou.module.detailinfo.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.EaseConstant;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.RelativeInfoBean;
import com.yhcloud.thankyou.bean.UserRoleBean;
import com.yhcloud.thankyou.minterface.ICallListener;
import com.yhcloud.thankyou.module.chat.view.EaseChatActivity;
import com.yhcloud.thankyou.module.detailinfo.bean.DetailPeopleInfoBean;
import com.yhcloud.thankyou.module.detailinfo.view.IDetailPeopleActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class DetailPeopleManage {

    private String TAG = getClass().getSimpleName();

    private IDetailPeopleActivityView mIDetailPeopleView;
    private Activity mActivity;
    private LogicService mService;
    private String uId, userRealName;
    private ArrayList<RelativeInfoBean> relativeInfoBeen;
    private int roleId;
    private boolean edited;

    public DetailPeopleManage(IDetailPeopleActivityView iDetailPeopleView) {
        this.mIDetailPeopleView = iDetailPeopleView;
        this.mActivity = (Activity) mIDetailPeopleView;
        mIDetailPeopleView.showLoading(R.string.loading_data);
        if (null != mActivity.getIntent()) {
            uId = mActivity.getIntent().getStringExtra("UID");
        }
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService =((LogicService.MyBinder)service).getService();
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
        mService.getDetailInfo(uId, new ICallListener<String>() {
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
                            userRealName = detailPeopleInfoBean.getRealName();
                            switch (detailPeopleInfoBean.getRoleName()) {
                                case "老师":
                                    roleId = 1010;
                                    break;
                                case "学生":
                                    roleId = 1011;
                                    if (null != jsonRelativeInfo && !"".equals(jsonRelativeInfo)) {
                                        relativeInfoBeen = gson.fromJson(jsonRelativeInfo, new TypeToken<ArrayList<RelativeInfoBean>>(){}.getType());
                                        if (null != relativeInfoBeen && 0 != relativeInfoBeen.size()) {
                                            mIDetailPeopleView.showList(relativeInfoBeen);
                                        }
                                    }
                                    break;
                                case "家长":
                                    roleId = 1012;
                                    if (null != jsonRelativeInfo && !"".equals(jsonRelativeInfo)) {
                                        relativeInfoBeen = gson.fromJson(jsonRelativeInfo, new TypeToken<ArrayList<RelativeInfoBean>>(){}.getType());
                                        if (null != relativeInfoBeen && 0 != relativeInfoBeen.size()) {
                                            mIDetailPeopleView.showList(relativeInfoBeen);
                                        }
                                    }
                                    break;
                            }
                            mIDetailPeopleView.initView(roleId);
                            mIDetailPeopleView.setHeadImage(detailPeopleInfoBean.getHeadImage());
                            mIDetailPeopleView.setName(userRealName);
                            ArrayList<UserRoleBean> list = detailPeopleInfoBean.getUserRole();
                            if (null != list && 0 != list.size()) {
                                mIDetailPeopleView.setOffice(list.get(0).getRoleName());
                            } else {
                                mIDetailPeopleView.setOffice("无");
                            }
                            mIDetailPeopleView.setReMark(detailPeopleInfoBean.getFriendRemark());
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIDetailPeopleView.showToastMsg(msg);
                        }
                        mActivity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIDetailPeopleView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIDetailPeopleView.hiddenLoading();
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

    public void goStudentChat() {
        sendMessage(uId, userRealName);
    }

    public void goParentChat(int position) {
        RelativeInfoBean relativeInfoBean = relativeInfoBeen.get(position);
        sendMessage(relativeInfoBean.getRelativesHxName(), relativeInfoBean.getRelativesRealName());
    }

    public void sendMessage(String userId, String userName) {
        Intent intent = new Intent(mActivity, EaseChatActivity.class);
        intent.putExtra(Constant.CHATTYPE, Constant.SINGLE);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
        intent.putExtra(Constant.USER_NAME, userName);
        mActivity.startActivity(intent);
    }

    public void sentProps() {}
}
