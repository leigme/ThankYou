package com.yhcloud.thankyou.module.detailinfo.view;

import com.yhcloud.thankyou.bean.RelativeInfoBean;
import com.yhcloud.thankyou.mInterface.IBaseActivityView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public interface IDetailPeopleActivityView extends IBaseActivityView {
    void initView(int flag);
    void setNameTitle(int flag);
    void showList(ArrayList<RelativeInfoBean> list);
    void setHeadImage(String url);
    void setName(String name);
    void setOffice(String officeName);
    void setReMark(String reMark);
    void setEdit(boolean edited);
}
