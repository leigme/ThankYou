package com.yhcloud.thankyou.module.image.view;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.image.adapter.BigImageViewPagerAdapter;
import com.yhcloud.thankyou.module.image.manage.BigImageManage;

import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity implements IBigImageView{

    //视图控件
    private ViewPager vpImages;
    //适配器
    private BigImageViewPagerAdapter bivpa;
    //管理器
    private BigImageManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        mManage = new BigImageManage(this);
    }

    @Override
    public void initView() {
        vpImages = (ViewPager) findViewById(R.id.vp_image_list);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {

    }

    @Override
    public void hiddenLoading() {

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
