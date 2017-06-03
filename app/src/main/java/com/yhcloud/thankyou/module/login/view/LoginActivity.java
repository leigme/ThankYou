package com.yhcloud.thankyou.module.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.module.login.manage.LoginManage;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.module.index.view.MainActivity;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    private String TAG = LoginActivity.class.getName();
    //视图控件
    private EditText et_login_username, et_login_password;
    private TextView btn_login_send;
    //管理器
    private LoginManage mLoginManage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        et_login_username = findView(R.id.et_login_username);
        et_login_username.setSingleLine();
        et_login_password = findView(R.id.et_login_password);
        btn_login_send = findView(R.id.btn_login_send);
    }

    @Override
    public void initEvents() {
        setOnClick(btn_login_send);
        et_login_username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //让mPasswordEdit获取输入焦点
                    et_login_password.requestFocus();
                    return true;
                }
                return false;
            }
        });
        et_login_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 这两个条件必须同时成立，如果仅仅用了enter判断，就会执行两次
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 执行发送消息等操作
                    mLoginManage.login();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initDatas() {
        mLoginManage = new LoginManage(this);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_send:
                mLoginManage.login();
                break;
        }
    }

    @Override
    public String getUserName() {
        return et_login_username.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return et_login_password.getText().toString().trim();
    }

    @Override
    public void showMsg(int msg) {
        MyToast.showToast(this, msg);
    }

    @Override
    public void pushMainActivity(ArrayList<ClassInfoBean> classInfoBeen) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ClassInfos", classInfoBeen);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void clearUsername() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void initData(String username, String password) {
        et_login_username.setText(username);
        et_login_password.setText(password);
    }

}
