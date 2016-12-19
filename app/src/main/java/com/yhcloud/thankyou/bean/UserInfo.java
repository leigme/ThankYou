package com.yhcloud.thankyou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/11.
 */

public class UserInfo implements Serializable, Parcelable {

    private int id;
    private int uid;
    private String username;
    private String password;
    private String key;
    private UserInfoBean userInfoBean;
    private ArrayList<ClassInfoBean> mClassInfoBeen;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    //添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>(){
        @Override
        public UserInfo createFromParcel(Parcel source) {//从Parcel中读取数据，返回UserInfo对象
            return new UserInfo();
        }
        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public ArrayList<ClassInfoBean> getClassInfoBeen() {
        return mClassInfoBeen;
    }

    public void setClassInfoBeen(ArrayList<ClassInfoBean> classInfoBeen) {
        this.mClassInfoBeen = classInfoBeen;
    }
}
