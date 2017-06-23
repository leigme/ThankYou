package com.yhcloud.thankyou.comm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.utils.myview.MyToast;

/**
 * Created by Administrator on 2017/1/22.
 */

public abstract class BaseFragment extends Fragment implements BaseView, View.OnClickListener {

    public static String TAG = BaseFragment.class.getName();

    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = false;
    private SparseArray<View> mViews;
    public View convertView;

    // 加载等待框
    private ProgressDialog mProgressDialog;


    @Override
    public void onClick(View view) {
        processClick(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        isInitView = true;
        lazyLoad();
        return convertView;
    }

    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        initDatas();
        initEvents();
        isFirstLoad = false;
    }

    public <E extends View> E findView(int resId) {
        if (null != convertView) {
            E view = (E) mViews.get(resId);
            if (null == view) {
                view = (E) convertView.findViewById(resId);
                mViews.put(resId, view);
            }
            return view;
        }
        return null;
    }

    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    @Override
    public void showLoading(int msgId) {
        hiddenLoading();
        mProgressDialog = ProgressDialog.show(getActivity(), null, getString(msgId));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToastMsg(int msgId) {
        MyToast.showToast(getActivity(), msgId);
    }

    @Override
    public void showToastMsg(String msg) {
        MyToast.showToast(getActivity(), msg);
    }

}
