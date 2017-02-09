package com.yhcloud.thankyou.module.chat.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mAbstract.ABaseActivity;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.chat.adapter.AddChatAdapter;
import com.yhcloud.thankyou.module.chat.bean.ChatContact;
import com.yhcloud.thankyou.module.chat.manage.AddChatManage;

import java.util.ArrayList;

public class AddChatActivity extends ABaseActivity implements IAddChatView {

    //视图控件
    private LinearLayout llBack, llRight;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private RecyclerView rvAddChatList;
    //适配器
    private AddChatAdapter aca;
    //管理器
    private AddChatManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        mManage = new AddChatManage(this);
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
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ll_header_left:
                        finish();
                        break;
                    case R.id.ll_header_right:
                        mManage.addChat();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llRight.setOnClickListener(myOnClickListener);
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
        }
        if (null == ivRight) {
            ivRight = (ImageView) findViewById(R.id.iv_header_right);
            ivRight.setImageResource(View.INVISIBLE);
        }
        if (null == tvRight) {
            tvRight = (TextView) findViewById(R.id.tv_header_right);
        }
        tvRight.setText(title);
    }

    @Override
    public void showList(ArrayList<ChatContact> list) {
        if (null == aca) {
            aca = new AddChatAdapter(this, list);
            aca.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {

                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvAddChatList.setLayoutManager(new LinearLayoutManager(this));
            rvAddChatList.setAdapter(aca);
        } else {

        }
    }
}