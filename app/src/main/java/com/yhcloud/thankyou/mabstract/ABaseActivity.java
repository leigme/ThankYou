package com.yhcloud.thankyou.mabstract;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.minterface.IBaseView;
import com.yhcloud.thankyou.minterface.IButtonOnClickListener;
import com.yhcloud.thankyou.utils.myview.MyToast;

/**
 * Created by Administrator on 2017/1/21.
 */

public abstract class ABaseActivity extends AppCompatActivity implements IBaseView {

    private ProgressDialog mProgressDialog;
    private Dialog mDialog;
    private IButtonOnClickListener mIButtonOnClickListener;

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
                mIButtonOnClickListener.btnOnClick();
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
        dCancel.setText("我知道了");
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

    public void setIButtonOnClickListener(IButtonOnClickListener IButtonOnClickListener) {
        this.mIButtonOnClickListener = IButtonOnClickListener;
    }
}
