package com.yhcloud.thankyou.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.ClassPeopleListAdapter;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.manage.ClassManage;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassFragment extends Fragment implements IClassView {

    private String TAG = getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;

    //视图控件
    private View mView;
    private LinearLayout llTeachers;
    private RecyclerView rvPeopleList;
    private ProgressDialog mProgressDialog;
    //适配器
    private ClassPeopleListAdapter cpla;
    //管理器
    private ClassManage mManage;
    private LogicService mService;

    public ClassFragment() {
        // Required empty public constructor
    }

    public static ClassFragment newInstance(LogicService service) {
        ClassFragment fragment = new ClassFragment();
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
        mView = inflater.inflate(R.layout.fragment_class, container, false);
        mManage = new ClassManage(this, mService);
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
        llTeachers = (LinearLayout) mView.findViewById(R.id.ll_class_teachers);
        rvPeopleList = (RecyclerView) mView.findViewById(R.id.rv_class_people_list);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_class_teachers:
                        mManage.goTeacherList();
                        break;
                }
            }
        };
        llTeachers.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {
        mProgressDialog = ProgressDialog.show(getActivity(), null, getString(msgId));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setRightTitle(String title) {

    }

    @Override
    public void showToastMsg(int msgId) {

    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public void showList(ArrayList<UserInfoBean> list) {
        if (null == cpla) {
            cpla = new ClassPeopleListAdapter(getActivity(), list);
            cpla.setIOnClickListener(new IOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    Log.e(TAG, MessageFormat.format("点击了第{0}个学生", position));
                    mManage.goDetailInfo(position);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            rvPeopleList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            rvPeopleList.setAdapter(cpla);
        } else {
            cpla.refreshData(list);
        }
    }

    @Override
    public ClassManage getClassManage() {
        return mManage;
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
