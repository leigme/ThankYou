package com.yhcloud.thankyou.module.curriculum.view;

import com.yhcloud.thankyou.minterface.IBaseActivityView;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumItemBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public interface ICurriculumActivityView extends IBaseActivityView {
    void showList(ArrayList<CurriculumItemBean> list);
    void initViewStub(String msg);
}
