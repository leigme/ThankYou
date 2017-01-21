package com.yhcloud.thankyou.module.homework.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.homework.bean.StudentHomeworkBean;
import com.yhcloud.thankyou.module.homework.bean.TeacherHomeworkBean;
import com.yhcloud.thankyou.module.homework.view.HomeworkInfoActivity;
import com.yhcloud.thankyou.module.homework.view.IHomeworkActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */

public class HomeworkManage {

    private String TAG = getClass().getSimpleName();

    private IHomeworkActivityView mIHomeworkView;
    private Activity mActivity;
    private LogicService mService;
    private int roleId, pageNum = 1;
    private ArrayList<TeacherHomeworkBean> mTeacherHomeworkBeen;
    private ArrayList<StudentHomeworkBean> mStudentHomeworkBeen;

    public HomeworkManage(IHomeworkActivityView iHomeworkView) {
        this.mIHomeworkView = iHomeworkView;
        this.mActivity = (Activity) mIHomeworkView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                roleId = mService.getUserInfo().getUserInfoBean().getUserRoleId();
                init(roleId);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void init(int roleId) {
        mIHomeworkView.initView();
        mIHomeworkView.initEvent();
        mIHomeworkView.setTitle("课后作业");
        switch (roleId) {
            case 1004:
                mTeacherHomeworkBeen = new ArrayList<>();
                mIHomeworkView.showRight();
                getTeacherHomeworkList();
                break;
            case 1010:
                mTeacherHomeworkBeen = new ArrayList<>();
                mIHomeworkView.showRight();
                getTeacherHomeworkList();
                break;
            case 1011:
                mStudentHomeworkBeen = new ArrayList<>();
                getStudentHomeworkList();
                break;
            case 1012:
                mStudentHomeworkBeen = new ArrayList<>();
                getStudentHomeworkList();
                break;
        }
    }

    public void getTeacherHomeworkList() {
        mIHomeworkView.showLoading(R.string.loading_data);
        mService.getTeacherHomeworkList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("Homework");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<TeacherHomeworkBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<TeacherHomeworkBean>>(){}.getType());
                            mTeacherHomeworkBeen.addAll(list);
                            mIHomeworkView.showTeacherHomeworkList(mTeacherHomeworkBeen);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIHomeworkView.showToastMsg(msg);
                        } else {
                            mIHomeworkView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mIHomeworkView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIHomeworkView.hiddenLoading();
                mIHomeworkView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void goAddTeacherHomework() {
        Tools.print(TAG, "去添加作业页面");
    }

    public void getStudentHomeworkList() {
        mIHomeworkView.showLoading(R.string.loading_data);
        mService.getStudentHomeworkList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("Homework");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<StudentHomeworkBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<StudentHomeworkBean>>(){}.getType());
                            mStudentHomeworkBeen.addAll(list);
                            mIHomeworkView.showStudentHomeworkList(mStudentHomeworkBeen);
                        }
                    } else {
                        String msg = jsonObject.getString("errorFlag");
                        if (null != msg && !"".equals(msg)) {
                            mIHomeworkView.showToastMsg(msg);
                        } else {
                            mIHomeworkView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIHomeworkView.hiddenLoading();
                }
                mIHomeworkView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mIHomeworkView.hiddenLoading();
                mIHomeworkView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void goHomeworkInfo(int position) {
        Intent intent;
        Bundle bundle;
        switch (roleId) {
            case 1004:
                if ("2".equals(mTeacherHomeworkBeen.get(position).getStatus())) {
                    intent = new Intent(mActivity, HomeworkInfoActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("teacherHomeworkBean", mTeacherHomeworkBeen.get(position));
                    intent.putExtras(bundle);
                    mActivity.startActivityForResult(intent, 101);
                } else {

                }
                break;
            case 1010:
                if ("2".equals(mTeacherHomeworkBeen.get(position).getStatus())) {
                    intent = new Intent(mActivity, HomeworkInfoActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("teacherHomeworkBean", mTeacherHomeworkBeen.get(position));
                    intent.putExtras(bundle);
                    mActivity.startActivityForResult(intent, 101);
                } else {

                }
                break;
            case 1011:
                intent = new Intent(mActivity, HomeworkInfoActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("studentHomeworkBean", mStudentHomeworkBeen.get(position));
                intent.putExtras(bundle);
                mActivity.startActivityForResult(intent, 101);
                break;
            case 1012:
                intent = new Intent(mActivity, HomeworkInfoActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("studentHomeworkBean", mStudentHomeworkBeen.get(position));
                intent.putExtras(bundle);
                mActivity.startActivityForResult(intent, 101);
                break;
        }
    }

    public void refreshData() {

    }

    public void getMoreData() {

    }
}
