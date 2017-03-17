package com.yhcloud.thankyou.module.homework.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.module.homework.bean.AnswerBean;
import com.yhcloud.thankyou.module.homework.bean.StudentHomeworkBean;
import com.yhcloud.thankyou.module.homework.bean.StudentQuestionBean;
import com.yhcloud.thankyou.module.homework.bean.TeacherHomeworkBean;
import com.yhcloud.thankyou.module.homework.bean.SendAnswerBean;
import com.yhcloud.thankyou.module.homework.bean.TeacherQuestionBean;
import com.yhcloud.thankyou.module.homework.view.AddPhotoActivity;
import com.yhcloud.thankyou.module.homework.bean.HomeworkInfoViewPagerBean;
import com.yhcloud.thankyou.module.homework.view.HomeworkStudentViews;
import com.yhcloud.thankyou.module.homework.view.HomeworkTeacherViews;
import com.yhcloud.thankyou.module.homework.view.IHomeworkInfoActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/11.
 */

public class HomeworkInfoManage {

    private String TAG = getClass().getSimpleName();

    private IHomeworkInfoActivityView mIHomeworkInfoView;
    private Activity mActivity;
    private LogicService mService;
    private LayoutInflater mInflater;
    private int roleId;
    private TeacherHomeworkBean mTeacherHomeworkBean;
    private StudentHomeworkBean mStudentHomeworkBean;
    private ArrayList<StudentQuestionBean> mStudentQuestionBeen;
    private ArrayList<TeacherQuestionBean> mTeacherQuestionBeen;
    private ArrayList<HomeworkInfoViewPagerBean> mViewPagerBeen;
    private ArrayList<AnswerBean> mBeen;
    private SendAnswerBean sendAnswerBean;
    private int page;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String sTime;
    private boolean status;

    //初始化管理器并连接服务
    public HomeworkInfoManage(IHomeworkInfoActivityView iHomeworkInfoView) {
        this.mIHomeworkInfoView = iHomeworkInfoView;
        this.mActivity = (Activity) mIHomeworkInfoView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mBeen = new ArrayList<>();
                mInflater = LayoutInflater.from(mActivity);
                roleId = mService.getUserInfo().getUserInfoBean().getUserRoleId();
                init(roleId);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    //初始化界面显示控件
    public void init(int roleId) {
        mIHomeworkInfoView.initView();
        mIHomeworkInfoView.initEvent();
        mIHomeworkInfoView.setTitle("作业详情");
        Intent intent = mActivity.getIntent();
        switch (roleId) {
            case 1004:
                mTeacherHomeworkBean = (TeacherHomeworkBean) intent.getSerializableExtra("teacherHomeworkBean");
                getTeacherHomeworkInfo(page);
                break;
            case 1010:
                mTeacherHomeworkBean = (TeacherHomeworkBean) intent.getSerializableExtra("teacherHomeworkBean");
                getTeacherHomeworkInfo(page);
                break;
            case 1011:
                mStudentHomeworkBean = (StudentHomeworkBean) intent.getSerializableExtra("studentHomeworkBean");
                if ("2".equals(mStudentHomeworkBean.getStatus())) {
                    status = false;
                    mIHomeworkInfoView.setRightTitle("交作业");
                } else {
                    status = true;
                }
                getStudentHomeworkInfo(page);
                //设置作业本开始时间
                sTime = format.format(new Date());
                Tools.print(TAG, "当前时间:" + sTime);
                break;
            case 1012:
                mStudentHomeworkBean = (StudentHomeworkBean) intent.getSerializableExtra("studentHomeworkBean");
                mStudentQuestionBeen = new ArrayList<>();
                if ("2".equals(mStudentHomeworkBean.getStatus())) {
                    status = false;
                    mIHomeworkInfoView.setRightTitle("交作业");
                } else {
                    status = true;
                }
                getStudentHomeworkInfo(page);
                //设置作业本开始时间
                sTime = format.format(new Date());
                Tools.print(TAG, "当前时间:" + sTime);
                break;
        }
    }

    //拿去老师作业详细信息
    public void getTeacherHomeworkInfo(final int pageNum) {
        mIHomeworkInfoView.showLoading(R.string.loading_data);
        mService.getTeacherHomeworkInfo(mTeacherHomeworkBean.getHomeworkId(), new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("QuestionList");
                        if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
                            mTeacherQuestionBeen = new ArrayList<>();
                            Gson gson = new Gson();
                            ArrayList<TeacherQuestionBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<TeacherQuestionBean>>(){}.getType());
                            mTeacherQuestionBeen.addAll(list);
                            initTeacherViewPager(mTeacherQuestionBeen);
                            mIHomeworkInfoView.selectViewPager(pageNum);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIHomeworkInfoView.showToastMsg(msg);
                        } else {
                            mIHomeworkInfoView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIHomeworkInfoView.showToastMsg(R.string.error_connection);
                    mIHomeworkInfoView.hiddenLoading();
                }
                mIHomeworkInfoView.hiddenLoading();
            }

