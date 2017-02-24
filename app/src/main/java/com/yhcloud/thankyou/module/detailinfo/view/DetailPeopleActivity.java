package com.yhcloud.thankyou.module.detailinfo.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.RelativeInfoBean;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.mInterfacea.IOnClickListener;
import com.yhcloud.thankyou.module.detailinfo.adapter.DetailPeopleListAdapter;
import com.yhcloud.thankyou.module.detailinfo.manage.DetailPeopleManage;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

public class DetailPeopleActivity extends ABaseActivity implements IDetailPeopleActivityView {

    //视图控件
    private LinearLayout llBack, llNameTitle, llOfficeTitle, llSendMessage, llSentProps;
    private ImageView ivHeader;
    private TextView tvName, tvNameTitle, tvDefault, tvOfficeName, tvRemarkName, tvEdit;
    private RecyclerView rvPeopleList;
    private EditText etRemarkName;
    //适配器
    private DetailPeopleListAdapter dpla;
    //管理器
    private DetailPeopleManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_people);
        mManage = new DetailPeopleManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_detail_people_back);
        ivHeader = (ImageView) findViewById(R.id.iv_detail_people_header_image);
        llNameTitle = (LinearLayout) findViewById(R.id.ll_detail_title);
        llOfficeTitle = (LinearLayout) findViewById(R.id.ll_detail_office);
        tvName = (TextView) findViewById(R.id.tv_detail_realname);
        tvNameTitle = (TextView) findViewById(R.id.tv_detail_name_title);
        tvDefault = (TextView) findViewById(R.id.tv_detail_no_title);
        tvOfficeName = (TextView) findViewById(R.id.tv_detail_office_name);
        rvPeopleList = (RecyclerView) findViewById(R.id.rv_detail_people_list);
        tvRemarkName = (TextView) findViewById(R.id.tv_detail_remark_name);
        etRemarkName = (EditText) findViewById(R.id.et_detail_remark_name);
        tvEdit = (TextView) findViewById(R.id.tv_detail_edit);
        llSendMessage = (LinearLayout) findViewById(R.id.ll_detail_send_massage);
        llSentProps = (LinearLayout) findViewById(R.id.ll_detail_sent_props);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_detail_people_back:
                        finish();
                        break;
                    case R.id.iv_detail_people_header_image:
                        mManage.goBigImage();
                        break;
                    case R.id.tv_detail_edit:
                        mManage.editReMark();
                        break;
                    case R.id.ll_detail_send_massage:
                        mManage.goStudentChat();
                        break;
                    case R.id.ll_detail_sent_props:
                        mManage.sentProps();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        ivHeader.setOnClickListener(myOnClickListener);
        tvEdit.setOnClickListener(myOnClickListener);
        llSendMessage.setOnClickListener(myOnClickListener);
        llSentProps.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public void initView(int flag) {
        switch (flag) {
            case 1004:
                llNameTitle.setVisibility(View.GONE);
                break;
            case 1010:
                llNameTitle.setVisibility(View.GONE);
                break;
            case 1012:
                llOfficeTitle.setVisibility(View.GONE);
                break;
        }
        setNameTitle(flag);
    }

    @Override
    public void setNameTitle(int flag) {
        switch (flag) {
            case 1011:
                tvNameTitle.setText("家长姓名: ");
                break;
            case 1012:
                tvNameTitle.setText("孩子姓名: ");
                break;
        }
    }

    @Override
    public void showList(ArrayList<RelativeInfoBean> list) {
        rvPeopleList.setVisibility(View.VISIBLE);
        tvDefault.setVisibility(View.GONE);
        if (null == dpla) {
            dpla = new DetailPeopleListAdapter(this, list);
            dpla.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goParentChat(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvPeopleList.setLayoutManager(new LinearLayoutManager(this));
//            rvPeopleList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            rvPeopleList.setAdapter(dpla);
        } else {
            dpla.refreshData(list);
        }
    }

    @Override
    public void setHeadImage(String url) {
        Tools.GlideCircleImageUrl(this, url, ivHeader);
    }

    @Override
    public void setName(String name) {
        tvName.setText(name);
    }

    @Override
    public void setOffice(String officeName) {
        tvOfficeName.setText(officeName);
    }

    @Override
    public void setReMark(String reMark) {
        if (null != reMark && !"".equals(reMark)) {
            tvRemarkName.setText(reMark);
        } else {
            tvRemarkName.setText("无");
        }
    }

    @Override
    public void setEdit(boolean edited) {
        if (edited) {
            tvEdit.setText("保存");
            tvRemarkName.setVisibility(View.INVISIBLE);
            etRemarkName.setVisibility(View.VISIBLE);
            etRemarkName.setEnabled(true);
            etRemarkName.setHint("最多输入10个字符");
            //弹出键盘
            etRemarkName.setFocusableInTouchMode(true);
            etRemarkName.requestFocus();
            InputMethodManager imm = (InputMethodManager)etRemarkName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etRemarkName, 0);
        } else {
            tvEdit.setText("编辑");
            tvRemarkName.setVisibility(View.VISIBLE);
            etRemarkName.setVisibility(View.INVISIBLE);
        }
    }
}
