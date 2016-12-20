package com.yhcloud.thankyou.view;

import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.manage.EaseChatManage;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/19.
 */

public interface IEaseChatView {
    void initEaseChatUserInfo(ArrayList<UserInfoBean> list);
    void showEaseChat(EaseChatManage.ChatType chatType, String HXUserName);
}
