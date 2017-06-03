package com.yhcloud.thankyou.module.homework.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.module.homework.adapter.HomeworkInfoViewPagerAdapter;
import com.yhcloud.thankyou.module.homework.bean.HomeworkInfoViewPagerBean;
import com.yhcloud.thankyou.module.homework.manage.HomeworkInfoManage;

import java.util.ArrayList;

public class HomeworkInfoActivity extends BaseActivity implements HomeworkInfoActivityView {

    //视图控件
    private LinearLayout llBack, llRight, llLast, llNext, llPhoto;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private ViewPager mViewPager;
    //适配器
    private HomeworkInfoViewPagerAdapter hivpa;
    //管理器
    private HomeworkInfoManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_info);
        mManage = new HomeworkInfoManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        llLast = (LinearLayout) findViewById(R.id.ll_homeworkinfo_last);
        llPhoto = (LinearLayout) findViewById(R.id.ll_homeworkinfo_photo);
        llNext = (LinearLayout) findViewById(R.id.ll_homeworkinfo_next);
        mViewPager = (ViewPager) findViewById(R.id.vp_homeworkinfo_page);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        mManage.closePage();
                        break;
                    case R.id.ll_homeworkinfo_last:
                        mManage.lastHomework();
                        break;
                    case R.id.ll_homeworkinfo_photo:
                        mManage.goPhoto();
                        break;
                    case R.id.ll_homeworkinfo_next:
                        mManage.nextHomework();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llLast.setOnClickListener(myOnClickListener);
        llPhoto.setOnClickListener(myOnClickListener);
        llNext.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {
        if (null == llRight) {
            llRight = (LinearLayout) findViewById(R.id.ll_header_right);
            llRight.setVisibility(View.VISIBLE);
            llRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.submitHomework();
                }
            });
            tvRight = (TextView) findViewById(R.id.tv_header_right);
            ivRight = (ImageView) findViewById(R.id.iv_header_right);
            ivRight.setVisibility(View.INVISIBLE);
        }
        tvRight.setText(title);
    }

    @Override
    public void showPhoto(boolean showed) {
        if (showed) {
            llPhoto.setVisibility(View.VISIBLE);
        } else {
            llPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void showViewPager(ArrayList<HomeworkInfoViewPagerBean> list) {
        hivpa = new HomeworkInfoViewPagerAdapter(list);
        mViewPager.setAdapter(hivpa);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mManage.setViewPager(position);
                mManage.setPage(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void selectViewPager(int i) {
        mViewPager.setCurrentItem(i);
    }

    @Override
    public void showDialog() {
//        super.showDialog("", "提交之后将无法更改,是否确认提交本次作业？");
//        this.setSubmitCallBack(new SubmitCallBack() {
//            @Override
//            public void btnOnClick() {
//                mManage.updateStudentHomework();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mManage.refreshData(data);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void processClick(View view) {

    }
}
