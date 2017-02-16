package com.yhcloud.thankyou.module.todayrecipes.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.module.todayrecipes.manage.TodayRecipesInfoManage;

public class TodayRecipesInfoActivity extends ABaseActivity implements ITodayRecipesInfoView {
    //视图控件
    private LinearLayout llBack;
    private TextView tvTitle, tvContent;
    private ImageView ivImage;
    //管理器
    private TodayRecipesInfoManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recipes_info);
        mManage = new TodayRecipesInfoManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_todayrecipesinfo_back);
        ivImage = (ImageView) findViewById(R.id.iv_todayrecipesinfo_image);
        tvTitle = (TextView) findViewById(R.id.tv_todayrecipesinfo_title);
        tvContent = (TextView) findViewById(R.id.tv_todayrecipesinfo_content);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ll_todayrecipesinfo_back:
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
    public void setImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(ivImage);
    }

    @Override
    public void setDishInfo(String dishInfo) {
        tvContent.setText(dishInfo);
    }
}
