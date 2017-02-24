package com.yhcloud.thankyou.module.image.view;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.module.image.adapter.BigImageViewPagerAdapter;
import com.yhcloud.thankyou.module.image.manage.BigImageManage;

import java.util.ArrayList;

public class BigImageActivity extends ABaseActivity implements IBigImageActivityView {

    //视图控件
    private ViewPager vpImages;
    private ProgressDialog mProgressDialog;
    //适配器
    private BigImageViewPagerAdapter bivpa;
    //管理器
    private BigImageManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_big_image);
        mManage = new BigImageManage(this);
    }

    @Override
    public void initView() {
        vpImages = (ViewPager) findViewById(R.id.vp_image_list);
        //5.0以上版本才支持
        vpImages.post(new Runnable() {
            @Override
            public void run() {
                // 圆形动画的x坐标  位于View的中心
                int cx = (vpImages.getLeft() + vpImages.getRight()) / 2;

                //圆形动画的y坐标  位于View的中心
                int cy = (vpImages.getTop() + vpImages.getBottom()) / 2;

                //起始大小半径
                float startRadius = 0f;

                //结束大小半径 大小为图片对角线的一半
                float endRadius = (float) Math.sqrt(cx * cx + cy * cy);
                //创建动画
                Animator animator = ViewAnimationUtils.createCircularReveal(vpImages, cx, cy, startRadius, endRadius);

                //在动画开始的地方速率改变比较慢,然后开始加速
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(600);
                //执行动画
                animator.start();
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {
        hiddenLoading();
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.loading_data));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public void showToastMsg(int msgId) {

    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public void init(ArrayList<View> views) {
        if (null == bivpa) {
            bivpa = new BigImageViewPagerAdapter(views);
            vpImages.setAdapter(bivpa);
        }
    }

    @Override
    public void setViewPager(int position) {
        vpImages.setCurrentItem(position);
    }
}
