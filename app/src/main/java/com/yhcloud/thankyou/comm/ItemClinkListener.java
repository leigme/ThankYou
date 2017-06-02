package com.yhcloud.thankyou.comm;

import android.view.View;

/**
 * Created by leig on 2016/11/19.
 */

public interface ItemClinkListener {

    /**
     * 列表页点击监听事件
     * @author leig
     * @version 20170301
     */
    void OnItemClickListener(View view, int position);

    /**
     * 列表页长按监听事件
     * @author leig
     * @version 20170301
     */
    void OnItemLongClickListener(View view, int position);
}
