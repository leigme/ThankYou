package com.yhcloud.thankyou.module.index.view;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.HomeFunctionListAdapter;
import com.yhcloud.thankyou.adapter.HomeSpreadAdapter;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.comm.BaseFragment;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.index.manage.HomeManage;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.GlideImageLoader;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.LinearLayoutForListView;
import com.yhcloud.thankyou.utils.myview.drag.DragItemCallBack;
import com.yhcloud.thankyou.utils.myview.drag.RecycleCallBack;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends BaseFragment implements HomeActivityView {

    private static String TAG = HomeFragment.class.getName();

    private OnFragmentInteractionListener mListener;

    //视图控件
    private View mView;
    private Banner mBanner;
    private RecyclerView rvFunctionList;
    private LinearLayoutForListView llflSpreadList;
    //适配器
    private HomeFunctionListAdapter hfla;
    private RecycleCallBack rcb;
    private ItemTouchHelper ith;
    private HomeSpreadAdapter hsa;
    //管理器
    private HomeManage mManage;
    private LogicService mService;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        mBanner = findView(R.id.banner);
        rvFunctionList = findView(R.id.rv_home_function_list);
        llflSpreadList = findView(R.id.llflv_plv_spread);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initEvents() {
        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                mManage.goBannerInfo(position);
            }
        });
    }

    @Override
    public void processClick(View view) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showBanner(ArrayList<String> imageUrls) {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(imageUrls);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        mBanner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void showFunction(final ArrayList<FunctionBean> list) {
        Tools.print(TAG, "设置功能区域...");
        if (null == hfla) {
            rcb = new RecycleCallBack() {
                @Override
                public void itemOnClick(int position, View view) {
                    Tools.print(TAG, "点击监听事件:" + position);
                }

                @Override
                public void itemOnLongClick(int position, View view) {
                    if (list.size() - 1 == position) {
                        return;
                    }
                    Tools.print(TAG, "长按监听事件:" + position);
                }

                @Override
                public void onMove(int from, int to) {
                    if (from == list.size() - 1 || to == list.size() - 1) {
                        return;
                    }
                    Tools.print(TAG, "移动监听事件:从" + from + "到" + to);
                    synchronized (this) {
                        if (from > to) {
                            int count = from - to;
                            for (int i = 0; i < count; i++) {
                                Collections.swap(list, from - i, from - i - 1);
                            }
                        }
                        if (from < to) {
                            int count = to - from;
                            for (int i = 0; i < count; i++) {
                                Collections.swap(list, from + i, from + i + 1);
                            }
                        }
                    }
                    hfla.setData(list);
                    hfla.notifyItemMoved(from, to);
                    hfla.show.clear();
                    hfla.show.put(to, to);
                }

                @Override
                public void saveFunctionList() {
                    mManage.saveFunctionList();
                }
            };
            hfla = new HomeFunctionListAdapter(getActivity(), list, rcb);
            hfla.setIOnClickListener(new ItemClinkListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goFunction(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvFunctionList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            ith = new ItemTouchHelper(new DragItemCallBack(rcb));
            ith.attachToRecyclerView(rvFunctionList);
            rvFunctionList.setAdapter(hfla);
        } else {
            hfla.setData(list);
            hfla.refreshData(list);
        }
    }

    @Override
    public void showSpread(ArrayList<SpreadBean> list) {
        if (null == hsa) {
            hsa = new HomeSpreadAdapter(getActivity(), list);
            llflSpreadList.setAdapter(hsa);
            llflSpreadList.setOnItemClickListener(new LinearLayoutForListView.OnItemClickListener() {
                @Override
                public void onItemClicked(View v, Object obj, int position) {
                    mManage.goSpreadInfo(position);
                }
            });
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setRightTitle(String title) {

    }

    public void setFunctionList(ArrayList<FunctionBean> list) {
        mManage.setBeen(list);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
