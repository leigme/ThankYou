package com.yhcloud.thankyou.bean;

import java.io.Serializable;

/**
 * Created by leig on 2016/12/1.
 */

public class UserInfoBean implements Serializable {

    /**
     * UserId : 3237
     * UserName : liuyisha
     * HeadImageURL : /uploads/face/HeadImg20160922/faces201609221602222328.jpg
     * RealName : 刘一莎
     * Sex : 女
     * updateTime : 2016-11-28 09:09:59
     * Birthday : 1970-01-08
     * Remark :
     * ClassRoleId :
     * Coin : 97
     * HXUserName : 3237
     * HXPwd : 666666
     * UserStatue :
     * Flowers : 26
     * BanUser : 0
     * UserRoleId : 1010
     * DefaultClassId : 161219
     * SchoolName : 天鸿小学
     * SchoolId : 12
     * FirendCircleId : 0
     */

    private String UserId;
    private String UserName;
    private String HeadImageURL;
    private String RealName;
    private String Sex;
    private String updateTime;
    private String Birthday;
    private String Remark;
    private String ClassRoleId;
    private String Coin;
    private String HXUserName;
    private String HXPwd;
    private String UserStatue;
    private String Flowers;
    private int BanUser;
    private int UserRoleId;
    private String DefaultClassId;
    private String SchoolName;
    private String SchoolId;
    private int FirendCircleId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getHeadImageURL() {
        return HeadImageURL;
    }

    public void setHeadImageURL(String HeadImageURL) {
        this.HeadImageURL = HeadImageURL;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getClassRoleId() {
        return ClassRoleId;
    }

    public void setClassRoleId(String ClassRoleId) {
        this.ClassRoleId = ClassRoleId;
    }

    public String getCoin() {
        return Coin;
    }

    public void setCoin(String Coin) {
        this.Coin = Coin;
    }

    public String getHXUserName() {
        return HXUserName;
    }

    public void setHXUserName(String HXUserName) {
        this.HXUserName = HXUserName;
    }

    public String getHXPwd() {
        return HXPwd;
    }

    public void setHXPwd(String HXPwd) {
        this.HXPwd = HXPwd;
    }

    public String getUserStatue() {
        return UserStatue;
    }

    public void setUserStatue(String UserStatue) {
        this.UserStatue = UserStatue;
    }

    public String getFlowers() {
        return Flowers;
    }

    public void setFlowers(String Flowers) {
        this.Flowers = Flowers;
    }

    public int getBanUser() {
        return BanUser;
    }

    public void setBanUser(int BanUser) {
        this.BanUser = BanUser;
    }

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int UserRoleId) {
        this.UserRoleId = UserRoleId;
    }

    public String getDefaultClassId() {
        return DefaultClassId;
    }

    public void setDefaultClassId(String DefaultClassId) {
        this.DefaultClassId = DefaultClassId;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String SchoolId) {
        this.SchoolId = SchoolId;
    }

    public int getFirendCircleId() {
        return FirendCircleId;
    }

    public void setFirendCircleId(int FirendCircleId) {
        this.FirendCircleId = FirendCircleId;
    }
}
