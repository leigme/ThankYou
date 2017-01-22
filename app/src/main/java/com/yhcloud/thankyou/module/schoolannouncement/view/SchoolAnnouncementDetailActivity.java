package com.yhcloud.thankyou.module.schoolannouncement.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.schoolannouncement.manage.SchoolAnnouncementDetailManage;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.utils.myview.MyWebView;

public class SchoolAnnouncementDetailActivity extends ABaseActivity implements ISchoolAnnouncementDetailActivityView {

    //视图控件
    private LinearLayout llBack, llMenu;
    private TextView tvTitle, tvMenu;
    private ImageView ivMenu;
    private MyWebView mwvContent;
    private ProgressDialog mProgressDialog;
    //管理器
    private SchoolAnnouncementDetailManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_announcement_detail);
        mManage = new SchoolAnnouncementDetailManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        llMenu = (LinearLayout) findViewById(R.id.ll_header_right);
        tvMenu = (TextView) findViewById(R.id.tv_header_right);
        mwvContent = (MyWebView) findViewById(R.id.mwv_schoolannouncement_content);
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
        llMenu.setVisibility(View.VISIBLE);
        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManage.nextAnnouncement();
            }
        });
        tvMenu.setText(title);
        ivMenu = (ImageView) findViewById(R.id.iv_header_right);
        ivMenu.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showWeb(String url) {
        mwvContent.loadUrl(Constant.SERVICEADDRESS + url);
    }
}
