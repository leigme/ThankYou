package com.yhcloud.thankyou.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.HomeFunctionListAdapter;
import com.yhcloud.thankyou.adapter.HomeSpreadAdapter;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.comm.BaseFragment;
import com.yhcloud.thankyou.comm.SubmitCallBack;
import com.yhcloud.thankyou.manage.HomeManage;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements HomeActivityView {

    private String TAG = getClass().getSimpleName();

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

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(LogicService service) {
        HomeFragment fragment = new HomeFragment();
        fragment.mService = service;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mManage = new HomeManage(this, mService);
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
            hfla.setIOnClickListener(new SubmitCallBack() {
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
        mBanner = (Banner) mView.findViewById(R.id.banner);
        rvFunctionList = (RecyclerView) mView.findViewById(R.id.rv_home_function_list);
        llflSpreadList = (LinearLayoutForListView) mView.findViewById(R.id.llflv_plv_spread);
    }

    @Override
    public void initEvent() {
        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                mManage.goBannerInfo(position);
            }
        });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
