package com.yhcloud.thankyou.utils;

/**
 * Created by leig on 2016/11/17.
 */

public class Constant {

    public static final boolean printLog = true;

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
//        public static final String SERVICEADDRESS = "http://192.168.0.139/edu";

    //登录请求 uid pwd
    public static final String LOGIN = SERVICEADDRESS + "/m17/M1708I/M1708I001";


    //获取推广列表 promotionType:推广类型[1:精品课程 2:精品阅读 4:直播课堂 8:付费精品 16:首页轮播] scopeCrowd:[1:学生 2:老师 4:家长] updateTime:[-1:获取全部]
    public static final String GETSPREADLIST = SERVICEADDRESS + "/m01/M0108I/M0108I07";
    //获取推广详情 Id
    public static final String GETSPREADDATA = SERVICEADDRESS + "/m01/M0108P04/M0108P04001";

    //学校公告 http://192.168.0.139/edu/m17/m1717I/m1717I002/schoolId/12
    public static final String GETSHCHOOLANNOUNCEMENTLIST = SERVICEADDRESS + "/m17/m1717I/m1717I002";
    //班级通知 http://192.168.0.139/edu/m17/M1711I/M1711I01/userId/3237/classId//pageNum/2/updateTime/-1/
    public static final String GETCLASSNOTIFICATIONLIST = SERVICEADDRESS + "/m17/M1711I/M1711I01";
    //更新班级通知阅读状态 http://192.168.0.139/edu/noticeId/49/userId/3237 isRead:0未读;1:已读;2:删除
    public static final String UPDATEREADSTATE = SERVICEADDRESS + "/m17/M1711I/M1711I02";

    //班干部  classId
    public static final String GETCLASSCADRE = SERVICEADDRESS + "/m17/m1718I/m1718I003";
    //值日生 classId
    public static final String GETDUTYSTUDENT = SERVICEADDRESS + "/m05/M0507I/M0507I05";
    //获取班级课表 http://www.k12chn.com/m07/M0703I/M0703I01/classid/161226/termid/10/uid/3237
    public static final String GETCLASSCURRICULUM = SERVICEADDRESS + "/m07/M0703I/M0703I01";
    //获取班级学生 userId classId updateTime
    public static final String GETCLASSPEOPLELIST = SERVICEADDRESS + "/m17/m1718I/m1718I001";
    //获取本班老师 classId
    public static final String GETCLASSTEACHERLIST = SERVICEADDRESS + "/m17/m1718I/m1718I002";
    //获取用户详情 uid id
    public static final String GETDETAILINFO = SERVICEADDRESS + "/m00/M0005I/M0005I02";

    //获取道具流转记录 http://www.k12chn.com/m00/M0009I/M0009I014/userId/3237/page/1/type/2
    public static final String GETPROPSLIST = SERVICEADDRESS + "/m00/M0009I/M0009I014";

    //获取关于我们的信息
    public static final String GETABOUTUSINFO = SERVICEADDRESS + "/m17/M1705I/M1705I001";
}
