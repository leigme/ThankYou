package com.yhcloud.thankyou.comm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;

/**
 * Created by Administrator on 2017/1/21.
 */

public abstract class BaseActivity extends FragmentActivity implements BaseView, View.OnClickListener {

    private static String TAG = BaseActivity.class.getName();

    // 视图控件集合
    private SparseArray<View> mViews;
    // 加载等待框
    private ProgressDialog mProgressDialog;
    // 对话框
    private Dialog mDialog;
    // 对话框提交回调接口
    private SubmitCallBack mSubmitCallBack;
    // 基础服务
    private BaseService mBaseService;
    // 服务连接器
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.print(TAG, "开始跑程序咯1");
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        Tools.print(TAG, "开始跑程序咯2");
        initViews();
        initDatas();
        initEvents();
        Tools.print(TAG, "开始跑程序咯3");
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         * ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         * 设置为横屏
         * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
         */
        if (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE != getRequestedOrientation()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    public void bindBaseService(Class<BaseService> baseServiceClass, final BindServiceCallBack bindServiceCallBack) {
        Tools.print(TAG, "开始绑定服务咯。。。");
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                BaseService mBaseService = ((BaseService.BaseBinder)iBinder).getService();
                Tools.print(TAG, "开始回调绑定服务咯。。。");
                bindServiceCallBack.bindBaseServiceSuccess(mBaseService);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                bindServiceCallBack.bindBaseServiceFailure();
            }
        };
        Intent intent = new Intent(this, baseServiceClass);
        this.bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hiddenLoading();
        if (null != mServiceConnection) {
            unbindService(mServiceConnection);
        }
    }

    public <E extends View> E findView(int resId) {
        E view = (E) mViews.get(resId);
        if (null == view) {
            view = (E) findViewById(resId);
            mViews.put(resId, view);
        }
        return view;
    }

    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }


    @Override
    public void showLoading(int msgId) {
        hiddenLoading();
        mProgressDialog = ProgressDialog.show(this, null, getString(msgId));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showDialog(String title, String msg) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.base_dialog);
        TextView dTitle = (TextView) mDialog.findViewById(R.id.tv_dialog_title);
        dTitle.setText(title);
        TextView dMsg = (TextView) mDialog.findViewById(R.id.tv_dialog_msg);
        dMsg.setText(msg);
        Button dCancel = (Button) mDialog.findViewById(R.id.btn_dialog_cancel);
        dCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button dSubmit = (Button) mDialog.findViewById(R.id.btn_dialog_submit);
        dSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubmitCallBack.btnOnClick();
            }
        });
    }

    @Override
    public void showDialog(String msg) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.base_dialog);
        TextView dMsg = (TextView) mDialog.findViewById(R.id.tv_dialog_msg);
        dMsg.setText(msg);
        Button dCancel = (Button) mDialog.findViewById(R.id.btn_dialog_cancel);
        dCancel.setText(R.string.ok);
        dCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        LinearLayout llSubmit = (LinearLayout) mDialog.findViewById(R.id.ll_dialog_submit);
        llSubmit.setVisibility(View.GONE);
    }

    @Override
    public void showToastMsg(int msgId) {
        MyToast.showToast(this, msgId);
    }

    @Override
    public void showToastMsg(String msg) {
        MyToast.showToast(this, msg);
    }

    public void setSubmitCallBack(SubmitCallBack submitCallBack) {
        this.mSubmitCallBack = submitCallBack;
    }
}
