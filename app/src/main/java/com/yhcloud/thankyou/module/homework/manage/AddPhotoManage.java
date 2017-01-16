package com.yhcloud.thankyou.module.homework.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.homework.bean.StudentQuestionBean;
import com.yhcloud.thankyou.module.image.view.BigImageActivity;
import com.yhcloud.thankyou.module.homework.view.IAddPhotoView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
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

    private IAddPhotoView mIAddPhotoView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<String> mBeen;
    private int REQUEST_PHOTO = 101, REQUEST_CAMERA = 102;
    private String imageFilePath, workId, questionId, score, pageNum, startTime;
    private Uri imageFileUri;
    private ImgSelConfig config;
    private StudentQuestionBean mStudentQuestionBean;

    public AddPhotoManage(IAddPhotoView iAddPhotoView) {
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
            mStudentQuestionBean = (StudentQuestionBean) intent.getSerializableExtra("homeworkBean");
            if (null != mStudentQuestionBean) {
                Tools.print(TAG, "作业编号:" + mStudentQuestionBean.getHomeworkId() + "问题编号:" + mStudentQuestionBean.getQusetionId());
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
            if (9 < position) {
                mIAddPhotoView.showToastMsg("最多添加9张图片");
            } else {
                ImgSelActivity.startActivity(mActivity, config, REQUEST_PHOTO);
            }
        }
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
            Glide.with(context).load(path).into(imageView);
        }
    };

    private void init() {
        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg";//设置图片的保存路径
        File imageFile = new File(imageFilePath);//通过路径创建保存文件
        imageFileUri = Uri.fromFile(imageFile);//获取文件的Uri

        config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // “确定”按钮背景色
                .btnBgColor(Color.parseColor("#6BBE51"))
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#6BBE51"))
                // 返回图标ResId
                .backResId(R.mipmap.icon_go_back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#6BBE51"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();
    }

    public void result(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PHOTO && resultCode == mActivity.RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
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
        Tools.print(TAG, "保存");
        ArrayList<String> addImageUrls = new ArrayList<>();
        for (String url: mBeen) {
            if (!url.equals(Constant.ADDIMAGE)) {
                addImageUrls.add(url);
            }
        }
        mService.sendStudentSubjectiveHomework(mStudentQuestionBean.getHomeworkId(),
                mStudentQuestionBean.getQusetionId(), mStudentQuestionBean.getScore(), mIAddPhotoView.getContent(),
                startTime, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), addImageUrls,
                new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        mIAddPhotoView.showToastMsg("保存成功...");
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
                }
                mIAddPhotoView.showToastMsg(R.string.error_connection);
            }

            @Override
            public void callFailed() {
                mIAddPhotoView.showToastMsg(R.string.error_connection);
            }
        });
    }
}
