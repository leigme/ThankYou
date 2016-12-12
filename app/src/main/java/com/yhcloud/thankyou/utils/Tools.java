package com.yhcloud.thankyou.utils;

import android.content.Intent;
import android.util.SparseArray;

import com.yhcloud.thankyou.bean.FunctionBean;

/**
 * Created by Administrator on 2016/11/10.
 */

public class Tools {

    public static SparseArray<FunctionBean> initFunction() {
        SparseArray<FunctionBean> sparseArray = new SparseArray();
        sparseArray.append(0, new FunctionBean(0, 0, "我的资料", new Intent()));
        sparseArray.append(1, new FunctionBean(0, 0, "我的朋友", new Intent()));
        sparseArray.append(2, new FunctionBean(0, 0, "我的鲜花", new Intent()));
        sparseArray.append(3, new FunctionBean(0, 0, "我的消息", new Intent()));
        sparseArray.append(4, new FunctionBean(0, 0, "我的下载", new Intent()));
        sparseArray.append(5, new FunctionBean(0, 0, "我的账户", new Intent()));
        sparseArray.append(6, new FunctionBean(0, 0, "每日签到", new Intent()));
        sparseArray.append(7, new FunctionBean(0, 0, "关于我们", new Intent()));
        sparseArray.append(8, new FunctionBean(0, 0, "切换账户", new Intent()));
        sparseArray.append(9, new FunctionBean(0, 0, "我的账户", new Intent()));
        sparseArray.append(10, new FunctionBean(0, 0, "每日签到", new Intent()));
        sparseArray.append(11, new FunctionBean(0, 0, "我的下载", new Intent()));
        sparseArray.append(12, new FunctionBean(0, 0, "学校公告", new Intent()));
        sparseArray.append(13, new FunctionBean(0, 0, "班级通知", new Intent()));
        sparseArray.append(14, new FunctionBean(0, 0, "教学资源", new Intent()));
        sparseArray.append(15, new FunctionBean(0, 0, "工作日程", new Intent()));
        sparseArray.append(16, new FunctionBean(0, 0, "课后作业", new Intent()));
        sparseArray.append(17, new FunctionBean(0, 0, "课堂练习", new Intent()));
        sparseArray.append(18, new FunctionBean(0, 0, "班干部", new Intent()));
        sparseArray.append(19, new FunctionBean(0, 0, "值日生", new Intent()));
        sparseArray.append(20, new FunctionBean(0, 0, "课表", new Intent()));
        sparseArray.append(21, new FunctionBean(0, 0, "本班老师", new Intent()));
        return sparseArray;
    }
}
