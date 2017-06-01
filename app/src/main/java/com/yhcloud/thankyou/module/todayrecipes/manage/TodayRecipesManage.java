package com.yhcloud.thankyou.module.todayrecipes.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.TermBean;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.todayrecipes.bean.RecipesBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesJsonBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesPagerBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.TodayRecipesTimeBean;
import com.yhcloud.thankyou.module.todayrecipes.bean.WeekBean;
import com.yhcloud.thankyou.module.todayrecipes.view.TodayRecipesView;
import com.yhcloud.thankyou.module.todayrecipes.view.TodayRecipesFragmentViews;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by leig on 2017/2/9.
 */

public class TodayRecipesManage {

    private String TAG = getClass().getSimpleName();

    private TodayRecipesView mITodayRecipesView;
    private Activity mActivity;
    private LogicService mService;
    private TodayRecipesFragmentViews fragmentViews;
    private ArrayList<TodayRecipesPagerBean> pagerBeen;
    private ArrayList<TermBean> list;
    private ArrayList<ArrayList<WeekBean>> weekLists;
    private ArrayList<WeekBean> weekList;
    private int termNum, weekNum;

    public TodayRecipesManage(TodayRecipesView iTodayRecipesView) {
        this.mITodayRecipesView = iTodayRecipesView;
        this.mActivity = (Activity) mITodayRecipesView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                fragmentViews = new TodayRecipesFragmentViews(mActivity);
                mITodayRecipesView.initView();
                mITodayRecipesView.initEvent();
                getTermData();
                mITodayRecipesView.setTitle("今日菜谱");
                getRecipesData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getTermData() {
        mService.getTermList(new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("termData");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            list = gson.fromJson(jsonResult, new TypeToken<ArrayList<TermBean>>(){}.getType());
                            weekLists = new ArrayList<>();
                            WeekBean wb;
                            for (int i = 0; i < list.size(); i++) {
                                String weekCount = list.get(i).getWeekCount();
                                weekList = new ArrayList<>();
                                if (null != weekCount && !"".equals(weekCount)) {
                                    int wc = Integer.parseInt(weekCount);
                                    for (int j = 1; j < wc + 1; j++) {
                                        wb = new WeekBean();
                                        wb.setId(String.valueOf(j));
                                        wb.setTitle("第" + j+ "周");
                                        weekList.add(wb);
                                    }
                                } else {
                                    for (int j = 1; j < 33; j++) {
                                        wb = new WeekBean();
                                        wb.setId(String.valueOf(j));
                                        wb.setTitle("第" + j+ "周");
                                        weekList.add(wb);
                                    }
                                }
                                weekLists.add(weekList);
                            }
                            String jsonTime = jsonObject.getString("times");
                            if (null != jsonTime && !"".equals(jsonTime)) {
                                Gson timeGson = new Gson();
                                TodayRecipesTimeBean trtb = timeGson.fromJson(jsonTime, TodayRecipesTimeBean.class);
                                mITodayRecipesView.setTime(MessageFormat.format("{0} 第{1}周", trtb.getTitle(), trtb.getWeekNum()));
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getId().equals(trtb.getId())) {
                                        termNum = i;
                                    }
                                }
                                weekNum = Integer.parseInt(trtb.getWeekNum()) - 1;
                            }
                            mITodayRecipesView.initOptionsPickerView(list, weekLists);
                        }
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mITodayRecipesView.showToastMsg(errorMsg);
                        } else {
                            mITodayRecipesView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailure() {
                mITodayRecipesView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void getRecipesData() {
        mITodayRecipesView.showLoading(R.string.loading_data);
        mService.getRecipesData(new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("data");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<TodayRecipesJsonBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<TodayRecipesJsonBean>>(){}.getType());
                            showRecipes(list);
                        }
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mITodayRecipesView.showToastMsg(errorMsg);
                        } else {
                            mITodayRecipesView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mITodayRecipesView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mITodayRecipesView.showToastMsg(R.string.error_connection);
                mITodayRecipesView.hiddenLoading();
            }
        });
    }

    private void showRecipes(ArrayList<TodayRecipesJsonBean> list) {
        ArrayList<RecipesBean> mBeen = new ArrayList<>();
        RecipesBean trb;
        for (int i = 0 ; i < list.size(); i++) {
            TodayRecipesJsonBean trjb = list.get(i);
            trb = new RecipesBean();
            trb.setTitle(trjb.getTitle());
            trb.setInfo(trjb.getDescription());
            trb.setImageUrl(trjb.getPicUrl());
            trb.setDay(trjb.getDayIndex());
            switch (trjb.getPhase()) {
                case "1":
                    trb.setTag("早餐");
                    break;
                case "2":
                    trb.setTag("中餐");
                    break;
                case "3":
                    trb.setTag("晚餐");
                    break;
                default:
                    break;
            }
            mBeen.add(trb);
        }

        ArrayList<RecipesBean> mBeen1 = new ArrayList<>();
        ArrayList<RecipesBean> mBeen2 = new ArrayList<>();
        ArrayList<RecipesBean> mBeen3 = new ArrayList<>();
        ArrayList<RecipesBean> mBeen4 = new ArrayList<>();
        ArrayList<RecipesBean> mBeen5 = new ArrayList<>();
        for (RecipesBean recipesBean : mBeen) {
            switch (recipesBean.getDay()) {
                case "1":
                    mBeen1.add(recipesBean);
                    break;
                case "2":
                    mBeen2.add(recipesBean);
                    break;
                case "3":
                    mBeen3.add(recipesBean);
                    break;
                case "4":
                    mBeen4.add(recipesBean);
                    break;
                case "5":
                    mBeen5.add(recipesBean);
                    break;
                default:
                    break;
            }
        }
        ArrayList<ArrayList<RecipesBean>> arrayListArrayList = new ArrayList<>();
        arrayListArrayList.add(mBeen1);
        arrayListArrayList.add(mBeen2);
        arrayListArrayList.add(mBeen3);
        arrayListArrayList.add(mBeen4);
        arrayListArrayList.add(mBeen5);
        ArrayList<ArrayList<TodayRecipesBean>> arrayLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<TodayRecipesBean> todayRecipesBeen = new ArrayList<>();
            TodayRecipesBean todayRecipesBean1 = new TodayRecipesBean();
            TodayRecipesBean todayRecipesBean2 = new TodayRecipesBean();
            TodayRecipesBean todayRecipesBean3 = new TodayRecipesBean();
            ArrayList<RecipesBean> recipesBeen1 = new ArrayList<>();
            ArrayList<RecipesBean> recipesBeen2 = new ArrayList<>();
            ArrayList<RecipesBean> recipesBeen3 = new ArrayList<>();

            for (RecipesBean rb: arrayListArrayList.get(i)) {
                if ("早餐".equals(rb.getTag())) {
                    recipesBeen1.add(rb);
                }
            }
            todayRecipesBean1.setTag("早餐");
            todayRecipesBean1.setInfo("一日之计在于晨");
            todayRecipesBean1.setColor(0xFFFFEFFA);
            todayRecipesBean1.setBeen(recipesBeen1);

            for (RecipesBean rb: mBeen1) {
                if ("中餐".equals(rb.getTag())) {
                    recipesBeen2.add(rb);
                }
            }
            todayRecipesBean2.setTag("中餐");
            todayRecipesBean2.setInfo("正午给身体充电");
            todayRecipesBean2.setColor(0xFFFFFBE4);
            todayRecipesBean2.setBeen(recipesBeen2);

            for (RecipesBean rb: mBeen1) {
                if ("晚餐".equals(rb.getTag())) {
                    recipesBeen3.add(rb);
                }
            }
            todayRecipesBean3.setTag("晚餐");
            todayRecipesBean3.setInfo("补充能量不掉队");
            todayRecipesBean3.setColor(0xFFF5F9FF);
            todayRecipesBean3.setBeen(recipesBeen3);


            todayRecipesBeen.add(todayRecipesBean1);
            todayRecipesBeen.add(todayRecipesBean2);
            todayRecipesBeen.add(todayRecipesBean3);
            arrayLists.add(todayRecipesBeen);
        }


        pagerBeen = new ArrayList<>();
        TodayRecipesPagerBean trpb;
        ArrayList<TodayRecipesBean> arrayList;
        for (int i = 0; i < arrayLists.size(); i++) {
            trpb = new TodayRecipesPagerBean();
            arrayList = arrayLists.get(i);
            switch (i) {
                case 0:
                    trpb.setTitle("星期一");
                    if (null != mBeen1 && 0 != mBeen1.size()) {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list, arrayList));
                    } else {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list));
                    }
                    break;
                case 1:
                    trpb.setTitle("星期二");
                    if (null != mBeen2 && 0 != mBeen2.size()) {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list, arrayList));
                    } else {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list));
                    }
                    break;
                case 2:
                    trpb.setTitle("星期三");
                    if (null != mBeen3 && 0 != mBeen3.size()) {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list, arrayList));
                    } else {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list));
                    }
                    break;
                case 3:
                    trpb.setTitle("星期四");
                    if (null != mBeen4 && 0 != mBeen4.size()) {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list, arrayList));
                    } else {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list));
                    }
                    break;
                case 4:
                    trpb.setTitle("星期五");
                    if (null != mBeen5 && 0 != mBeen5.size()) {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list, arrayList));
                    } else {
                        trpb.setView(fragmentViews.TodayRecipesFragment(R.layout.fragment_todayrecipes, R.id.rv_todayrecipes_list));
                    }
                    break;
                default:
                    break;
            }
            pagerBeen.add(trpb);
        }
        mITodayRecipesView.showPager(pagerBeen);
    }

    public void getRecipesData(String termId, String weekId) {
        mITodayRecipesView.showLoading(R.string.loading_data);
        mService.getRecipesData(termId, weekId, new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("data");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<TodayRecipesJsonBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<TodayRecipesJsonBean>>(){}.getType());
                            mITodayRecipesView.cleanPagerView();
                            showRecipes(list);
                        }
                    } else {
                        String errorMsg = jsonObject.getString("errorMsg");
                        if (null != errorMsg && !"".equals(errorMsg)) {
                            mITodayRecipesView.showToastMsg(errorMsg);
                        } else {
                            mITodayRecipesView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mITodayRecipesView.hiddenLoading();
            }

            @Override
            public void callFailure() {
                mITodayRecipesView.showToastMsg(R.string.error_connection);
                mITodayRecipesView.hiddenLoading();
            }
        });
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public void setTermNum(int termNum) {
        this.termNum = termNum;
    }

    public void showOptionsPickerView() {
        Tools.print(TAG, MessageFormat.format("学期位置:{0},周次位置:{1}", termNum, weekNum));
        mITodayRecipesView.showOptionsPickerView(termNum, weekNum);
    }
}
