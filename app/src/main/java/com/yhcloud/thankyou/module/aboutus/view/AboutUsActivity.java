package com.yhcloud.thankyou.module.aboutus.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstractd.ABaseActivity;
import com.yhcloud.thankyou.module.aboutus.manage.AboutUsManage;
import com.yhcloud.thankyou.utils.Tools;

public class AboutUsActivity extends ABaseActivity implements IAboutUsActivityView {

    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle, tvInfo;
    private ImageView ivImage;
    //管理器
    private AboutUsManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mManage = new AboutUsManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        tvInfo = (TextView) findViewById(R.id.tv_aboutus_info);
        ivImage = (ImageView) findViewById(R.id.iv_aboutus_image);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        finish();
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

    }

    @Override
    public void setInfo(String info) {
        tvInfo.setText(info);
    }

    @Override
    public void setImageView(String url) {
        Tools.GlideImageUrl(this, url, ivImage);
    }
}
