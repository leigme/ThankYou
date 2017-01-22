package com.yhcloud.thankyou.module.homework.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.homework.manage.AddHomeworkManage;

public class AddHomeworkActivity extends ABaseActivity implements IAddHomeworkActivityView {

    //视图控件
    private LinearLayout llBack, llRight;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    //管理器
    private AddHomeworkManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
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
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
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
            llRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.save();
                }
            });
            ivRight = (ImageView) findViewById(R.id.iv_header_right);
            ivRight.setVisibility(View.INVISIBLE);
            tvRight = (TextView) findViewById(R.id.tv_header_right);
        }
        tvRight.setText(title);
    }
}
