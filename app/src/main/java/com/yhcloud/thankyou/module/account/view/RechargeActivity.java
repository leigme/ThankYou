package com.yhcloud.thankyou.module.account.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.comm.SubmitCallBack;
import com.yhcloud.thankyou.module.account.adapter.AccountRechargeListAdapter;
import com.yhcloud.thankyou.module.account.adapter.AccountRechargePayListAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountRechargeBean;
import com.yhcloud.thankyou.module.account.bean.AccountRechargePayBean;
import com.yhcloud.thankyou.module.account.manage.AccountRechargeManage;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class RechargeActivity extends BaseActivity implements RechargeView {

    private LinearLayout llBack;
    private TextView tvTitle, tvRealname, tvUsername, tvCoin, tvRule;
    private Dialog mDialog;
    private ImageView ivHeaderImage;
    private RecyclerView rvList;
    private LinearLayout llRechargeSend;
    private AccountRechargeListAdapter arla;
    private AccountRechargePayListAdapter arpla;
    private AccountRechargeManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        mManage = new AccountRechargeManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        ivHeaderImage = (ImageView) findViewById(R.id.iv_recharge_header_image);
        tvRealname = (TextView) findViewById(R.id.tv_recharge_realname);
        tvUsername = (TextView) findViewById(R.id.tv_recharge_username);
        tvCoin = (TextView) findViewById(R.id.tv_recharge_coin);
        rvList = (RecyclerView) findViewById(R.id.rv_recharge_list);
        llRechargeSend = (LinearLayout) findViewById(R.id.ll_recharge_send);
        tvRule = (TextView) findViewById(R.id.tv_recharge_rule);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_header_left:
                        finish();
                        break;
                    case R.id.ll_recharge_send:
                        mManage.showPayList();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llRechargeSend.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showRechargeList(final ArrayList<AccountRechargeBean> list) {
        if (null == arla) {
            arla = new AccountRechargeListAdapter(this, list);
            rvList.setLayoutManager(new GridLayoutManager(this, 3));
            arla.setIOnClickListener(new SubmitCallBack() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    for (AccountRechargeBean bean: list) {
                        bean.setSelected(false);
                    }
                    list.get(position).setSelected(true);
                    arla.refreshData(list);
                }
                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvList.setAdapter(arla);
        } else {
            arla.refreshData(list);
        }
    }

    @Override
    public void setHeadimg(String headimg) {
        Glide.with(this)
                .load(headimg)
                .placeholder(R.mipmap.icon_small_404)
                .error(R.mipmap.icon_small_404)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivHeaderImage);
    }

    @Override
    public void setRealname(String realname) {
        tvRealname.setText(realname);
    }

    @Override
    public void setUsername(String username) {
        tvUsername.setText(username);
    }

    @Override
    public void setCoin(String coin) {
        tvCoin.setText(coin);
    }

    @Override
    public void setRule(String rule) {
        tvRule.setText(rule);
    }

    @Override
    public void showDialog(final ArrayList<AccountRechargePayBean> list, String payNum) {
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.dialog_recharge);
        LinearLayout llClose = (LinearLayout) mDialog.findViewById(R.id.ll_dialog_recharge_close);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
            }
        });
        TextView tvPayNum = (TextView) mDialog.findViewById(R.id.tv_dialog_recharge_paynum);
        tvPayNum.setText(payNum);
        RecyclerView rvList = (RecyclerView) mDialog.findViewById(R.id.rv_dialog_recharge_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        if (null == arpla) {
            arpla = new AccountRechargePayListAdapter(this, list);
            arpla.setIOnClickListener(new SubmitCallBack() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    for (AccountRechargePayBean payBean: list) {
                        payBean.setSelected(false);
                    }
                    list.get(position).setSelected(true);
                    arpla.refreshData(list);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
        } else {
            arpla.refreshData(list);
        }
        rvList.setAdapter(arpla);
        LinearLayout llPayment = (LinearLayout) mDialog.findViewById(R.id.ll_dialog_recharge_payment);
        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mDialog) {
                    mManage.payMoney();
                }
            }
        });
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置Dialog距离底部的距离
        lp.y = 20;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mDialog.show();
    }

    @Override
    public void closeDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }
}
