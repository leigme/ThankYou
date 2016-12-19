package com.yhcloud.thankyou.utils;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ServiceAPI {

    //生产环境服务器地址及开发环境服务器地址
    public static String SERVICEADDRESS = "http://www.k12chn.com";
//    public static final String SERVICEADDRESS = "http://192.168.0.139/edu";

    //自动更新请求检查及更新 返回info{version}
    public static final String UPDATEAPP = SERVICEADDRESS + "/m17/M1701I/M1701I001";

    //登录 user/liuyisha/pwd/666666
    public static final String LOGIN = SERVICEADDRESS + "/m17/M1708I/M1708I001";
}
