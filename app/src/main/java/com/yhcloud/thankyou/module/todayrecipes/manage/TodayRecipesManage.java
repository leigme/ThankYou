package com.yhcloud.thankyou.module.todayrecipes.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;
import com.yhcloud.thankyou.module.todayrecipes.view.ITodayRecipesView;
import com.yhcloud.thankyou.module.todayrecipes.view.TodayRecipesFragmentViews;
import com.yhcloud.thankyou.module.todayrecipes.view.TodayRecipesInfoActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public class TodayRecipesManage {

    private String TAG = getClass().getSimpleName();

    private ITodayRecipesView mITodayRecipesView;
    private Activity mActivity;
    private LogicService mService;
    public final static String dishTitle = "dishTitle", imageUrl = "imageUrl", dishInfo = "dishInfo";

    public TodayRecipesManage(ITodayRecipesView iTodayRecipesView) {
        this.mITodayRecipesView = iTodayRecipesView;
        this.mActivity = (Activity) mITodayRecipesView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mITodayRecipesView.initView();
                mITodayRecipesView.initEvent();
                mITodayRecipesView.setTitle("今日菜谱");
                testData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void testData() {
        TodayRecipesFragmentViews fragmentViews = new TodayRecipesFragmentViews(mActivity);
        final ArrayList<TodayRecipesBean> recipesBeen = new ArrayList<>();
        TodayRecipesBean recipesBean;
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    recipesBean = new TodayRecipesBean();
                    recipesBean.setType(0);
                    recipesBean.setTitle("早餐");
                    break;
                case 3:
                    recipesBean = new TodayRecipesBean();
                    recipesBean.setType(0);
                    recipesBean.setTitle("中餐");
                    break;
                case 5:
                    recipesBean = new TodayRecipesBean();
                    recipesBean.setType(0);
                    recipesBean.setTitle("晚餐");
                    break;
                default:
                    recipesBean = new TodayRecipesBean();
                    recipesBean.setType(1);
                    recipesBean.setTitle("红烧肉");
                    recipesBean.setImageUrl("http://s2.boohee.cn/house/food_big/big_photo201521116235212760.jpg");
                    recipesBean.setInfo("热量:431卡;脂肪:42.73克;碳水物:4.3克;蛋白质:7.31克");
                    break;
            }
            recipesBeen.add(recipesBean);
        }

        ArrayList<TodayRecipesPagerBean> pagerBeen = new ArrayList<>();
        TodayRecipesPagerBean pagerBean;
        View view;
        IOnClickListener iOnClickListener = new IOnClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Tools.print(TAG, recipesBeen.get(position).getTitle());
                Intent intent = new Intent(mActivity, TodayRecipesInfoActivity.class);
                intent.putExtra(dishTitle, recipesBeen.get(position).getTitle());
                intent.putExtra(imageUrl, recipesBeen.get(position).getImageUrl());
                intent.putExtra(dishInfo, recipesBeen.get(position).getInfo());
                mActivity.startActivity(intent);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        };

        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    pagerBean = new TodayRecipesPagerBean();
                    pagerBean.setTitle("星期一");
                    view = fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes,
                            R.id.rv_todayrecipes_list, recipesBeen, iOnClickListener);
                    pagerBean.setView(view);
                    break;
                case 1:
                    pagerBean = new TodayRecipesPagerBean();
                    pagerBean.setTitle("星期二");
                    view = fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes,
                            R.id.rv_todayrecipes_list, recipesBeen, iOnClickListener);
                    pagerBean.setView(view);
                    break;
                case 2:
                    pagerBean = new TodayRecipesPagerBean();
                    pagerBean.setTitle("星期三");
                    view = fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes,
                            R.id.rv_todayrecipes_list, recipesBeen, iOnClickListener);
                    pagerBean.setView(view);
                    break;
                case 3:
                    pagerBean = new TodayRecipesPagerBean();
                    pagerBean.setTitle("星期四");
                    view = fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes,
                            R.id.rv_todayrecipes_list, recipesBeen, iOnClickListener);
                    pagerBean.setView(view);
                    break;
                case 4:
                    pagerBean = new TodayRecipesPagerBean();
                    pagerBean.setTitle("星期五");
                    view = fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes,
                            R.id.rv_todayrecipes_list, recipesBeen, iOnClickListener);
                    pagerBean.setView(view);
                    break;
                default:
                    pagerBean = new TodayRecipesPagerBean();
                    break;
            }
            pagerBeen.add(pagerBean);
        }
        mITodayRecipesView.showPager(pagerBeen);
    }
}
