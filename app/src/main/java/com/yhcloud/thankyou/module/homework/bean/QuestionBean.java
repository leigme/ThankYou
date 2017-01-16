package com.yhcloud.thankyou.module.homework.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/12.
 */

public class QuestionBean implements Serializable {

    private String questionTitle;
    private boolean status;

    public QuestionBean() {}

    public QuestionBean(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public QuestionBean(String questionTitle, boolean status) {
        this.questionTitle = questionTitle;
        this.status = status;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
