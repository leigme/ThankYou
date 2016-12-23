package com.yhcloud.thankyou.utils;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.view.EaseChatActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016/11/10.
 */

public class Tools {

    public static void GlideCircleImageUrl(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(Constant.SERVICEADDRESS + path)
                .bitmapTransform(new CropCircleTransformation(context))
                .error(R.mipmap.icon_big_404)
                .into(imageView);
    }

    public static void GlideImageUrl(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(Constant.SERVICEADDRESS + path)
                .error(R.mipmap.icon_big_404)
                .into(imageView);
    }

    public static ArrayList<FunctionBean> initFunction(Context context) {
        ArrayList<FunctionBean> arrayList = new ArrayList<>();
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_all, "全部应用", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的资料", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的朋友", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的鲜花", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_message, "我的消息", new Intent(context, EaseChatActivity.class)));
        arrayList.add(new FunctionBean(0, 0, "我的账户", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "每日签到", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_download, "我的下载", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "关于我们", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "切换账户", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_announcement, "学校公告", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_notice, "班级通知", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_resources, "教学资源", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_schedule, "工作日程", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_homework, "课后作业", new Intent()));
        arrayList.add(new FunctionBean(0, R.mipmap.icon_function_exercise, "课堂练习", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "班干部", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "值日生", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "班级课表", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "本班老师", new Intent()));
        return arrayList;
    }
}
