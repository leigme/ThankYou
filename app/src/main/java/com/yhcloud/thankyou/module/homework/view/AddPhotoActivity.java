package com.yhcloud.thankyou.module.homework.view;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.homework.adapter.AddPhotoListAdapter;
import com.yhcloud.thankyou.module.homework.manage.AddPhotoManage;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;

import java.util.ArrayList;

public class AddPhotoActivity extends AppCompatActivity implements IAddPhotoView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private CoordinatorLayout container;
    private LinearLayout llBack, llRight;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private EditText etContent;
    private RecyclerView rvPhotoList;
    private ProgressDialog mProgressDialog;
    private Dialog mDialog;
    //适配器
    private AddPhotoListAdapter apla;
    //管理器
    private AddPhotoManage mManage;
    private int REQUEST_CAMERA = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        mManage = new AddPhotoManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        container = (CoordinatorLayout) findViewById(R.id.container);
        etContent = (EditText) findViewById(R.id.et_content);
        rvPhotoList = (RecyclerView) findViewById(R.id.rv_photo_list);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        mManage.closePage();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

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
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {
        if (null == llRight) {
            llRight = (LinearLayout) findViewById(R.id.ll_header_right);
            llRight.setVisibility(View.VISIBLE);
            llRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.save();
                }
            });
            ivRight = (ImageView) findViewById(R.id.iv_header_right);
            ivRight.setVisibility(View.INVISIBLE);
            tvRight = (TextView) findViewById(R.id.tv_header_right);
        }
        tvRight.setText(title);
    }

    @Override
    public void showToastMsg(int msgId) {
        MyToast.showToast(this, msgId);
    }

    @Override
    public void showToastMsg(String msg) {
        MyToast.showToast(this, msg);
    }

    @Override
    public void init() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Tools.print(TAG, "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(container, R.string.loadcamera, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(AddPhotoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(AddPhotoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    public void showList(final ArrayList<String> list) {
        if (null == apla) {
            apla = new AddPhotoListAdapter(this, list);
            rvPhotoList.setLayoutManager(new GridLayoutManager(this, 3));
            apla.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goAddPhoto(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            }, new AddPhotoListAdapter.IDelOnClickListerner() {
                @Override
                public void delOnClickListerner(View view, int position) {
                    mManage.delPhoto(position);
                }
            });
            rvPhotoList.setAdapter(apla);
        } else {
            apla.refreshData(list);
        }
    }

    @Override
    public String getContent() {
        String content = etContent.getText().toString().trim();
        return content;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            Tools.print(TAG, "Received response for Camera permission request.");
        }
    }

    @Override
    public void showDialog() {
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.dialog_integral);
        TextView tvTitle = (TextView) mDialog.findViewById(R.id.tv_dialog_title);
        tvTitle.setText("");
        TextView tvMsg = (TextView) mDialog.findViewById(R.id.tv_dialog_msg);
        tvMsg.setText("提交之后将无法更改,是否确认提交本次作业？");
        Button send = (Button) mDialog.findViewById(R.id.btn_dialog_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManage.updateStudentHomework();
            }
        });
        Button cancel = (Button) mDialog.findViewById(R.id.btn_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        mManage.result(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        mManage.closePage();
    }
}
