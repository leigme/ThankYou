package com.yhcloud.thankyou.module.chat.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstractd.ABaseActivity;
import com.yhcloud.thankyou.module.chat.manage.MessageManage;
import com.yhcloud.thankyou.utils.Tools;

/**
 * 消息列表
 * */

public class MessageActivity extends ABaseActivity implements IMessageView{

    private String TAG = getClass().getSimpleName();
    //视图控件
    private LinearLayout llBack, llRight;
    private TextView tvTitle;
    //管理器
    private MessageManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mManage = new MessageManage(this);
    }

    @Override
    public void initMessageFragment() {
        Tools.print(TAG, "启动聊天界面...");
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
//                startActivity(new Intent(MessageActivity.this, EaseChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
                mManage.goChatActivity(conversation);
            }
        });
//        conversationListFragment.setArguments(bundle);
//        conversationListFragment.hideTitleBar();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, conversationListFragment).commit();
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        llRight = (LinearLayout) findViewById(R.id.ll_header_right);
        llRight.setVisibility(View.VISIBLE);
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

    }
}
