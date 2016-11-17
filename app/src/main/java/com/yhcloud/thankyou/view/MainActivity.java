package com.yhcloud.thankyou.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.yhcloud.thankyou.R;

import static com.yhcloud.thankyou.R.id.iv_header_left;

public class MainActivity extends AppCompatActivity {

    private ImageView ivHeaderLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        ivHeaderLeft = (ImageView) findViewById(iv_header_left);
    }

    private void initData(){
        ivHeaderLeft.setImageResource(R.mipmap.icon_default_avatar);
    }
}
