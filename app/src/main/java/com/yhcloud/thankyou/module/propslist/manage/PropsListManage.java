package com.yhcloud.thankyou.module.propslist.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.propslist.adapter.PropsListAdapter;
import com.yhcloud.thankyou.module.propslist.bean.PropsListBean;
import com.yhcloud.thankyou.module.propslist.bean.PropsListViewBean;
import com.yhcloud.thankyou.module.propslist.view.IPropsListView;
import com.yhcloud.thankyou.service.LogicService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PropsListManage {

    private IPropsListView mIPropsListView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<PropsListBean> plbReceiveBeen, plbSendBeen;
    private ArrayList<PropsListViewBean> mViewBeen;


    public PropsListManage(IPropsListView iPropsListView) {
        this.mIPropsListView = iPropsListView;
        this.mActivity = (Activity) mIPropsListView;
        plbReceiveBeen = new ArrayList<>();
        plbSendBeen = new ArrayList<>();
        mViewBeen = new ArrayList<>();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIPropsListView.initView();
                mIPropsListView.initEvent();
                mIPropsListView.setTitle("道具记录");
                initViewPager();
                getPropsListData(2, 1);
                getPropsListData(1, 1);
                mIPropsListView.initViewPage(mViewBeen);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getPropsListData(final int typeId, int pageNum) {
        mService.getPropsList(typeId, pageNum, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonResult = jsonObject.getString("data");
                        int pageCount = jsonObject.getInt("pageCount");
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<PropsListBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<PropsListBean>>(){}.getType());
                            showListData(typeId, list);
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {

            }
        });
    }

    public void showListData(int type, ArrayList<PropsListBean> list) {
        switch (type) {
            case 1:
                plbSendBeen.addAll(list);
                if (null == mViewBeen.get(1).getAdapter()) {
                    PropsListAdapter plaSendAdapter = new PropsListAdapter(mActivity, plbSendBeen, 1);
                    mViewBeen.get(1).setAdapter(plaSendAdapter);
                    mViewBeen.get(1).getRecyclerView().setLayoutManager(new LinearLayoutManager(mActivity));
                    mViewBeen.get(1).getRecyclerView().setAdapter(plaSendAdapter);
                } else {
                    mViewBeen.get(1).getAdapter().refreshData(plbSendBeen);
                }
                break;
            case 2:
                plbReceiveBeen.addAll(list);
                if (null == mViewBeen.get(0).getAdapter()) {
                    PropsListAdapter plaReceiveAdapter = new PropsListAdapter(mActivity, plbReceiveBeen, 2);
                    mViewBeen.get(0).setAdapter(plaReceiveAdapter);
                    mViewBeen.get(0).getRecyclerView().setLayoutManager(new LinearLayoutManager(mActivity));
                    mViewBeen.get(0).getRecyclerView().setAdapter(plaReceiveAdapter);
                } else {
                    mViewBeen.get(0).getAdapter().refreshData(plbReceiveBeen);
                }
                break;
        }
    }

    public void initViewPager() {
        PropsListViewBean pvReceivePager = new PropsListViewBean();
        PropsListViewBean pvSendPager = new PropsListViewBean();
        pvReceivePager.setTitle("收到的道具");
        pvSendPager.setTitle("送出的道具");
        View receiveView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_propslist_list, null);
        View sendView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_propslist_list, null);
        pvReceivePager.setView(receiveView);
        pvSendPager.setView(sendView);
        RecyclerView rvReceiveList = (RecyclerView) receiveView.findViewById(R.id.rv_propslist_list);
        RecyclerView rvSendList = (RecyclerView) sendView.findViewById(R.id.rv_propslist_list);
        pvReceivePager.setRecyclerView(rvReceiveList);
        pvSendPager.setRecyclerView(rvSendList);
        mViewBeen.add(pvReceivePager);
        mViewBeen.add(pvSendPager);
    }
}
