package com.yhcloud.thankyou.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.manage.EaseChatManage;

import java.util.ArrayList;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public class EaseChatActivity extends AppCompatActivity implements IEaseChatView {

    private EaseChatManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ease_chat);
        mManage = new EaseChatManage(this);
    }

    @Override
    public void initEaseChatUserInfo(ArrayList<UserInfoBean> list) {
        //get easeui instance
        EaseUI easeUI = EaseUI.getInstance();
        for (final UserInfoBean userInfoBean: list) {
            easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
                @Override
                public EaseUser getUser(String username) {
                    EaseUser easeUser = getUserInfo(userInfoBean.getHXUserName());
                    easeUser.setAvatar(userInfoBean.getHeadImageURL());
                    easeUser.setNickname(userInfoBean.getRealName());
                    return easeUser;
                }
            });
        }
    }

    @Override
    public void showEaseChat(EaseChatManage.ChatType chatType, String HXUserName) {
        EaseChatFragment chatFragment = new EaseChatFragment();
        Bundle args = new Bundle();
        switch (chatType) {
            case SINGLE:
                args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                break;
            case GROUP:
                args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                break;
        }
        args.putString(EaseConstant.EXTRA_USER_ID, HXUserName);
        chatFragment.setArguments(args);
//        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, chatFragment).commit();
    }
}
