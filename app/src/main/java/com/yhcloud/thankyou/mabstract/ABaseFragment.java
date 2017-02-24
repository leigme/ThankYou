package com.yhcloud.thankyou.mabstract;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IBaseFragmentView;
import com.yhcloud.thankyou.mInterface.IButtonOnClickListener;
import com.yhcloud.thankyou.utils.myview.MyToast;

/**
 * Created by Administrator on 2017/1/22.
 */

public abstract class ABaseFragment extends Fragment implements IBaseFragmentView {

    private ProgressDialog mProgressDialog;
    private Dialog mDialog;
    private IButtonOnClickListener mIButtonOnClickListener;

    @Override
    public void showLoading(int msgId) {
        hiddenLoading();
        mProgressDialog = ProgressDialog.show(getActivity(), null, getString(msgId));
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
        mDialog = new Dialog(getActivity(), R.style.MyDialog);
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
        mDialog = new Dialog(getActivity(), R.style.MyDialog);
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
        MyToast.showToast(getActivity(), msgId);
    }

    @Override
    public void showToastMsg(String msg) {
        MyToast.showToast(getActivity(), msg);
    }

    public void setIButtonOnClickListener(IButtonOnClickListener IButtonOnClickListener) {
        this.mIButtonOnClickListener = IButtonOnClickListener;
    }
}
