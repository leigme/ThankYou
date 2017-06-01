package com.yhcloud.thankyou.comm;

import android.view.View;

/**
 * Created by leig on 2016/11/19.
 */

public interface ItemClinkListener {
    void OnItemClickListener(View view, int position);
    void OnItemLongClickListener(View view, int position);
}
