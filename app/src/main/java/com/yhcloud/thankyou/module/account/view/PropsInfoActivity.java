package com.yhcloud.thankyou.module.account.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.module.account.bean.AccountPropBean;
import com.yhcloud.thankyou.module.account.manage.AccountPropsInfoManage;
import com.yhcloud.thankyou.utils.myview.MyToast;


import java.text.MessageFormat;

public class PropsInfoActivity extends AppCompatActivity implements IPropsInfoView {

    private LinearLayout llBack;
    private TextView tvTitle, tvPropsName, tvPropsDetails, tvPropsCoin, tvPropsPeopleNum,
            tvPropsBuyNum, tvPropsSumnum;
    private ImageView ivPropsInfoImage;
    private LinearLayout llAddPropsNum, llSubPropsNum, llBuyProps;
    private ProgressDialog mProgressDialog;
    private Dialog mDialog;

    private AccountPropsInfoManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_props_info);
        mManage = new AccountPropsInfoManage(this);
    }

    @Override
    public void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_header_left);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        ivPropsInfoImage = (ImageView) findViewById(R.id.iv_props_info_bg);
        tvPropsName = (TextView) findViewById(R.id.tv_props_info_name);
        tvPropsDetails = (TextView) findViewById(R.id.tv_props_info_detail);
        tvPropsCoin = (TextView) findViewById(R.id.tv_props_info_coin);
        tvPropsPeopleNum = (TextView) findViewById(R.id.tv_props_info_buypeoplenum);
        tvPropsBuyNum = (TextView) findViewById(R.id.tv_props_info_buynum);
        tvPropsSumnum = (TextView) findViewById(R.id.tv_props_info_sumnum);
        llAddPropsNum = (LinearLayout) findViewById(R.id.ll_props_info_plus);
        llSubPropsNum = (LinearLayout) findViewById(R.id.ll_props_info_less);
        llBuyProps = (LinearLayout) findViewById(R.id.ll_props_info_buy);
    }

    @Override
    public void initData(AccountPropBean accountPropBean) {
        Glide.with(this)
                .load(accountPropBean.getPropImg())
                .placeholder(R.mipmap.icon_big_404)
                .error(R.mipmap.icon_big_404)
                .into(ivPropsInfoImage);
        tvPropsName.setText(accountPropBean.getPropName());
        tvPropsDetails.setText(accountPropBean.getDescription());
        tvPropsCoin.setText(accountPropBean.getPropPrice());
        tvPropsSumnum.setText(accountPropBean.getPropPrice());
        tvPropsBuyNum.setText("1");
        tvPropsPeopleNum.setText(String.valueOf(accountPropBean.getSaleNum()));
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
                    case R.id.ll_props_info_plus:
                        mManage.addNum();
                        break;
                    case R.id.ll_props_info_less:
                        mManage.subNum();
                        break;
                    case R.id.ll_props_info_buy:
                        mManage.showDialog();
                        break;
                }
            }
        };
        llBack.setOnClickListener(myOnClickListener);
        llAddPropsNum.setOnClickListener(myOnClickListener);
        llSubPropsNum.setOnClickListener(myOnClickListener);
        llBuyProps.setOnClickListener(myOnClickListener);
    }

    @Override
    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    @Override
    public void showLoading(int msgId) {
        mProgressDialog = ProgressDialog.show(this, null, getString(msgId));
    }

    @Override
    public void showMsg(int msgId) {
        MyToast.showToast(this, msgId);
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setPeopleNum(int peopleNum) {
        tvPropsPeopleNum.setText(String.valueOf(peopleNum));
    }

    @Override
    public void showDialog(int coin, String msg, final boolean canBuy) {
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.dialog_integral);
        TextView dialogTitle = (TextView) mDialog.findViewById(R.id.tv_dialog_title);
        TextView dialogMsg = (TextView) mDialog.findViewById(R.id.tv_dialog_msg);
        Button dialogSubmit = (Button) mDialog.findViewById(R.id.btn_dialog_send);
        Button dialogCancel = (Button) mDialog.findViewById(R.id.btn_dialog_cancel);
        String str;
        if (canBuy) {
            dialogTitle.setText("确认购买");
            str = MessageFormat.format("购买这个道具会花费您{0}点积分,确认购买吗?", coin);
            dialogSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.buyProps();
                }
            });
        } else {
            dialogTitle.setText("兑换优点");
            str = msg;
            dialogSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mManage.goIntegral();
                }
            });
        }
        dialogMsg.setText(str);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    public int getBuynum() {
        int num = Integer.parseInt(tvPropsBuyNum.getText().toString().trim());
        return num;
    }

    @Override
    public void setBuynum(String num) {
        tvPropsBuyNum.setText(num);
    }

    @Override
    public void setSumnum(String sumCoin) {
        tvPropsSumnum.setText(sumCoin);
    }

    @Override
    public void showResult(final String title, String msg) {
        mDialog = new Dialog(this, R.style.MyDialog);
        mDialog.setContentView(R.layout.dialog_integral);
        TextView dialogTitle = (TextView) mDialog.findViewById(R.id.tv_dialog_title);
        TextView dialogMsg = (TextView) mDialog.findViewById(R.id.tv_dialog_msg);
        dialogTitle.setText(title);
        dialogMsg.setText(msg);
        LinearLayout llCancel = (LinearLayout) mDialog.findViewById(R.id.ll_integral_cancel);
        Button dialogSubmit = (Button) mDialog.findViewById(R.id.btn_dialog_send);
        switch (title) {
            case "购买成功":
                llCancel.setVisibility(View.GONE);
                dialogSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("购买成功".equals(title)) {
                            closePage();
                        }
                        mDialog.dismiss();
                    }
                });
                break;
            case "购买失败":
                llCancel.setVisibility(View.GONE);
                dialogSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                break;
            default:
                llCancel.setVisibility(View.GONE);
        }
        mDialog.show();
    }

    @Override
    public void closePage() {
        this.finish();
    }
}
