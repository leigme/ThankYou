package com.yhcloud.thankyou.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yhcloud.thankyou.R;

public class DetailPeopleActivity extends AppCompatActivity implements IDetailPeopleView {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_people);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

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
}
