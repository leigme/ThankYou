package com.yhcloud.thankyou.module.propslist.bean;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yhcloud.thankyou.module.propslist.adapter.PropsListAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PropsListViewBean {

    private int mType, pageCount = -1, pageNow = 1;
    private String mTitle;
    private View mView;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private PropsListAdapter mAdapter;
    private ArrayList<PropsListBean> mBeen;
    private boolean showed;

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    public void setRefreshLayout(SwipeRefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public PropsListAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(PropsListAdapter adapter) {
        mAdapter = adapter;
    }

    public ArrayList<PropsListBean> getBeen() {
        return mBeen;
    }

    public void setBeen(ArrayList<PropsListBean> been) {
        mBeen = been;
    }

    public boolean isShowed() {
        return showed;
    }

    public void setShowed(boolean showed) {
        this.showed = showed;
    }
}
