package com.yhcloud.thankyou.module.chat.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.module.chat.view.AddChatView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

/**
 * Created by leig on 2017/2/9.
 */

public class AddChatManage {

    private String TAG = getClass().getSimpleName();

    private AddChatView mIAddChatView;
    private Activity mActivity;
    private LogicService mService;

    public AddChatManage(AddChatView IAddChatView) {
        this.mIAddChatView = IAddChatView;
        this.mActivity = (Activity) mIAddChatView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mIAddChatView.initView();
                mIAddChatView.initEvent();
                mIAddChatView.setTitle("选择联系人");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void addChat() {
        Tools.print(TAG, "添加聊天咯~~");
    }
}
