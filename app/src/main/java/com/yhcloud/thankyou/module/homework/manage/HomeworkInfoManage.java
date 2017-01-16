package com.yhcloud.thankyou.module.homework.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.homework.view.AddPhotoActivity;
import com.yhcloud.thankyou.module.homework.adapter.StudentQuestionListAdpater;
import com.yhcloud.thankyou.module.homework.adapter.StudentQuestionSubjectiveListAdapter;
import com.yhcloud.thankyou.module.homework.bean.HomeworkInfoViewPagerBean;
import com.yhcloud.thankyou.module.homework.bean.QuestionBean;
import com.yhcloud.thankyou.module.homework.bean.StudentHomeworkBean;
import com.yhcloud.thankyou.module.homework.bean.StudentQuestionBean;
import com.yhcloud.thankyou.module.homework.bean.TeacherHomeworkBean;
import com.yhcloud.thankyou.module.homework.view.IHomeworkInfoView;
import com.yhcloud.thankyou.module.image.view.BigImageActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.DividerItemDecoration;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/11.
 */

public class HomeworkInfoManage {

    private String TAG = getClass().getSimpleName();

    private IHomeworkInfoView mIHomeworkInfoView;
    private Activity mActivity;
    private LogicService mService;
    private LayoutInflater mInflater;
    private int roleId;
    private TeacherHomeworkBean mTeacherHomeworkBean;
    private StudentHomeworkBean mStudentHomeworkBean;
    private ArrayList<StudentQuestionBean> mStudentQuestionBeen;
    private ArrayList<HomeworkInfoViewPagerBean> mViewPagerBeen;
    private int page;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String sTime;

