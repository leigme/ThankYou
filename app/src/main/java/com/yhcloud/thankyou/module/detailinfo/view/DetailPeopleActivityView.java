package com.yhcloud.thankyou.module.detailinfo.view;

import com.yhcloud.thankyou.bean.RelativeInfoBean;
import com.yhcloud.thankyou.minterface.BaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public interface DetailPeopleActivityView extends BaseActivityView {
    void initView(int flag);
    void setNameTitle(int flag);
    void showList(ArrayList<RelativeInfoBean> list);
    void setHeadImage(String url);
    void setName(String name);
    void setOffice(String officeName);
    void setReMark(String reMark);
    void setEdit(boolean edited);
}
