package com.yhcloud.thankyou.module.classnotification.manage;

import android.app.Activity;
import android.content.Intent;

import com.yhcloud.thankyou.mabstract.ABaseManager;
import com.yhcloud.thankyou.minterface.IBindBaseServiceCallBack;
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.module.classnotification.view.IAddClassNotificationDetailActivityView;
import com.yhcloud.thankyou.module.image.view.MyImgSelActivity;
import com.yhcloud.thankyou.service.BaseService;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leig on 2017/2/17.
 */

public class AddClassNotificationManage extends ABaseManager implements IBindBaseServiceCallBack, ICallBackListener<String>{

    private String TAG = getClass().getSimpleName();

    private IAddClassNotificationDetailActivityView mIAddClassNotificationDetailActivityView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<String> imageList;
    private ImgSelConfig config;

    public AddClassNotificationManage(IAddClassNotificationDetailActivityView iAddClassNotificationDetailActivityView) {
        this.mIAddClassNotificationDetailActivityView = iAddClassNotificationDetailActivityView;
        this.mActivity = (Activity) mIAddClassNotificationDetailActivityView;
        Tools.bingBaseService(mActivity, this);
    }

    private void initData() {
        imageList = new ArrayList<>();
        config = Tools.getImgSelConfig(mActivity.getApplicationContext());
    }

    public void onClickRight() {
        Tools.print(TAG, "点击了右边的按钮~~");
    }

    public void onClickImage(int position) {
        if (position == imageList.size()) {
            if (9 > position) {
                Tools.print(TAG, "去添加图片~~");
                config.maxNum = 10 - imageList.size();
                MyImgSelActivity.startActivity(mActivity, config, Constant.ADDCLASSNOTIFICATION);
            } else {
                Tools.print(TAG, "最多只能添加9张图片~~");
            }
        } else {
            Tools.print(TAG, "去看大图片咯~~");
        }
    }

    public void onClickDelImage(int position) {
        if (position < imageList.size()) {
            imageList.remove(position);
            mIAddClassNotificationDetailActivityView.showImageList(imageList);
        }
    }

    public void addImageResult(Intent data) {
        List<String> pathList = data.getStringArrayListExtra(MyImgSelActivity.INTENT_RESULT);
        for (String s: pathList) {
            imageList.add(s);
        }

    }

    @Override
    public void callSuccess(String s) {

    }

    @Override
    public void callFailure() {

    }

    @Override
    public void bindBaseServiceSuccess(BaseService baseService) {
        mService = (LogicService) baseService;
        initData();
        mIAddClassNotificationDetailActivityView.initView();
        mIAddClassNotificationDetailActivityView.initEvent();
        mIAddClassNotificationDetailActivityView.showImageList(imageList);
    }

    @Override
    public void bindBaseServiceFailure() {
        mActivity.finish();
    }
}
