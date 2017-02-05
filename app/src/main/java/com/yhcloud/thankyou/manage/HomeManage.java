package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.IHomeActivityView;
import com.yhcloud.thankyou.view.WebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeManage {

    private String TAG = getClass().getSimpleName();
    private IHomeActivityView mIHomeView;
    private Activity mActivity;
    private Fragment mFragment;
    private LogicService mService;
    private ArrayList<SpreadBean> mBannerList, mSpreadBeen;
    private SparseArray<FunctionBean> mSparseArray;
    private ArrayList<FunctionBean> mBeen;
    private boolean refrshBanner;
    private int refreshBannerNum, refreshSpreadNum;

    public HomeManage(IHomeActivityView homeView, LogicService service) {
        this.mIHomeView = homeView;
        this.mFragment = (Fragment) mIHomeView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mIHomeView.initView();
        mIHomeView.initEvent();
        mSparseArray = mService.getBeanSparseArray();
        mBeen = mService.getBeen();
        switch (mService.getUserInfo().getUserInfoBean().getUserRoleId()) {
            case 1004:
                showSpreadList("-1");
                break;
            case 1010:
                showSpreadList("-1");
                break;
            case 1011:
                showSpreadList("-1");
                break;
            case 1012:
                showSpreadList("-1");
                break;
            default:
                break;
        }
        showBanner("-1");
        showFunctionList();
    }

    public void showBanner(String updateTime) {
        mService.getImageUrls(updateTime, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String msg = jsonObject.getString("errorMsg");
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonList = jsonObject.getString("dataList");
                        if (null != jsonList && !"".equals(jsonList)) {
                            Gson gson = new Gson();
                            Type jsonType =new TypeToken<ArrayList<SpreadBean>>(){}.getType();
                            mBannerList = gson.fromJson(jsonList, jsonType);
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (SpreadBean sb: mBannerList) {
                                arrayList.add(sb.getSummaryPicLink());
                            }
                            mIHomeView.showBanner(arrayList);
                            refrshBanner = false;
                        }
                    } else {
                        if (null != msg && !"".equals(msg)) {
                            mIHomeView.showToastMsg(msg);
                        } else {
                            mIHomeView.showToastMsg(R.string.error_connection);
                        }
                        refrshBanner = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    refrshBanner = true;
                }
            }

            @Override
            public void callFailed() {
                mIHomeView.showToastMsg(R.string.error_connection);
                refrshBanner = true;
                if (3 > refreshBannerNum) {
                    showBanner("-1");
                    refreshBannerNum += 1;
                }
            }
        });
    }

    public void showFunctionList() {
        mBeen.add(7, mSparseArray.get(0));
        mIHomeView.showFunction(mBeen);
    }

    public void showSpreadList(String updateTime) {
        mService.getSpreadList(updateTime, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonList = jsonObject.getString("dataList");
                        if (null != jsonList && !"".equals(jsonList)) {
                            Gson gson = new Gson();
                            Type jsonType = new TypeToken<ArrayList<SpreadBean>>(){}.getType();
                            mSpreadBeen = gson.fromJson(jsonList, jsonType);
                            mIHomeView.showSpread(mSpreadBeen);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIHomeView.showToastMsg(msg);
                        } else {
                            mIHomeView.showToastMsg(R.string.error_connection);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {
                mIHomeView.showToastMsg(R.string.error_connection);
                if (3 > refreshSpreadNum) {
                    showSpreadList("-1");
                    refreshSpreadNum += 1;
                }
            }
        });
    }

    public void goBannerInfo(int position) {
        if (refrshBanner) {
            showBanner("-1");
        } else {
            SpreadBean spreadBean = mBannerList.get(position - 1);
            Tools.print(TAG, "点击的是:第" + position + "个图片，标题是:" + spreadBean.getTitle());
            Intent intent = new Intent(mActivity, WebActivity.class);
            String url = MessageFormat.format("{0}/Id/{1}", Constant.GETSPREADDATA, spreadBean.getId());
            intent.putExtra("Title", spreadBean.getTitle());
            intent.putExtra("Url", url);
            mActivity.startActivity(intent);
        }
    }

    public void goSpreadInfo(int position) {
        Tools.print(TAG, MessageFormat.format("点击了第{0}个推广链接", mSpreadBeen.get(position).getTitle()));
        SpreadBean sb = mSpreadBeen.get(position);
        Intent intent = new Intent(mActivity, WebActivity.class);
        String url = MessageFormat.format("{0}/Id/{1}", Constant.GETSPREADDATA, sb.getId());
        intent.putExtra("Title", sb.getTitle());
        intent.putExtra("Url", url);
        mActivity.startActivity(intent);
    }

    public void goFunction(int position) {
        FunctionBean functionBean = mBeen.get(position);
        if (null != functionBean.getIntent()) {
            if (0 == functionBean.getId()) {
                mActivity.startActivityForResult(functionBean.getIntent(), Constant.ALLFUNCATION_REQUEST);
            } else if (4 == functionBean.getId()){
                if (mService.isCanMessage()) {
                    mActivity.startActivity(functionBean.getIntent());
                }
            } else {
                mActivity.startActivity(functionBean.getIntent());
            }
        }
    }

    public void saveFunctionList() {
        mService.saveFuncations();
    }
}
