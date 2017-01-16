package com.yhcloud.thankyou.module.homework.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/11.
 */

public class TeacherHomeworkBean implements Serializable {

    /**
     * HomeworkId : 469
     * ChapterId : 1001
     * CourseId : 1001
     * PrepBookId : 4616
     * VersionId : 1001
     * Title : 入学教育
     * Status : 2
     * ReceiveNum : 1
     * url : http://www.k12chn.com/m07/m0750P07/m0750P07001/homeworkId/469/UserId/3237
     * Subject : 语文
     * UserId : 3237
     * SetDevice : 0 [2:移动端]
     * CreateTime : 2017-01-06 11:59:43
     * ClassName : 三年级2班
     */

    private String HomeworkId;
    private String ChapterId;
    private String CourseId;
    private String PrepBookId;
    private String VersionId;
    private String Title;
    private String Status;
    private String ReceiveNum;
    private String url;
    private String Subject;
    private String UserId;
    private String SetDevice;
    private String CreateTime;
    private String ClassName;

    public String getHomeworkId() {
        return HomeworkId;
    }

    public void setHomeworkId(String HomeworkId) {
        this.HomeworkId = HomeworkId;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String ChapterId) {
        this.ChapterId = ChapterId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String CourseId) {
        this.CourseId = CourseId;
    }

    public String getPrepBookId() {
        return PrepBookId;
    }

    public void setPrepBookId(String PrepBookId) {
        this.PrepBookId = PrepBookId;
    }

    public String getVersionId() {
        return VersionId;
    }

    public void setVersionId(String VersionId) {
        this.VersionId = VersionId;
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

    public String getReceiveNum() {
        return ReceiveNum;
    }

    public void setReceiveNum(String ReceiveNum) {
        this.ReceiveNum = ReceiveNum;
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

    public String getSetDevice() {
        return SetDevice;
    }

    public void setSetDevice(String SetDevice) {
        this.SetDevice = SetDevice;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }
}

