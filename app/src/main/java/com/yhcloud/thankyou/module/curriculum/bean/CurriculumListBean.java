package com.yhcloud.thankyou.module.curriculum.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumListBean {

    private ArrayList<CouresBean> Coures;
    private DayIndexBean dayIndex;

    public ArrayList<CouresBean> getCoures() {
        return Coures;
    }

    public void setCoures(ArrayList<CouresBean> coures) {
        Coures = coures;
    }

    public DayIndexBean getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(DayIndexBean dayIndex) {
        this.dayIndex = dayIndex;
    }

}
