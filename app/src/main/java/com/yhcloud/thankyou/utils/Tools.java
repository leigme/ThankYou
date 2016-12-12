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
        sparseArray.append(0, new FunctionBean(0, 0, 0, "我的资料", new Intent()));
        sparseArray.append(1, new FunctionBean(0, 0, 0, "我的朋友", new Intent()));
        sparseArray.append(2, new FunctionBean(0, 0, 0, "我的鲜花", new Intent()));
        sparseArray.append(3, new FunctionBean(0, 0, 0, "我的消息", new Intent()));
        sparseArray.append(4, new FunctionBean(0, 0, 0, "我的下载", new Intent()));
        sparseArray.append(5, new FunctionBean(0, 0, 0, "我的账户", new Intent()));
        sparseArray.append(6, new FunctionBean(0, 0, 0, "每日签到", new Intent()));
        sparseArray.append(7, new FunctionBean(0, 0, 0, "关于我们", new Intent()));
        sparseArray.append(8, new FunctionBean(0, 0, 0, "切换账户", new Intent()));
        return sparseArray;
    }
}
