package com.yhcloud.thankyou.utils.myview.drag;

import android.view.View;

/**
 * Author: leig
 * Date: 2016/7/4 16:00
 * Description: 说明
 * PackageName: com.yhcloud.thankyou.utils.myview.drag
 * Copyright: 阳环科技实业有限公司
 **/
public interface RecycleCallBack {
    //item的点击事件
    void itemOnClick(int position, View view);

    void itemOnLongClick(int position, View view);

    void onMove(int from, int to);

    void saveFunctionList();
}
