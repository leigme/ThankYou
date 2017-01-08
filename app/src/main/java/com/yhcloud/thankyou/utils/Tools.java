package com.yhcloud.thankyou.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.module.aboutus.view.AboutUsActivity;
import com.yhcloud.thankyou.module.classcadre.view.ClassCadreActivity;
import com.yhcloud.thankyou.module.classnotification.view.ClassNotificationActivity;
import com.yhcloud.thankyou.module.classteachers.view.ClassTeacherListActivity;
import com.yhcloud.thankyou.module.curriculum.view.CurriculumActivity;
import com.yhcloud.thankyou.module.dutystudent.view.DutyStudentActivity;
import com.yhcloud.thankyou.module.propslist.view.PropsListActivity;
import com.yhcloud.thankyou.module.schoolannouncement.view.SchoolAnnouncementActivity;
import com.yhcloud.thankyou.view.EaseChatActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2016/11/10.
 */

public class Tools {

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static int getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    public static void GlideCircleImageUrl(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(Constant.SERVICEADDRESS + path)
                .placeholder(R.mipmap.default_photo)
                .error(R.mipmap.icon_account_404)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    public static void GlideCircleImageUrl(Context context, String path, int defaultId, ImageView imageView) {
        Glide.with(context)
                .load(Constant.SERVICEADDRESS + path)
                .placeholder(defaultId)
                .error(defaultId)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    public static void GlideImageUrl(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(Constant.SERVICEADDRESS + path)
                .error(R.mipmap.icon_big_404)
                .into(imageView);
    }

    //Http正则验证 URL:true
    public static boolean isUrlNO(String url) {
        Pattern p = Pattern
                .compile("^([hH][tT]{2}[pP]:\\/\\/|[hH][tT]{2}[pP][sS]:\\/\\/)(([A-Za-z0-9-~]+)\\.)+([A-Za-z0-9-~\\/])+$");
        Matcher m = p.matcher(url);
//        Log.e("正则验证:", "正则验证:" + m.matches() + "---");
        return m.matches();
    }

    public static int getFileTypeImage(String str) {
        int resourceTypeId = R.mipmap.icon_account_all;
        str = str.trim();
        ArrayList<FileTypeImage> fileTypeImages = new ArrayList<>();
        fileTypeImages.add(new FileTypeImage("doc", R.mipmap.icon_account_doc));
        fileTypeImages.add(new FileTypeImage("docx", R.mipmap.icon_account_doc));
        fileTypeImages.add(new FileTypeImage("xlsx", R.mipmap.icon_account_xls));
        fileTypeImages.add(new FileTypeImage("xls", R.mipmap.icon_account_xls));
        fileTypeImages.add(new FileTypeImage("ppt", R.mipmap.icon_account_ppt));
        fileTypeImages.add(new FileTypeImage("pdf", R.mipmap.icon_account_pdf));
        fileTypeImages.add(new FileTypeImage("mp3", R.mipmap.icon_account_mp3));
        fileTypeImages.add(new FileTypeImage("mp4", R.mipmap.icon_account_mp4));
        fileTypeImages.add(new FileTypeImage("rmvb", R.mipmap.icon_account_mp4));
        fileTypeImages.add(new FileTypeImage("txt", R.mipmap.icon_account_txt));
        for (FileTypeImage fileTypeImage: fileTypeImages) {
            if (null != str && fileTypeImage.getFileType().equals(str)) {
                resourceTypeId = fileTypeImage.getResourceId();
            }
        }
        return resourceTypeId;
    }

    public static SparseArray<FunctionBean> initFunction(Context context) {
        SparseArray<FunctionBean> sparseArray = new SparseArray<>();
        sparseArray.append(0, new FunctionBean(0, 0, R.mipmap.icon_function_all, "全部应用"));
        sparseArray.append(1, new FunctionBean(1, R.mipmap.icon_my_info, 0, "我的资料"));
        sparseArray.append(2, new FunctionBean(2, R.mipmap.icon_my_friends, 0, "我的朋友"));
        sparseArray.append(3, new FunctionBean(3, 0, R.mipmap.icon_function_props, "道具榜", new Intent(context, PropsListActivity.class)));
        sparseArray.append(4, new FunctionBean(4, R.mipmap.icon_my_message, R.mipmap.icon_function_message, "我的消息", new Intent(context, EaseChatActivity.class)));
        sparseArray.append(5, new FunctionBean(5, R.mipmap.icon_my_account, 0, "我的账户"));
        sparseArray.append(6, new FunctionBean(6, R.mipmap.icon_my_schedule, 0, "每日签到"));
        sparseArray.append(7, new FunctionBean(7, R.mipmap.icon_my_downloads, R.mipmap.icon_function_download, "我的下载"));
        sparseArray.append(8, new FunctionBean(8, R.mipmap.icon_about_me, 0, "关于我们", new Intent(context, AboutUsActivity.class)));
        sparseArray.append(9, new FunctionBean(9, R.mipmap.icon_account_cutover, 0, "切换账户"));
        sparseArray.append(10, new FunctionBean(10, 0, R.mipmap.icon_function_announcement, "学校公告", new Intent(context, SchoolAnnouncementActivity.class)));
        sparseArray.append(11, new FunctionBean(11, 0, R.mipmap.icon_function_notice, "班级通知", new Intent(context, ClassNotificationActivity.class)));
        sparseArray.append(12, new FunctionBean(12, 0, R.mipmap.icon_function_resources, "教学资源"));
        sparseArray.append(13, new FunctionBean(13, 0, R.mipmap.icon_function_schedule, "工作日程"));
        sparseArray.append(14, new FunctionBean(14, 0, R.mipmap.icon_function_homework, "课后作业"));
        sparseArray.append(15, new FunctionBean(15, 0, R.mipmap.icon_function_exercise, "课堂练习"));
        sparseArray.append(16, new FunctionBean(16, R.mipmap.icon_class_cadre, 0, "班干部", new Intent(context, ClassCadreActivity.class)));
        sparseArray.append(17, new FunctionBean(17, R.mipmap.icon_class_duty, 0, "值日生", new Intent(context, DutyStudentActivity.class)));
        sparseArray.append(18, new FunctionBean(18, R.mipmap.icon_class_curriculum, 0, "班级课表", new Intent(context, CurriculumActivity.class)));
        sparseArray.append(19, new FunctionBean(19, 0, 0, "本班老师", new Intent(context, ClassTeacherListActivity.class)));
        sparseArray.append(20, new FunctionBean(20, R.mipmap.icon_my_props, 0, "我的道具", new Intent(context, PropsListActivity.class)));
        return sparseArray;
    }

    public static class FileTypeImage {
        private String mFileType;
        private int mResourceId;

        public FileTypeImage(String fileType, int resourceId) {
            this.mFileType = fileType;
            this.mResourceId = resourceId;
        }

        public String getFileType() {
            return mFileType;
        }

        public void setFileType(String fileType) {
            mFileType = fileType;
        }

        public int getResourceId() {
            return mResourceId;
        }

        public void setResourceId(int resourceId) {
            mResourceId = resourceId;
        }
    }

    public static void print(String tag, String msg) {
        if (Constant.printLog) {
            Log.e(tag, msg);
        }
    }
}
