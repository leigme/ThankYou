package com.yhcloud.thankyou.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.manage.LoginManage;
import com.yhcloud.thankyou.utils.myview.MyToast;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private String TAG = getClass().getSimpleName();

    private EditText et_login_username, et_login_password;
    private TextView btn_login_send;
    private ProgressDialog mProgressDialog;
    private LoginManage mLoginManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginManage = new LoginManage(this);

        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "RxJava测试~");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "Item:" + s);
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                observer.onNext("Hello");
                observer.onNext(" World!");
                observer.onCompleted();
            }
        });

        observable.subscribe(observer);
    }

    @Override
    public void initView() {
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        btn_login_send = (TextView) findViewById(R.id.btn_login_send);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        btn_login_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginManage.login();
            }
        });
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
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading() {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.logging));
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
}
