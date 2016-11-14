package com.yhcloud.thankyou.utils.myview;

import android.content.Context;
import android.widget.Toast;

import com.yhcloud.thankyou.R;

/**
 * Created by Administrator on 2016/11/14.
 */

public class MyToast {

    private static Toast toast;

    public static void showToast(Context mContext) {
        if (toast == null) {
            toast = Toast.makeText(mContext, R.string.no_more_data, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(mContext, R.string.no_more_data, Toast.LENGTH_SHORT);
        }
        toast.show();//重新显示吐司
    }

    public static void showToast(Context mContext, int resId) {
        if (toast == null) {
            toast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        }
        toast.show();//重新显示吐司
    }

    public static void showToast(Context mContext, String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        }
        toast.show();//重新显示吐司
    }
}
