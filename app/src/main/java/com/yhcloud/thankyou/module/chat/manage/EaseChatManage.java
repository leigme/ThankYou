package com.yhcloud.thankyou.module.chat.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.hyphenate.easeui.EaseConstant;
import com.yhcloud.thankyou.module.chat.view.IEaseChatView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/20.
 */

public class EaseChatManage {

    private String TAG = getClass().getSimpleName();

    private IEaseChatView mIEaseChatView;
    private Activity mActivity;
    private LogicService mService;
    private String HxId;

    public EaseChatManage(IEaseChatView iEaseChatView) {
        this.mIEaseChatView = iEaseChatView;
        this.mActivity = (Activity) mIEaseChatView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIEaseChatView.initView();
                mIEaseChatView.initEvent();
                goEaseChat();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void goEaseChat() {
        Intent intent = mActivity.getIntent();
        if (null != intent) {
            HxId = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
            HashMap<String, String[]> map = mService.getMap();
            String[] userInfo = map.get(HxId);
            mIEaseChatView.setTitle(userInfo[1]);
            mIEaseChatView.showEaseChat(HxId);
        }
    }

    public void goDetailInfo() {
        Tools.print(TAG, "点击了用户头像...");
    }
}