            @Override
            public void callFailure() {

            }
        });
    }

    //拿去学生作业详细信息
    public void getStudentHomeworkInfo(final int pageNum) {
        mIHomeworkInfoView.showLoading(R.string.loading_data);
        mService.getStudentHomeworkInfo(mStudentHomeworkBean.getWorkId(), new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("QuestionList");
                        if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
                            Gson gson = new Gson();
                            mStudentQuestionBeen = new ArrayList<>();
                            ArrayList<StudentQuestionBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<StudentQuestionBean>>(){}.getType());
                            mStudentQuestionBeen.addAll(list);
                            initStudentViewPager(mStudentQuestionBeen);
                            mIHomeworkInfoView.selectViewPager(pageNum);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIHomeworkInfoView.showToastMsg(msg);
                        } else {
                            mIHomeworkInfoView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIHomeworkInfoView.hiddenLoading();
                }
                mIHomeworkInfoView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIHomeworkInfoView.hiddenLoading();
                mIHomeworkInfoView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void initStudentViewPager(final ArrayList<StudentQuestionBean> list) {
        mViewPagerBeen = new ArrayList<>();
        HomeworkStudentViews homeworkStudentViews = new HomeworkStudentViews(mActivity);
        for (int i = 0, n = list.size(); i < n; i++) {
            StudentQuestionBean studentQuestionBean = list.get(i);
            HomeworkInfoViewPagerBean hivpb = null;
            if (null != studentQuestionBean.getResourceType()) {
                switch (studentQuestionBean.getResourceType()) {
                    case "0":
                        //单选题
                        hivpb = new HomeworkInfoViewPagerBean();
                        hivpb.setView(homeworkStudentViews.getRadioView(studentQuestionBean, i, list.size(), status));
                        break;
                    case "2":
                        //判断题
                        hivpb = new HomeworkInfoViewPagerBean();
                        hivpb.setView(homeworkStudentViews.getRadioView(studentQuestionBean, i, list.size(), status));
                        break;
                    case "3":
                        //多选题
                        hivpb = new HomeworkInfoViewPagerBean();
                        hivpb.setView(homeworkStudentViews.getChoiceView(studentQuestionBean, i, list.size(), status));
                        break;
                    case "4":
                        //填空题
                        hivpb = new HomeworkInfoViewPagerBean();
                        hivpb.setView(homeworkStudentViews.getBlankView(studentQuestionBean, i, list.size(), status));
                        break;
                    default:
                        //主观题
                        hivpb = new HomeworkInfoViewPagerBean();
                        hivpb.setView(homeworkStudentViews.getSubjectiveView(studentQuestionBean, i, list.size()));
                        break;
                }
            } else {
                mIHomeworkInfoView.showToastMsg("作业记录不全");
                mActivity.finish();
                return;
            }
            mViewPagerBeen.add(hivpb);
        }
        mIHomeworkInfoView.showViewPager(mViewPagerBeen);
    }

    public void initTeacherViewPager(ArrayList<TeacherQuestionBean> list) {
        mViewPagerBeen = new ArrayList<>();
        HomeworkTeacherViews homeworkTeacherViews = new HomeworkTeacherViews(mActivity);
        HomeworkInfoViewPagerBean hivpb;
        for (int i = 0; i < list.size(); i++) {
            TeacherQuestionBean teacherQuestionBean = list.get(i);
            switch (list.get(i).getQuestionType()) {
                //单选题
                case "0":
                    hivpb = new HomeworkInfoViewPagerBean();
                    hivpb.setView(homeworkTeacherViews.getObjectiveView(teacherQuestionBean, i, list.size()));
                    break;
                //判断题
                case "2":
                    hivpb = new HomeworkInfoViewPagerBean();
                    hivpb.setView(homeworkTeacherViews.getObjectiveView(teacherQuestionBean, i, list.size()));
                    break;
                //多选题
                case "3":
                    hivpb = new HomeworkInfoViewPagerBean();
                    hivpb.setView(homeworkTeacherViews.getObjectiveView(teacherQuestionBean, i, list.size()));
                    break;
                //填空题
                case "4":
                    hivpb = new HomeworkInfoViewPagerBean();
                    hivpb.setView(homeworkTeacherViews.getObjectiveView(teacherQuestionBean, i, list.size()));
                    break;
                //主观题
                default:
                    hivpb = new HomeworkInfoViewPagerBean();
                    hivpb.setView(homeworkTeacherViews.getSubjectiveView(teacherQuestionBean, i, list.size()));
                    break;
            }
            mViewPagerBeen.add(hivpb);
        }
        mIHomeworkInfoView.showViewPager(mViewPagerBeen);
    }

    public void setViewPager(int position) {
        String type;
        switch (roleId) {
            case 1004:
                break;
            case 1010:
                break;
            case 1011:
                type = mStudentQuestionBeen.get(position).getResourceType();
                switch (type) {
                    case "0":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    case "2":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    case "3":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    case "4":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    default:
                        mIHomeworkInfoView.showPhoto(true);
                        break;
                }
                if ("1".equals(mStudentHomeworkBean.getStatus())) {
                    mIHomeworkInfoView.showPhoto(false);
                }
                break;
            case 1012:
                type = mStudentQuestionBeen.get(position).getResourceType();
                switch (type) {
                    case "0":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    case "2":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    case "3":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    case "4":
                        mIHomeworkInfoView.showPhoto(false);
                        break;
                    default:
                        mIHomeworkInfoView.showPhoto(true);
                        break;
                }
                if ("1".equals(mStudentHomeworkBean.getStatus())) {
                    mIHomeworkInfoView.showPhoto(false);
                }
                break;
            default:
                break;
        }
    }

    public void lastHomework() {
        if (0 < page) {
            page -= 1;
            setViewPager(page);
            mIHomeworkInfoView.selectViewPager(page);
        } else {
            mIHomeworkInfoView.showToastMsg("当前题是第一题");
        }
    }

    public void nextHomework() {
        switch (roleId) {
            case 1004:
                if (mTeacherQuestionBeen.size() - 1 > page) {
                    page += 1;
                    setViewPager(page);
                    mIHomeworkInfoView.selectViewPager(page);
                } else {
                    mIHomeworkInfoView.showToastMsg("当前题是最后一题");
                }
                break;
            case 1010:
                if (mTeacherQuestionBeen.size() - 1 > page) {
                    page += 1;
                    setViewPager(page);
                    mIHomeworkInfoView.selectViewPager(page);
                } else {
                    mIHomeworkInfoView.showToastMsg("当前题是最后一题");
                }
                break;
            case 1011:
                if (mStudentQuestionBeen.size() - 1 > page) {
                    page += 1;
                    setViewPager(page);
                    mIHomeworkInfoView.selectViewPager(page);
                } else {
                    mIHomeworkInfoView.showToastMsg("当前题是最后一题");
                }
                break;
            case 1012:
                if (mStudentQuestionBeen.size() - 1 > page) {
                    page += 1;
                    setViewPager(page);
                    mIHomeworkInfoView.selectViewPager(page);
                } else {
                    mIHomeworkInfoView.showToastMsg("当前题是最后一题");
                }
                break;
            default:
                break;
        }
    }

    public void setPage (int page) {
        this.page = page;
    }

    public void submitHomework() {
        mIHomeworkInfoView.showLoading(R.string.loading_data);
        sendAnswerBean = new SendAnswerBean();
        Tools.print(TAG, "提交作业~~~");
        for (StudentQuestionBean studentQuestionBean : mStudentQuestionBeen) {
            AnswerBean answerBean = new AnswerBean();
            answerBean.setStartTime(sTime);
            answerBean.setQuestionId(studentQuestionBean.getQusetionId());
            answerBean.setScore(studentQuestionBean.getScore());
            answerBean.setType(studentQuestionBean.getResourceType());
            answerBean.setAnswerList((ArrayList<String>) studentQuestionBean.getAnswerContent());
            answerBean.setEndTime(Tools.getNowDateTime());
            mBeen.add(answerBean);
        }
        sendAnswerBean.setHomeworkId(mStudentHomeworkBean.getWorkId());
        sendAnswerBean.setHomeworkTime(Tools.getNowDateTime());
        sendAnswerBean.setHomeworkList(mBeen);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(sendAnswerBean);
        Tools.print(TAG, "提交的json为:" + jsonObject);
        mService.sendStudentObjectiveHomework(jsonObject, new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                mIHomeworkInfoView.hiddenLoading();
                mIHomeworkInfoView.showDialog();
            }

            @Override
            public void callFailure() {
                mIHomeworkInfoView.hiddenLoading();
            }
        });
    }

    public void closePage() {
        mIHomeworkInfoView.hiddenLoading();
        mActivity.finish();
    }

    //拍照上传
    public void goPhoto() {
        Tools.print(TAG, "去拍照");
        Intent intent = new Intent(mActivity, AddPhotoActivity.class);
        intent.putExtra("page", page);
        intent.putExtra("workId", mStudentHomeworkBean.getWorkId());
        Tools.print(TAG, "作业ID:" + mStudentHomeworkBean.getWorkId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStudentQuestionBeen", mStudentQuestionBeen.get(page));
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 101);
    }

    public void updateStudentHomework() {
        mIHomeworkInfoView.showLoading(R.string.loading_data);
        mService.updateStudentHomework(mStudentHomeworkBean.getWorkId(), new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        closePage();
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIHomeworkInfoView.showToastMsg(msg);
                        } else {
                            mIHomeworkInfoView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIHomeworkInfoView.showToastMsg(R.string.error_connection);
                    mIHomeworkInfoView.hiddenLoading();
                }
                mIHomeworkInfoView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIHomeworkInfoView.showToastMsg(R.string.error_connection);
                mIHomeworkInfoView.hiddenLoading();
            }
        });
    }

    public void refreshData(Intent data) {
        page = data.getIntExtra("page", 0);
        getStudentHomeworkInfo(page);
    }
}
