package com.yhcloud.thankyou.module.image.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.image.view.BigImageActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public class BigImageManage {

    private BigImageActivityView mIBigImageView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<String> imageUrls;
    private ArrayList<View> mViews;
    private LayoutInflater mInflater;

    public BigImageManage(BigImageActivityView iBigImageView) {
        this.mIBigImageView = iBigImageView;
        this.mActivity = (Activity) mIBigImageView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                imageUrls = new ArrayList<>();
                mViews = new ArrayList<>();
                mInflater = LayoutInflater.from(mActivity);
                mIBigImageView.initView();
                init();
                mIBigImageView.initEvent();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    private void init() {
        Intent intent = mActivity.getIntent();
        int pageNum = intent.getIntExtra(Constant.PAGE_NUM, 0);
        imageUrls = intent.getStringArrayListExtra(Constant.IMAGEURLS);
        if (null != imageUrls && 0 != imageUrls.size()) {
            for (String url: imageUrls) {
                View view = mInflater.inflate(R.layout.fragment_image_list, null);
                ImageView ivImage = (ImageView) view.findViewById(R.id.iv_big_image);
                ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.finish();
                    }
                });
                Glide.with(mActivity)
                        .load(Constant.SERVICEADDRESS + url)
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.icon_big_404)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivImage);
                mViews.add(view);
            }
            mIBigImageView.init(mViews);
            mIBigImageView.setViewPager(pageNum);
        }
    }
}
