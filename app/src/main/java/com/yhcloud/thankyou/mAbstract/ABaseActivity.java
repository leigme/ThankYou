package com.yhcloud.thankyou.mAbstract;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IBaseView;
import com.yhcloud.thankyou.mInterface.IButtonOnClickListener;

/**
 * Created by Administrator on 2017/1/21.
 */

public abstract class ABaseActivity extends AppCompatActivity implements IBaseView {

    private ProgressDialog mProgressDialog;
    private Dialog mDialog;
    private IButtonOnClickListener mIButtonOnClickListener;

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

    public void showDialog(String title, String msg) {
        if (null != mDialog) {
            mDialog.dismiss();
        }
        mDialog = new Dialog(this, R.style.MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_integral, null);
        TextView dTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        dTitle.setText(title);
        TextView dMsg = (TextView) view.findViewById(R.id.tv_dialog_msg);
        dMsg.setText(msg);
        Button dCancel = (Button) view.findViewById(R.id.btn_dialog_cancel);
        dCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        Button dSubmit = (Button) view.findViewById(R.id.btn_dialog_submit);
        dSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIButtonOnClickListener.btnOnClick();
            }
        });
    }

    public void setIButtonOnClickListener(IButtonOnClickListener IButtonOnClickListener) {
        this.mIButtonOnClickListener = IButtonOnClickListener;
    }

    public void showDialog(String msg) {
        this.showDialog("", msg);
    }

}
