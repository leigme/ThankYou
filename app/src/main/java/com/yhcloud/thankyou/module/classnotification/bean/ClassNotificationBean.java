package com.yhcloud.thankyou.module.classnotification.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ClassNotificationBean implements Serializable {

    private String Id;
    private String NoticeId;
    private String Title;
    private String CreateTime;
    private String Content;
    private String Url;
    private String ClassId;
    private String PublishUserId;
    private String PublishUserName;
    private String IsRead;//0:未读, 1:已读, 2:删除
    private String UserId;
    private String UpdateTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(String noticeId) {
        NoticeId = noticeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getPublishUserId() {
        return PublishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        PublishUserId = publishUserId;
    }

    public String getPublishUserName() {
        return PublishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        PublishUserName = publishUserName;
    }

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }
}