    //初始化管理器并连接服务
    public HomeworkInfoManage(IHomeworkInfoView iHomeworkInfoView) {
        this.mIHomeworkInfoView = iHomeworkInfoView;
        this.mActivity = (Activity) mIHomeworkInfoView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mInflater = LayoutInflater.from(mActivity);
                roleId = mService.getUserInfo().getUserInfoBean().getUserRoleId();
                mViewPagerBeen = new ArrayList<>();
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
                break;
            case 1010:
                mTeacherHomeworkBean = (TeacherHomeworkBean) intent.getSerializableExtra("teacherHomeworkBean");
                break;
            case 1011:
                mStudentHomeworkBean = (StudentHomeworkBean) intent.getSerializableExtra("studentHomeworkBean");
                mStudentQuestionBeen = new ArrayList<>();
                if ("1".equals(mStudentHomeworkBean.getStatus())) {

                } else {
                    mIHomeworkInfoView.setRightTitle("提交");
                }
                getStudentHomeworkInfo();
                //设置作业本开始时间
                sTime = format.format(new Date());
                Tools.print(TAG, "当前时间:" + sTime);
                break;
            case 1012:
                mStudentHomeworkBean = (StudentHomeworkBean) intent.getSerializableExtra("studentHomeworkBean");
                mStudentQuestionBeen = new ArrayList<>();
                if ("1".equals(mStudentHomeworkBean.getStatus())) {

                } else {
                    mIHomeworkInfoView.setRightTitle("提交");
                }
                getStudentHomeworkInfo();
                //设置作业本开始时间
                sTime = format.format(new Date());
                Tools.print(TAG, "当前时间:" + sTime);
                break;
        }
    }

    //拿去老师作业详细信息
    public void getTeacherHomeworkInfo() {}

    //拿去学生作业详细信息
    public void getStudentHomeworkInfo() {
        mIHomeworkInfoView.showLoading(R.string.loading_data);
        mService.getStudentHomeworkInfo(mStudentHomeworkBean.getWorkId(), new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("QuestionList");
                        if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<StudentQuestionBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<StudentQuestionBean>>(){}.getType());
                            mStudentQuestionBeen.addAll(list);
                            initStudentViewPager(mStudentQuestionBeen);
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
            public void callFailed() {
                mIHomeworkInfoView.hiddenLoading();
                mIHomeworkInfoView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void initStudentViewPager(final ArrayList<StudentQuestionBean> list) {
        for (int i = 0, n = list.size(); i < n; i++) {
            StudentQuestionBean questionBean = list.get(i);
            HomeworkInfoViewPagerBean hivpb = null;
            switch (questionBean.getResourceType()) {
                case "0":
                    //单选题
                    hivpb = new HomeworkInfoViewPagerBean();
                    View radioView = mInflater.inflate(R.layout.fragment_homeworkinfo_radio, null);
                    TextView radioTitle = (TextView) radioView.findViewById(R.id.tv_homework_title);
                    TextView radioTitleName = (TextView) radioView.findViewById(R.id.tv_homework_title_name);
                    TextView radioQuestionNumber = (TextView) radioView.findViewById(R.id.question_number);
                    radioQuestionNumber.setText(MessageFormat.format("{0}/{1}", i + 1, n));
                    RecyclerView radioQuestionList = (RecyclerView) radioView.findViewById(R.id.rv_homework_list);
                    radioTitle.setText(questionBean.getQuestionTitle());
                    radioTitleName.setText(questionBean.getQuestionTitleName());
                    final ArrayList<QuestionBean> radioQuestionBeanArrayList = new ArrayList<>();
                    for (String s: questionBean.getQuestionContent()) {
                        QuestionBean qb = new QuestionBean(s);
                        radioQuestionBeanArrayList.add(qb);
                    }
                    for (String s: questionBean.getAnswerContent()) {
                        radioQuestionBeanArrayList.get(getRadio(s)).setStatus(true);
                    }
                    hivpb.setBeen(radioQuestionBeanArrayList);
                    final StudentQuestionListAdpater radioStudentQuestionListAdpater = new StudentQuestionListAdpater(mActivity, radioQuestionBeanArrayList);
                    radioQuestionList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
                    radioQuestionList.setLayoutManager(new LinearLayoutManager(mActivity));
                    if (!"1".equals(mStudentHomeworkBean.getStatus())) {
                        radioStudentQuestionListAdpater.setIOnClickListener(new IOnClickListener() {
                            @Override
                            public void OnItemClickListener(View view, int position) {
                                for (QuestionBean qb: radioQuestionBeanArrayList) {
                                    qb.setStatus(false);
                                }
                                radioQuestionBeanArrayList.get(position).setStatus(true);
                                radioStudentQuestionListAdpater.refreshData(radioQuestionBeanArrayList);
                            }

                            @Override
                            public void OnItemLongClickListener(View view, int position) {

                            }
                        });
                    }
                    radioQuestionList.setAdapter(radioStudentQuestionListAdpater);
                    hivpb.setAdpater(radioStudentQuestionListAdpater);
                    hivpb.setView(radioView);
                    break;
                case "2":
                    //判断题
                    hivpb = new HomeworkInfoViewPagerBean();
                    View judgmentView = mInflater.inflate(R.layout.fragment_homeworkinfo_radio, null);
                    TextView judgmentTitle = (TextView) judgmentView.findViewById(R.id.tv_homework_title);
                    TextView judgmentTitleName = (TextView) judgmentView.findViewById(R.id.tv_homework_title_name);
                    TextView judgmentQuestionNumber = (TextView) judgmentView.findViewById(R.id.question_number);
                    judgmentQuestionNumber.setText(MessageFormat.format("{0}/{1}", i + 1, n));
                    RecyclerView judgmentQuestionList = (RecyclerView) judgmentView.findViewById(R.id.rv_homework_list);
                    judgmentTitle.setText(questionBean.getQuestionTitle());
                    judgmentTitleName.setText(questionBean.getQuestionTitleName());
                    final ArrayList<QuestionBean> judgmentQuestionBeanArrayList = new ArrayList<>();
                    for (String s: questionBean.getQuestionContent()) {
                        QuestionBean qb = new QuestionBean(s);
                        judgmentQuestionBeanArrayList.add(qb);
                    }
                    for (String s: questionBean.getAnswerContent()) {
                        judgmentQuestionBeanArrayList.get(getRadio(s)).setStatus(true);
                    }
                    hivpb.setBeen(judgmentQuestionBeanArrayList);
                    final StudentQuestionListAdpater judgmentStudentQuestionListAdpater = new StudentQuestionListAdpater(mActivity, judgmentQuestionBeanArrayList);
                    judgmentQuestionList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
                    judgmentQuestionList.setLayoutManager(new LinearLayoutManager(mActivity));
                    if (!"1".equals(mStudentHomeworkBean.getStatus())) {
                        judgmentStudentQuestionListAdpater.setIOnClickListener(new IOnClickListener() {
                            @Override
                            public void OnItemClickListener(View view, int position) {
                                for (QuestionBean qb: judgmentQuestionBeanArrayList) {
                                    qb.setStatus(false);
                                }
                                judgmentQuestionBeanArrayList.get(position).setStatus(true);
                                judgmentStudentQuestionListAdpater.refreshData(judgmentQuestionBeanArrayList);
                            }

                            @Override
                            public void OnItemLongClickListener(View view, int position) {

                            }
                        });
                    }
                    judgmentQuestionList.setAdapter(judgmentStudentQuestionListAdpater);
                    hivpb.setAdpater(judgmentStudentQuestionListAdpater);
                    hivpb.setView(judgmentView);
                    break;
                case "3":
                    //多选题
                    hivpb = new HomeworkInfoViewPagerBean();
                    View choiceView = mInflater.inflate(R.layout.fragment_homeworkinfo_radio, null);
                    TextView choiceTitle = (TextView) choiceView.findViewById(R.id.tv_homework_title);
                    TextView choiceTitleName = (TextView) choiceView.findViewById(R.id.tv_homework_title_name);
                    TextView choiceQuestionNumber = (TextView) choiceView.findViewById(R.id.question_number);
                    choiceQuestionNumber.setText(MessageFormat.format("{0}/{1}", i + 1, n));
                    RecyclerView choiceQuestionList = (RecyclerView) choiceView.findViewById(R.id.rv_homework_list);
                    choiceTitle.setText(questionBean.getQuestionTitle());
                    choiceTitleName.setText(questionBean.getQuestionTitleName());
                    final ArrayList<QuestionBean> choiceQuestionBeanArrayList = new ArrayList<>();
                    for (String s: questionBean.getQuestionContent()) {
                        QuestionBean qb = new QuestionBean(s);
                        choiceQuestionBeanArrayList.add(qb);
                    }
                    for (String s: questionBean.getAnswerContent()) {
                        choiceQuestionBeanArrayList.get(getRadio(s)).setStatus(true);
                    }
                    hivpb.setBeen(choiceQuestionBeanArrayList);
                    final StudentQuestionListAdpater choiceStudentQuestionListAdpater = new StudentQuestionListAdpater(mActivity, choiceQuestionBeanArrayList);
                    choiceQuestionList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
                    choiceQuestionList.setLayoutManager(new LinearLayoutManager(mActivity));
                    if (!"1".equals(mStudentHomeworkBean.getStatus())) {
                        choiceStudentQuestionListAdpater.setIOnClickListener(new IOnClickListener() {
                            @Override
                            public void OnItemClickListener(View view, int position) {
                                if (choiceQuestionBeanArrayList.get(position).isStatus()) {
                                    choiceQuestionBeanArrayList.get(position).setStatus(false);
                                } else {
                                    choiceQuestionBeanArrayList.get(position).setStatus(true);
                                }
                                choiceStudentQuestionListAdpater.refreshData(choiceQuestionBeanArrayList);
                            }

                            @Override
                            public void OnItemLongClickListener(View view, int position) {

                            }
                        });
                    }
                    choiceQuestionList.setAdapter(choiceStudentQuestionListAdpater);
                    hivpb.setAdpater(choiceStudentQuestionListAdpater);
                    hivpb.setView(choiceView);
                    break;
                case "4":
                    //填空题
                    hivpb = new HomeworkInfoViewPagerBean();
                    View blankView = mInflater.inflate(R.layout.fragment_homeworkinfo_radio, null);
                    TextView blankTitle = (TextView) blankView.findViewById(R.id.tv_homework_title);
                    TextView blankTitleName = (TextView) blankView.findViewById(R.id.tv_homework_title_name);
                    TextView blankQuestionNumber = (TextView) blankView.findViewById(R.id.question_number);
                    blankQuestionNumber.setText(MessageFormat.format("{0}/{1}", i + 1, n));
                    RecyclerView blankQuestionList = (RecyclerView) blankView.findViewById(R.id.rv_homework_list);
                    blankTitle.setText(questionBean.getQuestionTitle());
                    blankTitleName.setText(questionBean.getQuestionTitleName());
                    final ArrayList<QuestionBean> blankQuestionBeanArrayList = new ArrayList<>();
                    for (String s: questionBean.getQuestionContent()) {
                        QuestionBean qb = new QuestionBean(s);
                        blankQuestionBeanArrayList.add(qb);
                    }
//                    for (String s: questionBean.getAnswerContent()) {
//                        blankQuestionBeanArrayList.get(getRadio(s)).setStatus(true);
//                    }
                    hivpb.setBeen(blankQuestionBeanArrayList);
                    final StudentQuestionListAdpater blankStudentQuestionListAdpater = new StudentQuestionListAdpater(mActivity, blankQuestionBeanArrayList);
                    blankQuestionList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
                    blankQuestionList.setLayoutManager(new LinearLayoutManager(mActivity));
                    if (!"1".equals(mStudentHomeworkBean.getStatus())) {
                        blankStudentQuestionListAdpater.setIOnClickListener(new IOnClickListener() {
                            @Override
                            public void OnItemClickListener(View view, int position) {
                                for (QuestionBean qb: blankQuestionBeanArrayList) {
                                    qb.setStatus(false);
                                }
                                blankQuestionBeanArrayList.get(position).setStatus(true);
                                blankStudentQuestionListAdpater.refreshData(blankQuestionBeanArrayList);
                            }

                            @Override
                            public void OnItemLongClickListener(View view, int position) {

                            }
                        });
                    }
                    blankQuestionList.setAdapter(blankStudentQuestionListAdpater);
                    hivpb.setAdpater(blankStudentQuestionListAdpater);
                    hivpb.setView(blankView);
                    break;
                default:
                    //主观题
                    hivpb = new HomeworkInfoViewPagerBean();
                    View subjectiveView = mInflater.inflate(R.layout.fragment_homeworkinfo_radio, null);
                    TextView subjectiveTitle = (TextView) subjectiveView.findViewById(R.id.tv_homework_title);
                    TextView subjectiveTitleName = (TextView) subjectiveView.findViewById(R.id.tv_homework_title_name);
                    TextView subjectiveQuestionNumber = (TextView) subjectiveView.findViewById(R.id.question_number);
                    subjectiveQuestionNumber.setText(MessageFormat.format("{0}/{1}", i + 1, n));
                    RecyclerView subjectiveQuestionList = (RecyclerView) subjectiveView.findViewById(R.id.rv_homework_list);
                    subjectiveTitle.setText(questionBean.getQuestionTitle());
                    subjectiveTitleName.setText(questionBean.getQuestionTitleName());
                    final ArrayList<String> subjectiveQuestionBeanArrayList = new ArrayList<>();
                    for (String s: questionBean.getAnswerPic()) {
                        subjectiveQuestionBeanArrayList.add("http://192.168.0.139/edu" + s);
                    }
                    final StudentQuestionSubjectiveListAdapter subjectiveStudentQuestionListAdpater = new StudentQuestionSubjectiveListAdapter(mActivity, subjectiveQuestionBeanArrayList);
                    subjectiveQuestionList.setLayoutManager(new GridLayoutManager(mActivity, 4));
                    subjectiveStudentQuestionListAdpater.setIOnClickListener(new IOnClickListener() {
                        @Override
                        public void OnItemClickListener(View view, int position) {
                            Intent intent = new Intent(mActivity, BigImageActivity.class);
                            intent.putExtra(Constant.PAGE_NUM, position);
                            intent.putStringArrayListExtra(Constant.IMAGEURLS, subjectiveQuestionBeanArrayList);
                            mActivity.startActivity(intent);
                        }

                        @Override
                        public void OnItemLongClickListener(View view, int position) {

                        }
                    });
                    subjectiveQuestionList.setAdapter(subjectiveStudentQuestionListAdpater);
                    hivpb.setView(subjectiveView);
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
                break;
        }
        if ("1".equals(mStudentHomeworkBean.getStatus())) {
            mIHomeworkInfoView.showPhoto(false);
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
        if (mStudentQuestionBeen.size() - 1 > page) {
            page += 1;
            setViewPager(page);
            mIHomeworkInfoView.selectViewPager(page);
        } else {
            mIHomeworkInfoView.showToastMsg("当前题是最后一题");
        }
    }

    public void setPage (int page) {
        this.page = page;
    }

    public void submitHomework() {
        Tools.print(TAG, "提交作业~~~");

    }

    public void closePage() {
        mActivity.finish();
    }

    //拍照上传
    public void goPhoto() {
        Tools.print(TAG, "去拍照");
        Intent intent = new Intent(mActivity, AddPhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("homeworkBean", mStudentQuestionBeen.get(page));
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 101);
    }

    public int getRadio(String s) {
        String ss = s.toUpperCase();
        ArrayList<String> list = new ArrayList<>();
        for (char x = 'A'; x < 'Z'; x++ ) {
            list.add(String.valueOf(x));
        }
        for (int i = 0; i < list.size(); i++) {
            if (ss.equals(list.get(i))) {
                return i;
            }
        }
        return 0;
    }
}
