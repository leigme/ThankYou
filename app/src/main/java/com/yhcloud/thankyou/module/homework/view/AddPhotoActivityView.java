package com.yhcloud.thankyou.module.homework.view;

import com.yhcloud.thankyou.minterface.BaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public interface AddPhotoActivityView extends BaseActivityView {
    void init();
    void showList(ArrayList<String> list);
    String getContent();
    void showDialog();
}
