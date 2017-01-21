package com.yhcloud.thankyou.module.classnotification.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.classnotification.manage.ClassNotificationDetailManage;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.utils.myview.MyWebView;

public class ClassNotificationDetailActivity extends AppCompatActivity implements IClassNotificationDetailView {

    //视图控件
    private LinearLayout llBack, llMenu;
    private TextView tvTitle, tvMenu;
    private ImageView ivMenu;
    private MyWebView mwvClassnotificationInfo;
    private ProgressDialog mProgressDialog;
    //管理器
    private ClassNotificationDetailManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_notification_detail);
        mManage = new ClassNotificationDetailManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        llMenu = (LinearLayout) findViewById(R.id.ll_header_right);
        tvMenu = (TextView) findViewById(R.id.tv_header_right);
        ivMenu = (ImageView) findViewById(R.id.iv_header_right);
        ivMenu.setVisibility(View.INVISIBLE);
        mwvClassnotificationInfo = (MyWebView) findViewById(R.id.mwv_classnotification_detail);
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
                    case R.id.ll_header_right:
                        mManage.nextNotification();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llMenu.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {
        hiddenLoading();
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
        llMenu.setVisibility(View.VISIBLE);
        tvMenu.setText(title);
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
    public void showWeb(String url) {
        mwvClassnotificationInfo.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mManage.closePage();
    }
}
