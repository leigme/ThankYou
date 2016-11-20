package com.yhcloud.thankyou.bean;

import java.io.Serializable;

/**
 * Created by leig on 2016/11/20.
 */

public class SpreadBean implements Serializable {

    private String Id;
    private String SummaryPicUrl;
    private String SummaryPicLink;
    private String Title;
    private String Summary;
    private String Content;
    private String PromotionTag;
    private String PromotionType;
    private String Statue;
    private String CreateTime;
    private String UpdateTime;
    private String UserName;
    private String UserRealName;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSummaryPicUrl() {
        return SummaryPicUrl;
    }

    public void setSummaryPicUrl(String summaryPicUrl) {
        SummaryPicUrl = summaryPicUrl;
    }

    public String getSummaryPicLink() {
        return SummaryPicLink;
    }

    public void setSummaryPicLink(String summaryPicLink) {
        SummaryPicLink = summaryPicLink;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPromotionTag() {
        return PromotionTag;
    }

    public void setPromotionTag(String promotionTag) {
        PromotionTag = promotionTag;
    }

    public String getPromotionType() {
        return PromotionType;
    }

    public void setPromotionType(String promotionType) {
        PromotionType = promotionType;
    }

    public String getStatue() {
        return Statue;
    }

    public void setStatue(String statue) {
        Statue = statue;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserRealName() {
        return UserRealName;
    }

    public void setUserRealName(String userRealName) {
        UserRealName = userRealName;
    }
}
