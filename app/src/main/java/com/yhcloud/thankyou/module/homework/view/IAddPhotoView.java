package com.yhcloud.thankyou.module.homework.view;

import android.app.Activity;

import com.yhcloud.thankyou.mInterface.IBaseView;
import com.yhcloud.thankyou.module.image.bean.PhotoBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public interface IAddPhotoView extends IBaseView {
    void init();
    void showList(ArrayList<String> list);
    String getContent();
    void showDialog();
}
