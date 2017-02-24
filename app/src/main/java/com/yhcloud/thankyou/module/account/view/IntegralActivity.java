package com.yhcloud.thankyou.module.account.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mabstract.ABaseActivity;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.account.adapter.AccountIntegralListAdapter;
import com.yhcloud.thankyou.module.account.bean.AccountIntegralBean;
import com.yhcloud.thankyou.module.account.manage.AccountIntegralManage;

import java.text.MessageFormat;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class IntegralActivity extends ABaseActivity implements IIntegralView {

    private LinearLayout llBack;
    private TextView tvTitle, tvRealname, tvUsername, tvCoin, tvRule;
    private ImageView ivHeaderImage;
    private RecyclerView rvList;
    private LinearLayout llSend;
    private Dialog mDialog;
    private AccountIntegralListAdapter aila;

    private AccountIntegralManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        mManage = new AccountIntegralManage(this);
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
        llSend = (LinearLayout) findViewById(R.id.ll_recharge_send);
        tvRule = (TextView) findViewById(R.id.tv_recharge_rule);
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
                        mManage.send();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llSend.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showList(final ArrayList<AccountIntegralBean> list) {
        if (null == aila) {
            aila = new AccountIntegralListAdapter(this, list);
            rvList.setLayoutManager(new GridLayoutManager(this, 3));
            aila.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    for (AccountIntegralBean bean: list) {
                        bean.setSelected(false);
                    }
                    list.get(position).setSelected(true);
                    aila.refreshData(list);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvList.setAdapter(aila);
        } else {
            aila.refreshData(list);
        }
    }

    @Override
    public void setHeadimg(String headimg) {
        Glide.with(this)
                .load(headimg)
                .error(R.mipmap.icon_account_404)
                .placeholder(R.mipmap.icon_account_404)
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
    public void showDialog(boolean exchanged, String uCoin, String coin) {
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.dialog_integral);
        TextView tvMsg = (TextView) mDialog.findViewById(R.id.tv_dialog_msg);
        Button send = (Button) mDialog.findViewById(R.id.btn_dialog_submit);
        Button cancel = (Button) mDialog.findViewById(R.id.btn_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        if (exchanged) {
            tvMsg.setText(MessageFormat.format("您是否确定用{0}优点兑换{1}积分？", uCoin, coin));
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.getUserCoin();
                    mDialog.dismiss();
                }
            });
        } else {
            tvMsg.setText("您的优点余额不足,无法兑换此积分。是否前往优点充值页面去充值?");
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.goRecharge();
                    mDialog.dismiss();
                }
            });
        }
        mDialog.show();
    }

    @Override
    public void closeDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showDialogMsg() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_message);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        tvTitle.setText("恭喜您,积分兑换成功!");
        tvTitle.setTextSize(21);
        tvTitle.setTextColor(Color.parseColor("#ff4f00"));
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_dialog_msg);
        tvMsg.setGravity(Gravity.CENTER);
        tvMsg.setText("您现在可以去买道具啦!");
        tvMsg.setTextSize(12);
        tvMsg.setTextColor(Color.parseColor("#666666"));
        Button btnOk = (Button) dialog.findViewById(R.id.btn_dialog_submit);
        btnOk.setText("我知道啦");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
