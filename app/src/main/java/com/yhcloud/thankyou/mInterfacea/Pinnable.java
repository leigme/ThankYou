package com.yhcloud.thankyou.mInterfacea;

/**
 * Created by leig on 2017/2/14.
 */

public interface Pinnable {
    //是否需要显示悬停title
    boolean isPanned();
    //悬停的tag
    String getPinnedTag();
    //悬停的info
    String getPinnedInfo();
    //悬停的color
    int getPinnedColor();
}
