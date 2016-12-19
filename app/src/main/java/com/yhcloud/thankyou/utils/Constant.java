package com.yhcloud.thankyou.utils;

/**
 * Created by leig on 2016/11/17.
 */

public class Constant {

    /**
     * 用户角色常量
     */
    public static final int ROLE_PRINCIPAL = 1004;
    public static final int ROLE_TEACHER = 1010;
    public static final int ROLE_STUDENT = 1011;
    public static final int ROLE_PARENT = 1012;

    /**
     * 存储选项
     */
    public static final String USER_INFO = "userInfo";
    public static final String USER_NAME = "userName";
    public static final String USER_PWD = "userPassword";
    public static final String USER_FLAG = "userFlag";
    public static final String USER_HXNAME = "userHxName";
    public static final String USER_HXPWD = "userHxPwd";
    public static final String USER_KEY = "userKey";
    public static final String USER_LOGINED = "userLogined";

    /**
     * 服务器API
     */
    //服务地址
    public static final String SERVICEADDRESS = "http://www.k12chn.com";
    //    public static final String SERVICEADDRESS = "http://192.168.0.19";
    //登录请求
    public static final String LOGIN = SERVICEADDRESS + "/m17/M1708I/M1708I001";
}
