package com.yhcloud.thankyou.module.homework.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.module.homework.bean.StudentQuestionBean;
import com.yhcloud.thankyou.module.homework.view.IAddPhotoActivityView;
import com.yhcloud.thankyou.module.image.view.BigImageActivity;
import com.yhcloud.thankyou.module.image.view.MyImgSelActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */

public class AddPhotoManage {

    private String TAG = getClass().getSimpleName();

    private IAddPhotoActivityView mIAddPhotoView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<String> mBeen;
    private int page, REQUEST_PHOTO = 101, REQUEST_CAMERA = 102;
    private String imageFilePath, workId, questionId, score, pageNum, startTime;
    private Uri imageFileUri;
    private ImgSelConfig config;
    private StudentQuestionBean mStudentQuestionBean;

    public AddPhotoManage(IAddPhotoActivityView iAddPhotoView) {
        this.mIAddPhotoView = iAddPhotoView;
        this.mActivity = (Activity) mIAddPhotoView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                init();
                mBeen = new ArrayList<>();
                mBeen.add(Constant.ADDIMAGE);
                initData();
                mIAddPhotoView.initView();
                mIAddPhotoView.init();
                mIAddPhotoView.initEvent();
                mIAddPhotoView.setTitle("拍照上传");
                mIAddPhotoView.setRightTitle("保存");
                mIAddPhotoView.showList(mBeen);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void initData() {
        startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Intent intent = mActivity.getIntent();
        if (null != intent) {
            page = intent.getIntExtra("page", 0);
            workId = intent.getStringExtra("workId");
            Tools.print(TAG, "作业ID是:" + workId);
            mStudentQuestionBean = (StudentQuestionBean) intent.getSerializableExtra("mStudentQuestionBeen");
            if (null != mStudentQuestionBean) {
                Tools.print(TAG, "作业编号:" + mStudentQuestionBean.getHomeworkId() + "问题编号:" + mStudentQuestionBean.getQusetionId() + "分数是:" + mStudentQuestionBean.getScore());
            }
        }
    }

    public void goAddPhoto(int position) {
        if (position < mBeen.size() - 1) {
            Tools.print(TAG, "去大图~~~");
            ArrayList<String> imageUrls = new ArrayList<>();
            Intent intent = new Intent(mActivity, BigImageActivity.class);
            for (int i = 0; i < mBeen.size() - 1; i++) {
                imageUrls.add(mBeen.get(i));
            }
            intent.putStringArrayListExtra(Constant.IMAGEURLS, imageUrls);
            intent.putExtra(Constant.PAGE_NUM, position);
            mActivity.startActivity(intent);
        } else {
            if (9 < mBeen.size()) {
                mIAddPhotoView.showToastMsg("最多添加9张图片");
            } else {
                config.maxNum = 10 - mBeen.size();
                MyImgSelActivity.startActivity(mActivity, config, REQUEST_PHOTO);
            }
        }
    }

    private void init() {
        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg";//设置图片的保存路径
        File imageFile = new File(imageFilePath);//通过路径创建保存文件
        imageFileUri = Uri.fromFile(imageFile);//获取文件的Uri

        config = Tools.getImgSelConfig(mActivity.getApplicationContext());
    }

    public void result(int requestCode, int resultCode, Intent data) {
        //从图库里
        if (requestCode == REQUEST_PHOTO && resultCode == mActivity.RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(MyImgSelActivity.INTENT_RESULT);
            int iNum = mBeen.size();
            if (iNum + pathList.size() <= 10) {
                mBeen.addAll(0, pathList);
                mIAddPhotoView.showList(mBeen);
            } else {
                for (int i = 0; i < 11 - iNum; i++) {
                    mBeen.add(0, pathList.get(i));
                }
                mIAddPhotoView.showList(mBeen);
            }
        } else if (requestCode == REQUEST_CAMERA && resultCode == mActivity.RESULT_OK) {
            mBeen.add(0, imageFilePath);
            mIAddPhotoView.showList(mBeen);
        }
    }

    public void delPhoto(int position) {
        mBeen.remove(position);
        mIAddPhotoView.showList(mBeen);
    }

    public void save() {
        mIAddPhotoView.showLoading(R.string.loading_data);
        String content = "";
        if (null != mIAddPhotoView.getContent() && !"".equals(mIAddPhotoView.getContent())) {
            content = mIAddPhotoView.getContent();
        }
        ArrayList<String> addImageUrls = new ArrayList<>();
        for (String url: mBeen) {
            if (!url.equals(Constant.ADDIMAGE)) {
                addImageUrls.add(url);
            }
        }
        mService.sendStudentSubjectiveHomework(workId,
                mStudentQuestionBean.getQusetionId(), content, mStudentQuestionBean.getScore(),
                startTime, Tools.getNowDateTime(), addImageUrls,
                new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        mIAddPhotoView.showToastMsg("保存成功...");
                        mIAddPhotoView.showDialog();
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIAddPhotoView.showToastMsg(msg);
                        } else {
                            mIAddPhotoView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIAddPhotoView.showToastMsg(R.string.error_connection);
                }
                mIAddPhotoView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIAddPhotoView.showToastMsg(R.string.error_connection);
                mIAddPhotoView.hiddenLoading();
            }
        });
    }

    public void updateStudentHomework() {
        mIAddPhotoView.showLoading(R.string.loading_data);
        mService.updateStudentHomework(mStudentQuestionBean.getHomeworkId(), new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        mIAddPhotoView.showToastMsg("提交成功");
                        closePage();
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIAddPhotoView.showToastMsg(msg);
                        } else {
                            mIAddPhotoView.showLoading(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIAddPhotoView.showLoading(R.string.error_connection);
                    mIAddPhotoView.hiddenLoading();
                }
                mIAddPhotoView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mIAddPhotoView.showLoading(R.string.error_connection);
                mIAddPhotoView.hiddenLoading();
            }
        });
    }

    public void closePage() {
        Intent intent = new Intent();
        intent.putExtra("page", page);
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();
    }
}
