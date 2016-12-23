package com.yhcloud.thankyou.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.utils.CommonAdapter;
import com.yhcloud.thankyou.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/23.
 */

public class HomeSpreadAdapter extends CommonAdapter<SpreadBean> {

    public HomeSpreadAdapter(Context context, List<SpreadBean> datas) {
        super(context, datas, R.layout.item_spread);
    }

    @Override
    public void convert(ViewHolder holder, SpreadBean bean) {
        holder.setImageURL(R.id.id_iv_summarypic, bean.getSummaryPicLink())
                .setText(R.id.id_tv_title, bean.getTitle())
                .setText(R.id.id_tv_summary, bean.getSummary());
        ImageView statueIcon = holder.getView(R.id.id_iv_statueicon);
        if ("0".equals(bean.getStatue())) {
            statueIcon.setVisibility(View.VISIBLE);
        } else {
            statueIcon.setVisibility(View.INVISIBLE);
        }
    }
}
