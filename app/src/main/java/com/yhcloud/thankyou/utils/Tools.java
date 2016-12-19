package com.yhcloud.thankyou.utils;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.view.EaseChatActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public class Tools {

    public static ArrayList<FunctionBean> initFunction(Context context) {
        ArrayList<FunctionBean> arrayList = new ArrayList<>();
        arrayList.add(new FunctionBean(0, 0, "我的资料", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的朋友", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的鲜花", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的消息", new Intent(context, EaseChatActivity.class)));
        arrayList.add(new FunctionBean(0, 0, "我的账户", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "每日签到", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "我的下载", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "关于我们", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "切换账户", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "学校公告", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "班级通知", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "教学资源", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "工作日程", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "课后作业", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "课堂练习", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "班干部", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "值日生", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "班级课表", new Intent()));
        arrayList.add(new FunctionBean(0, 0, "本班老师", new Intent()));
        return arrayList;
    }
}
