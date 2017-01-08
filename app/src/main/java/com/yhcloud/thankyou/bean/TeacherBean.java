package com.yhcloud.thankyou.bean;

/**
 * Created by Administrator on 2016/12/22.
 */

public class TeacherBean {

    private String HeadImageURL;
    private String UserId;
    private String RealName;
    private String Roles;
    private String PropsNum;

    public String getHeadImageURL() {
        return HeadImageURL;
    }

    public void setHeadImageURL(String headImageURL) {
        HeadImageURL = headImageURL;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String roles) {
        Roles = roles;
    }

    public String getPropsNum() {
        return PropsNum;
    }

    public void setPropsNum(String propsNum) {
        PropsNum = propsNum;
    }
}
