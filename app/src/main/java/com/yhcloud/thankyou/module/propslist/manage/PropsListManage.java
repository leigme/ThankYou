package com.yhcloud.thankyou.module.propslist.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.module.propslist.adapter.PropsListAdapter;
import com.yhcloud.thankyou.module.propslist.bean.PropsListBean;
import com.yhcloud.thankyou.module.propslist.bean.PropsListViewBean;
import com.yhcloud.thankyou.module.propslist.view.IPropsListActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PropsListManage {

    private String TAG = getClass().getSimpleName();

    private IPropsListActivityView mIPropsListView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<PropsListBean> plbReceiveBeen, plbSendBeen;
    private ArrayList<PropsListViewBean> mViewBeen;

    public PropsListManage(IPropsListActivityView iPropsListView) {
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
                getPropsListData(2, mViewBeen.get(0).getPageNow());
                getPropsListData(1, mViewBeen.get(1).getPageNow());
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
                        switch (typeId) {
                            case 1:
                                mViewBeen.get(1).setPageCount(pageCount);
                                break;
                            case 2:
                                mViewBeen.get(0).setPageCount(pageCount);
                                break;
                        }
                        if (null != jsonResult && !"".equals(jsonResult)) {
                            Gson gson = new Gson();
                            ArrayList<PropsListBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<PropsListBean>>(){}.getType());
                            int page;
                            switch (typeId) {
                                case 1:
                                    page = mViewBeen.get(1).getPageNow();
                                    mViewBeen.get(1).setPageNow(page + 1);
                                    break;
                                case 2:
                                    page = mViewBeen.get(0).getPageNow();
                                    mViewBeen.get(0).setPageNow(page + 1);
                                    break;
                            }
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
                    mViewBeen.get(1).getRecyclerView().addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
                    mViewBeen.get(1).getRecyclerView().setLayoutManager(layoutManager);
                    mViewBeen.get(1).setLayoutManager(layoutManager);
                    mViewBeen.get(1).getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                                int lastVisiblePosition = mViewBeen.get(1).getLayoutManager().findLastVisibleItemPosition();
                                if (lastVisiblePosition >= mViewBeen.get(1).getLayoutManager().getItemCount() - 1) {
                                    if (-1 != mViewBeen.get(1).getPageCount()) {
                                        if (mViewBeen.get(1).getPageNow() <= mViewBeen.get(1).getPageCount()) {
                                            getPropsListData(1, mViewBeen.get(1).getPageNow());
                                        }
                                    }
                                }
                            }
                        }
                    });
                    plaSendAdapter.setIOnClickListener(new IOnClickListener() {
                        @Override
                        public void OnItemClickListener(View view, int position) {

                        }

                        @Override
                        public void OnItemLongClickListener(View view, int position) {
                            Log.e(TAG, "长按SEND提示框。。。");
                            showPopWindows(1, position);
                        }
                    });
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
                    mViewBeen.get(0).getRecyclerView().addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
                    mViewBeen.get(0).getRecyclerView().setLayoutManager(layoutManager);
                    mViewBeen.get(0).setLayoutManager(layoutManager);
                    mViewBeen.get(0).getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                                int lastVisiblePosition = mViewBeen.get(0).getLayoutManager().findLastVisibleItemPosition();
                                if (lastVisiblePosition >= mViewBeen.get(0).getLayoutManager().getItemCount() - 1) {
                                    if (-1 != mViewBeen.get(0).getPageCount()) {
                                        if (mViewBeen.get(0).getPageNow() <= mViewBeen.get(0).getPageCount()) {
                                            getPropsListData(2, mViewBeen.get(0).getPageNow());
                                        }
                                    }
                                }
                            }
                        }
                    });
                    plaReceiveAdapter.setIOnClickListener(new IOnClickListener() {
                        @Override
                        public void OnItemClickListener(View view, int position) {

                        }

                        @Override
                        public void OnItemLongClickListener(View view, int position) {
                            Log.e(TAG, "长按RECEIVE提示框。。。");
                            showPopWindows(2, position);
                        }
                    });
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

    private void showPopWindows(int typeId, int position) {
        String content = "";
        switch (typeId) {
            case 1:
                content = MessageFormat.format("{0} 送给 {1} {2}X{3}", plbSendBeen.get(position).getSendRealName(), plbSendBeen.get(position).getRecvRealName(), plbSendBeen.get(position).getPropName(), plbSendBeen.get(position).getNum());
                break;
            case 2:
                content = MessageFormat.format("{0} 收到 {1} 赠送的 {2}X{3}", plbReceiveBeen.get(position).getRecvRealName(), plbReceiveBeen.get(position).getSendRealName(), plbReceiveBeen.get(position).getPropName(), plbReceiveBeen.get(position).getNum());
                break;
        }
        mIPropsListView.showToastMsg(content);
    }
}
