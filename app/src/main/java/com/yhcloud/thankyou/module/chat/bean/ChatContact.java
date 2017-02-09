package com.yhcloud.thankyou.module.chat.bean;

/**
 * Created by leig on 2017/2/9.
 */

public class ChatContact {

    private String UserId;
    private String UserName;
    private String HeadImageURL;
    private String RealName;
    private String Remark;
    private String HXUserName;
    private String HxId;
    private String HXPwd;
    private boolean selected;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getHeadImageURL() {
        return HeadImageURL;
    }

    public void setHeadImageURL(String headImageURL) {
        HeadImageURL = headImageURL;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getHXUserName() {
        return HXUserName;
    }

    public void setHXUserName(String HXUserName) {
        this.HXUserName = HXUserName;
    }

    public String getHxId() {
        return HxId;
    }

    public void setHxId(String hxId) {
        HxId = hxId;
    }

    public String getHXPwd() {
        return HXPwd;
    }

    public void setHXPwd(String HXPwd) {
        this.HXPwd = HXPwd;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
