package com.yhcloud.thankyou.module.homework.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.homework.adapter.AddPhotoListAdapter;
import com.yhcloud.thankyou.module.homework.manage.AddPhotoManage;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

public class AddPhotoActivity extends BaseActivity implements AddPhotoActivityView {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private CoordinatorLayout container;
    private LinearLayout llBack, llRight;
    private TextView tvTitle, tvRight;
    private ImageView ivRight;
    private EditText etContent;
    private RecyclerView rvPhotoList;
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
    public void init() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Tools.print(TAG, "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(container, R.string.load_camera, Snackbar.LENGTH_INDEFINITE)
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
            apla.setIOnClickListener(new ItemClinkListener() {
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
//        super.showDialog("", "提交之后将无法更改,是否确认提交本次作业？");
//        this.setSubmitCallBack(new SubmitCallBack() {
//            @Override
//            public void btnOnClick() {
//                mManage.updateStudentHomework();
//            }
//        });
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void processClick(View view) {

    }
}
