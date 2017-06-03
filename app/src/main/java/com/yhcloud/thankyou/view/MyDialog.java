package com.yhcloud.thankyou.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.SubmitCallBack;

/**
 * @author leig
 * @version 20170301
 */

public class MyDialog extends Dialog implements View.OnClickListener {

    private String mTitle, mContent;
    private TextView tvTitle, tvContent;
    private LinearLayout llSubmit;
    private Button btnSubmit, btnCancel;
    private int mLayouId;
    private Context mContext;
    private SubmitCallBack mSubmitCallBack;

    public MyDialog(Context context) {
        super(context, R.style.MyDialog);
        mLayouId = R.layout.base_dialog;
        mContext = context;
    }

    public MyDialog(Context context, int theme){
        super(context, theme);
        mContext = context;
    }

    public MyDialog(Context context, int theme, int layouId){
        super(context, theme);
        mLayouId = layouId;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(mLayouId);
        initViews();
    }

    private void initViews() {
        if (null != mTitle && !"".equals(mTitle)) {
            tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        }
        if (null != mContent && !"".equals(mContent)) {
            tvContent = (TextView) findViewById(R.id.tv_dialog_msg);
            tvContent.setText(mContent);
        }
        if (null != mSubmitCallBack) {
            llSubmit = (LinearLayout) findViewById(R.id.ll_dialog_submit);
            llSubmit.setVisibility(View.VISIBLE);
            btnSubmit = (Button) findViewById(R.id.btn_dialog_submit);
            btnSubmit.setOnClickListener(this);
        }
        btnCancel = (Button) findViewById(R.id.btn_dialog_cancel);
        btnCancel.setOnClickListener(this);
    }

    public MyDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public MyDialog setContent(String content) {
        mContent = content;
        return this;
    }

    public MyDialog setSubmitCallBack(SubmitCallBack submitCallBack) {
        mSubmitCallBack = submitCallBack;
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_submit:
                if (null != mSubmitCallBack) {
                    mSubmitCallBack.btnOnClick();
                }
                break;
            case R.id.btn_dialog_cancel:
                dismiss();
                break;
        }
    }
}
