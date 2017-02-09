package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.logic.IMainLogic;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.ClassFragment;
import com.yhcloud.thankyou.view.HomeFragment;
import com.yhcloud.thankyou.view.IClassActivityView;
import com.yhcloud.thankyou.view.IMainActivityView;
import com.yhcloud.thankyou.view.MineFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leig on 2016/11/19.
 */

public class MainManage {

    private String TAG = getClass().getSimpleName();
    private IMainLogic mIMainLogic;
    private IMainActivityView mIMainView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private SparseArray<FunctionBean> mSparseArray;
    private ArrayList<Fragment> mFragments;
    private ArrayList<ClassInfoBean> mClassInfoBeen;
    private ArrayList<FunctionBean> mMenuBeen;
    private ArrayList<UserInfoBean> mUserInfoBeen;

    public MainManage(IMainActivityView mainView) {
        this.mIMainView = mainView;
        this.mActivity = (Activity) mainView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                if (null == mUserInfoBeen) {
                    mUserInfoBeen = new ArrayList<>();
                    getFriendList("-1");
                }
                mUserInfo = mService.getUserInfo();
                mSparseArray = Tools.initFunction(mActivity);
                mService.setBeanSparseArray(mSparseArray);
                mService.getUserAllFuncation();
                mIMainView.initView();
                mIMainView.initData();
                mIMainView.initEvent();
                initViewPages();
                mIMainView.showFragment(0);
                mIMainView.setHeaderLeftImage(mUserInfo.getUserInfoBean().getHeadImageURL());
                mIMainView.setTitle(mUserInfo.getUserInfoBean().getSchoolName());
                mIMainView.setDrawerHeadImg(mUserInfo.getUserInfoBean().getHeadImageURL());
                mIMainView.setDrawerUsername(mUserInfo.getUserInfoBean().getRealName());
                if (null != mActivity.getIntent()) {
                    Bundle bundle = mActivity.getIntent().getExtras();
                    mClassInfoBeen = (ArrayList<ClassInfoBean>) bundle.getSerializable("ClassInfos");
                    for (ClassInfoBean classInfoBean: mClassInfoBeen) {
                        if (classInfoBean.getClassId().equals(mUserInfo.getUserInfoBean().getDefaultClassId())) {
                            classInfoBean.setSelected(true);
                            mIMainView.setDrawerClassname(classInfoBean.getClassName());
                        }
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    private void getFriendList(String updateTime) {
        mService.getFriendList(updateTime, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                Tools.print(TAG, "获取用户好友列表信息成功...");
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("friendList");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Tools.print(TAG, "开始解析JSON...");
                            Gson gson = new Gson();
                            ArrayList<UserInfoBean> mBeen = gson.fromJson(jsonResult, new TypeToken<ArrayList<UserInfoBean>>(){}.getType());
                            mUserInfoBeen.addAll(mBeen);
                            mService.setUserInfoBeen(mUserInfoBeen);
                            HashMap<String, String[]> map;
                            map = makeMap(mUserInfoBeen);
                            mService.setMap(map);
                            setEaseUIProviders(map);
                            mService.setCanMessage(true);
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mService.setCanMessage(false);
            }

            @Override
            public void callFailed() {
                Tools.print(TAG, "获取用户好友列表信息失败...");
                mService.setCanMessage(false);
            }
        });
    }

    public void initViewPages() {
        mFragments = new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance(mService);
        mFragments.add(homeFragment);
        ClassFragment classFragment = ClassFragment.newInstance(mService);
        mFragments.add(classFragment);
        MineFragment mineFragment = MineFragment.newInstance(mService);
        mFragments.add(mineFragment);
        mIMainView.initFragments(mFragments);
    }

    public void setTitle(int i) {
        switch (i) {
            case 0:
                mIMainView.setTitle(mUserInfo.getUserInfoBean().getSchoolName());
                break;
            case 1:
                if (null != mClassInfoBeen) {
                    for (ClassInfoBean classInfoBean: mClassInfoBeen) {
                        if (classInfoBean.getClassId().equals(mUserInfo.getUserInfoBean().getDefaultClassId())) {
                            mIMainView.setTitle(classInfoBean.getClassName());
                        }
                    }
                }
                break;
            case 2:
                mIMainView.setTitle("我的");
                break;
        }
    }

    public void showDrawer() {
        if (null != mClassInfoBeen) {
            mIMainView.showDrawer(mClassInfoBeen);
        }
    }

    public void setDefaultClassId(String classId) {
        mUserInfo.getUserInfoBean().setDefaultClassId(classId);
        for (ClassInfoBean classInfoBean: mClassInfoBeen) {
            if (classInfoBean.getClassId().equals(mUserInfo.getUserInfoBean().getDefaultClassId())) {
                mIMainView.setDrawerClassname(classInfoBean.getClassName());
            }
        }
    }

    public void setRightButton(boolean showed) {
        mIMainView.initHeaderRightButton(showed);
        if (showed) {
            if (null == mMenuBeen || 0 == mMenuBeen.size()) {
                mMenuBeen = new ArrayList<>();
                mMenuBeen.add(mSparseArray.get(16));
                mMenuBeen.add(mSparseArray.get(17));
                FunctionBean functionBean = mSparseArray.get(18);
                functionBean.setTitle("课程表");
                mMenuBeen.add(functionBean);
            }
        }
    }

    public void setClassPeopleList(String classId) {
        IClassActivityView iClassView = (IClassActivityView) mFragments.get(1);
        iClassView.getClassManage().getClassPeopleList(classId);
    }

    public void showTrm() {
        mIMainView.showTrm(mMenuBeen);
    }

    public void refreshFuncations() {
        Tools.print(TAG, "刷新应用布局");
        HomeFragment homeFragment = (HomeFragment) mFragments.get(0);
        ArrayList<FunctionBean> mBeen = mService.getBeen();
        mBeen.add(7, mSparseArray.get(0));
        homeFragment.showFunction(mBeen);
    }

    //设置环信消息提供者
    public void setEaseUIProviders(final HashMap<String, String[]> map) {
        Log.e(TAG, "进来了setEaseUIProviders");
        //需要easeui库显示用户头像和昵称设置此provider
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                Log.e(TAG, "getUser :" + username);
                return getUserInfo(username);
            }

            private EaseUser getUserInfo(String username) {
                //获取user信息，demo是从内存的好友列表里获取，
                //实际开发中，可能还需要从服务器获取用户信息,
                //从服务器获取的数据，最好缓存起来，避免频繁的网络请求
//        EaseUser user = null;
//        if(username.equals(EMClient.getInstance().getCurrentUser()))
//            return getUserProfileManager().getCurrentUserInfo();
//        user = DemoHelper.getInstance().getContactList().get(username);
//        //TODO 获取不在好友列表里的群成员具体信息，即陌生人信息，demo未实现
//        if(user == null && getRobotList() != null){
//            user = getRobotList().get(username);
//        }

                EaseUser user = new EaseUser(username);
                if (null != map.get(username)) {
                    user.setNick(map.get(username)[1]);
                    user.setAvatar(Constant.SERVICEADDRESS + map.get(username)[0]);
                }
                return user;
            }
        });
    }

    //构建环信用户头像数据集
    private HashMap<String, String[]> makeMap(ArrayList<UserInfoBean> datas) {
        HashMap<String, String[]> map = new HashMap();
        String[] strs = null;
        if (null != datas) {
            for (UserInfoBean userInfoBean : datas) {
                strs = new String[]{userInfoBean.getHeadImageURL(), userInfoBean.getRealName()};
                map.put(userInfoBean.getHXUserName(), strs);
            }
        }
        return map;
    }
}
