package com.yhcloud.thankyou.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.yhcloud.thankyou.R;

import java.io.File;
import java.util.Locale;

/**
 * Created by Administrator on 2016/11/14.
 */

public class UpdateManager {
    private String TAG = getClass().getName();
    private Context mContext;

    private Dialog noticeDialog;
    private Dialog downloadDialog;
    //提示语
    private String updateMsg = "有最新的软件包哦，亲快下载吧~";
    //返回的安装包url
    private String apkUrl = "http://www.k12chn.com/download/ThankYou.apk";
    /* 下载包安装路径 */
    private String fileName = "ThankYou.apk";
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/updatedir/";
    private String saveFileName = savePath + fileName;
    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    //下载请求
//    private DownloadRequest mDownloadRequest;
    //下载队列
//    private DownloadQueue downloadQueue;

    private UpdateManagerListener uml;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    public UpdateManager(Context context, String msg) {
        this.mContext = context;
        this.updateMsg = msg;
    }

    public UpdateManager(Context context, String msg, String url, String name) {
        this.mContext = context;
        this.updateMsg = msg;
        this.apkUrl = url;
        this.fileName = name;
    }

    //外部接口让主Activity调用
    public void checkUpdateInfo(){
        showNoticeDialog();
    }

    private void showNoticeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null != uml) {
                    uml.goOn();
                }
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar)v.findViewById(R.id.id_progress);

        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (null!=uml) {
                    uml.goOn();
                }
//                mDownloadRequest.cancel();
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
//        downloadApk();
    }

    /**
     * 下载apk
     * @param
     */

//    private void downloadApk() {
//        // 开始下载了，但是任务没有完成，代表正在下载，那么暂停下载。
//        if (mDownloadRequest != null && mDownloadRequest.isStarted() && !mDownloadRequest.isFinished()) {
//            // 暂停下载。
//            mDownloadRequest.cancel();
//        } else if (mDownloadRequest == null || mDownloadRequest.isFinished()) {
//            // 没有开始或者下载完成了，就重新下载。
//            // url 下载地址。
//            // fileFolder 保存的文件夹。
//            // fileName 文件名。
//            // isRange 是否断点续传下载。
//            // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
//            mDownloadRequest = NoHttp.createDownloadRequest(apkUrl, savePath, fileName, true, true);
//            //构建下载队列
//            downloadQueue = NoHttp.newDownloadQueue();
//            // what 区分下载。
//            // downloadRequest 下载请求对象。
//            // downloadListener 下载监听。
//            downloadQueue.add(0, mDownloadRequest, downloadListener);
//        }
//    }
//
//    private DownloadListener downloadListener = new DownloadListener() {
//
//        @Override
//        public void onDownloadError(int what, Exception exception) {
//            Log.e(TAG, "下载失败..." + getDownloadError(exception));
//            downloadDialog.dismiss();
//            if (null != uml) {
//                uml.goOn();
//            }
//        }
//
//        @Override
//        public void onStart(int what, boolean resume, long preLenght, Headers header, long count) {
//            Log.e(TAG, "下载开始...");
//        }
//
//        @Override
//        public void onProgress(int what, int progress, long downCount) {
//            Log.e(TAG, String.valueOf(progress));
//            updateProgress(progress);
//        }
//
//        @Override
//        public void onFinish(int what, String filePath) {
//            Log.e(TAG, "下载结束...");
//            downloadDialog.dismiss();
//            installApk();
//        }
//
//        @Override
//        public void onCancel(int what) {
//            Log.e(TAG, "下载暂停！");
//        }
//
//        //更新下载进度
//        private void updateProgress(int progress) {
//            mProgress.setProgress(progress);
//        }
//    };

    /**
     * 安装apk
     * @param
     */
    private void installApk(){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

//    private String getDownloadError(Exception exception) {
//        String message = "Download the wrong:%1$s";
//        String messageContent;
//        if (exception instanceof ServerError) {
//            messageContent = "Server data error!";
//        } else if (exception instanceof NetworkError) {
//            messageContent = "The network is not available, please check the network!";
//        } else if (exception instanceof StorageReadWriteError) {
//            messageContent = "Memory error, please check the memory card!";
//        } else if (exception instanceof StorageSpaceNotEnoughError) {
//            messageContent = "There is insufficient space on the memory card!";
//        } else if (exception instanceof TimeoutError) {
//            messageContent = "Download the timeout!";
//        } else if (exception instanceof UnKnownHostError) {
//            messageContent = "Can not find a server.";
//        } else if (exception instanceof URLError) {
//            messageContent = "URL address errors.";
//        } else if (exception instanceof ArgumentError) {
//            messageContent = "Download the parameter error.";
//        } else {
//            messageContent = "Unknown error.";
//        }
//        message = String.format(Locale.getDefault(), message, messageContent);
//        return message;
//    }

    //设置升级管理的监听方法
    public void setListenerInterface(UpdateManagerListener uml) {
        this.uml = uml;
    }

    public interface UpdateManagerListener {
        //不升级，继续使用...
        public void goOn();
    }
}
