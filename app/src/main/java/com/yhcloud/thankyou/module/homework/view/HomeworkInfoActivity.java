package com.yhcloud.thankyou.module.homework.view;

import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.homework.adapter.HomeworkInfoViewPagerAdapter;
import com.yhcloud.thankyou.module.homework.bean.HomeworkInfoViewPagerBean;
import com.yhcloud.thankyou.module.homework.manage.HomeworkInfoManage;
import com.yhcloud.thankyou.utils.myview.MyToast;

import java.util.ArrayList;

public class HomeworkInfoActivity extends AppCompatActivity implements IHomeworkInfoView {

    //视图控件
    private LinearLayout llBack, llRight, llLast, llNext, llPhoto;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private ViewPager mViewPager;
    private View vLine;
    private ProgressDialog mProgressDialog;
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
        llNext = (LinearLayout) findViewById(R.id.ll_homeworkinfo_next);
        mViewPager = (ViewPager) findViewById(R.id.vp_homeworkinfo_page);
        vLine = findViewById(R.id.v_line);
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
                    case R.id.ll_homeworkinfo_next:
                        mManage.nextHomework();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llLast.setOnClickListener(myOnClickListener);
        llNext.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {
        mProgressDialog = ProgressDialog.show(this, null, getString(msgId));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
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
    public void showToastMsg(int msgId) {
        MyToast.showToast(this, msgId);
    }

    @Override
    public void showToastMsg(String msg) {
        MyToast.showToast(this, msg);
    }

    @Override
    public void showPhoto(boolean showed) {
        if (null == llPhoto) {
            llPhoto = (LinearLayout) findViewById(R.id.ll_homeworkinfo_photo);
            llPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.goPhoto();
                }
            });
        }
        if (showed) {
            llPhoto.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.VISIBLE);
        } else {
            llPhoto.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }
    }

    @Override
    public void showViewPager(ArrayList<HomeworkInfoViewPagerBean> list) {
        if (null == hivpa) {
            hivpa = new HomeworkInfoViewPagerAdapter(list);
        }
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
}
