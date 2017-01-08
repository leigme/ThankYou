package com.yhcloud.thankyou.module.schoolannouncement.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/8.
 */

public class SchoolAnnouncementBean implements Serializable {

    /**
     * Id : 510
     * Title : 测试15:57
     * Status :
     * CreateTime : 2016-11-03 15:57:40
     * UpdateTime : 2016-11-03 15:57:40
     * CreatedBy : 3233
     * ContentURL : /m17/SchoolNotice/NoticeDetail/noticeid/510
     */

    private String Id;
    private String Title;
    private String Status;
    private String CreateTime;
    private String UpdateTime;
    private String CreatedBy;
    private String ContentURL;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
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

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getContentURL() {
        return ContentURL;
    }

    public void setContentURL(String ContentURL) {
        this.ContentURL = ContentURL;
    }
}
