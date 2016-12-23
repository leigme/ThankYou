package com.yhcloud.thankyou.module.curriculum.view;

import com.yhcloud.thankyou.mInterface.IBaseView;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public interface ICurriculumView extends IBaseView {
    void showList(ArrayList<CurriculumBean> list);
    void initViewStub(String msg);
}
