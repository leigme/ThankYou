package com.yhcloud.thankyou.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.manage.MessageManage;

public class MessageActivity extends AppCompatActivity implements IMessageView{

    private MessageManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mManage = new MessageManage(this);
    }

    @Override
    public void initMessageFragment() {
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MessageActivity.this, EaseChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName()));
            }
        });
//        conversationListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, conversationListFragment).commit();
    }
}
