package com.yhcloud.thankyou.module.homework.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/11.
 */

public class StudentHomeworkBean implements Serializable {

    /**
     * WorkId : 16123
     * SendUser : 邹艳
     * Title : 入学教育
     * Status : 2
     * url : http://www.k12chn.com/m07/m0750P06/m0750P06001/homeworkId/459/userId/1729470
     * Subject : 语文
     * UserId : 1729470
     * CreateTime : 2016-12-22 14:58:35
     * HomeworkId : 459
     */

    private String WorkId;
    private String SendUser;
    private String Title;
    private String Status;
    private String url;
    private String Subject;
    private String UserId;
    private String CreateTime;
    private String HomeworkId;

    public String getWorkId() {
        return WorkId;
    }

    public void setWorkId(String WorkId) {
        this.WorkId = WorkId;
    }

    public String getSendUser() {
        return SendUser;
    }

    public void setSendUser(String SendUser) {
        this.SendUser = SendUser;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getHomeworkId() {
        return HomeworkId;
    }

    public void setHomeworkId(String HomeworkId) {
        this.HomeworkId = HomeworkId;
    }
}
