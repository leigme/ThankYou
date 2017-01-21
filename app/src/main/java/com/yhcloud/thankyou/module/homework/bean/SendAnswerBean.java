package com.yhcloud.thankyou.module.homework.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/19.
 */

public class SendAnswerBean {

    private String homeworkId;
    private String homeworkTime;
    private ArrayList<AnswerBean> homeworkList;

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkTime() {
        return homeworkTime;
    }

    public void setHomeworkTime(String homeworkTime) {
        this.homeworkTime = homeworkTime;
    }

    public ArrayList<AnswerBean> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(ArrayList<AnswerBean> homeworkList) {
        this.homeworkList = homeworkList;
    }
}
