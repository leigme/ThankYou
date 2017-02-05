package com.yhcloud.thankyou.module.detailinfo.bean;

import com.yhcloud.thankyou.bean.UserRoleBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class DetailPeopleInfoBean implements Serializable {

    /**
     * RealName : 杨小箭爸爸~★@「◆」
     * HeadImage : /uploads/face/HeadImg20160926/faces201609261558307440.jpg
     * UserName : yangxiaojianbaba
     * Coin : 96
     * hxid : 15265
     * hxname : 3661
     * SchoolName : 天鸿小学
     * sex : 男
     * RoleName : 家长
     * friendRemark : 这家长不错
     * userRole : []
     */

    private String RealName;
    private String HeadImage;
    private String UserName;
    private String Coin;
    private String hxid;
    private String hxname;
    private String SchoolName;
    private String sex;
    private String RoleName;
    private String friendRemark;
    private ArrayList<UserRoleBean> userRole;

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getHeadImage() {
        return HeadImage;
    }

    public void setHeadImage(String HeadImage) {
        this.HeadImage = HeadImage;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getCoin() {
        return Coin;
    }

    public void setCoin(String Coin) {
        this.Coin = Coin;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getHxname() {
        return hxname;
    }

    public void setHxname(String hxname) {
        this.hxname = hxname;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public String getFriendRemark() {
        return friendRemark;
    }

    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    public ArrayList<UserRoleBean> getUserRole() {
        return userRole;
    }

    public void setUserRole(ArrayList<UserRoleBean> userRole) {
        this.userRole = userRole;
    }
}
