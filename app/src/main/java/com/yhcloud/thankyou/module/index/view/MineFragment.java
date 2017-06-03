package com.yhcloud.thankyou.module.index.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.MineFunctionAdapter;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.comm.BaseFragment;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.index.manage.MineManage;
import com.yhcloud.thankyou.service.LogicService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment implements MineActivityView {

    private OnFragmentInteractionListener mListener;

    //视图控件
    private View mView;
    private RecyclerView rvMineFunction;
    private MineFunctionAdapter mfa;
    //管理器
    private MineManage mManage;
    private LogicService mService;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(LogicService service) {
        MineFragment fragment = new MineFragment();
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
        mView = inflater.inflate(R.layout.fragment_mine, container, false);
        mManage = new MineManage(this, mService);
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
    public void initView() {
        rvMineFunction = (RecyclerView) mView.findViewById(R.id.rv_mine_function);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showList(ArrayList<FunctionBean> list) {
        if (null == mfa) {
            mfa = new MineFunctionAdapter(getActivity(), list);
            rvMineFunction.setLayoutManager(new LinearLayoutManager(getActivity()));
            mfa.setIOnClickListener(new ItemClinkListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    mManage.goFunction(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvMineFunction.setAdapter(mfa);
        } else {
            mfa.refreshData(list);
        }
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void processClick(View view) {

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
