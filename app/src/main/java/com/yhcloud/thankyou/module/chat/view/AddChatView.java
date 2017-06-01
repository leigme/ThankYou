package com.yhcloud.thankyou.module.chat.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;
import com.yhcloud.thankyou.module.chat.bean.ChatContact;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public interface AddChatView extends BaseActivityView {
    void showList(ArrayList<ChatContact> list);
}
