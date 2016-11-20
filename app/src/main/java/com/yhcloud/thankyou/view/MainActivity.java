package com.yhcloud.thankyou.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.ClassDrawerListAdapter;
import com.yhcloud.thankyou.bean.ClassInfo;
import com.yhcloud.thankyou.mInterface.MyOnClickListener;
import com.yhcloud.thankyou.manage.MainManage;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.utils.myview.NoScrollViewPager;

import java.util.ArrayList;

import static com.yhcloud.thankyou.R.id.iv_header_left;

public class MainActivity extends AppCompatActivity implements IMainView,
        HomeFragment.OnFragmentInteractionListener, ClassFragment.OnFragmentInteractionListener,
        MineFragment.OnFragmentInteractionListener {

    private String TAG = getClass().getSimpleName();

    private MainManage mManage = new MainManage(this);
    private ImageView ivHeaderLeft, ivHeaderRight, ivFooterHome, ivFooterClass, ivFooterMine;
    private TextView tvHeaderTitle, tvFooterHome, tvFooterClass, tvFooterMine;
    private DrawerLayout dlDrawer;
    private RecyclerView rvDrawerList;

    private LinearLayout llHeaderLeft, llHeaderRight, llFooterHome, llFooterClass, llFooterMine;

    private ClassDrawerListAdapter cdla;
    private ArrayList<Fragment> mFragments;
    private NoScrollViewPager nsvpList;
    private MyFragmentViewPagerAdapter mfvpa;

    private ArrayList<ClassInfo> classInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
        showFragment(0);
    }

    private void initView() {
        llHeaderLeft = (LinearLayout) findViewById(R.id.ll_header_left);
        ivHeaderLeft = (ImageView) findViewById(iv_header_left);
        tvHeaderTitle = (TextView) findViewById(R.id.tv_header_title);
        llHeaderRight = (LinearLayout) findViewById(R.id.ll_header_right);
        dlDrawer = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        rvDrawerList = (RecyclerView) findViewById(R.id.rv_drawer_list);
        llFooterHome = (LinearLayout) findViewById(R.id.ll_footer_home);
        llFooterClass = (LinearLayout) findViewById(R.id.ll_footer_class);
        llFooterMine = (LinearLayout) findViewById(R.id.ll_footer_mine);
        ivFooterHome = (ImageView) findViewById(R.id.iv_footer_home);
        ivFooterClass = (ImageView) findViewById(R.id.iv_footer_class);
        ivFooterMine = (ImageView) findViewById(R.id.iv_footer_mine);
        tvFooterHome = (TextView) findViewById(R.id.tv_footer_home);
        tvFooterClass = (TextView) findViewById(R.id.tv_footer_class);
        tvFooterMine = (TextView) findViewById(R.id.tv_footer_mine);
        nsvpList = (NoScrollViewPager) findViewById(R.id.nsvp_main);
    }

    private void initData(){
        ivHeaderLeft.setImageResource(R.mipmap.icon_default_avatar);
        mFragments = new ArrayList<>();
        if (null != getIntent()) {
            Bundle bundle = getIntent().getExtras();
            classInfos = (ArrayList<ClassInfo>) bundle.getSerializable("ClassInfos");
        }
    }

    private void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ll_header_left:
//                        mManage.getClassInfo("");
                        showDrawer(classInfos);
                        break;
                    case R.id.ll_footer_home:
                        showFragment(0);
                        break;
                    case R.id.ll_footer_class:
                        showFragment(1);
                        break;
                    case R.id.ll_footer_mine:
                        showFragment(2);
                        break;
                }
            }
        };
        llHeaderLeft.setOnClickListener(myOnClickListener);
        llFooterHome.setOnClickListener(myOnClickListener);
        llFooterClass.setOnClickListener(myOnClickListener);
        llFooterMine.setOnClickListener(myOnClickListener);
    }

    @Override
    public void setTitle(String str) {
        tvHeaderTitle.setText(str);
    }

    @Override
    public void setDrawer() {

    }

    @Override
    public void showDrawer(final ArrayList<ClassInfo> classInfos) {
        if (null == cdla) {
            cdla = new ClassDrawerListAdapter(this, classInfos);
            cdla.setMyOnClickListener(new MyOnClickListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    Log.e(TAG, "设置默认班级:" + position);
                    for (ClassInfo ci: classInfos) {
                        ci.setSelected(false);
                    }
                    classInfos.get(position).setSelected(true);
                    cdla.reflreshList(classInfos);
                    dlDrawer.closeDrawer(Gravity.LEFT);
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvDrawerList.setAdapter(cdla);
            rvDrawerList.setLayoutManager(layoutManager);
        } else {
            cdla.reflreshList(classInfos);
        }
        dlDrawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void showFragment(int i) {
        resetFooterBtn();
        if (null == mfvpa) {
            Fragment hFragment = HomeFragment.newInstance("", "");
            Fragment cFragment = ClassFragment.newInstance("", "");
            Fragment mFragment = MineFragment.newInstance("", "");
            mFragments.add(hFragment);
            mFragments.add(cFragment);
            mFragments.add(mFragment);
            mfvpa = new MyFragmentViewPagerAdapter(getSupportFragmentManager());
            nsvpList.setAdapter(mfvpa);
        }
        switch (i) {
            case 0:
                ivFooterHome.setImageResource(R.mipmap.icon_home);
                tvFooterHome.setTextColor(Color.parseColor("#68c04a"));
                break;
            case 1:
                ivFooterClass.setImageResource(R.mipmap.icon_class);
                tvFooterClass.setTextColor(Color.parseColor("#68c04a"));
                break;
            case 2:
                ivFooterMine.setImageResource(R.mipmap.icon_mine);
                tvFooterMine.setTextColor(Color.parseColor("#68c04a"));
                break;
        }
        nsvpList.setCurrentItem(i);

    }

    private void resetFooterBtn() {
        ivFooterHome.setImageResource(R.mipmap.icon_home_un);
        ivFooterClass.setImageResource(R.mipmap.icon_class_un);
        ivFooterMine.setImageResource(R.mipmap.icon_mine_un);

        tvFooterHome.setTextColor(Color.parseColor("#8E908D"));
        tvFooterClass.setTextColor(Color.parseColor("#8E908D"));
        tvFooterMine.setTextColor(Color.parseColor("#8E908D"));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    Long firstClickTime = (long) 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstClickTime <= 2000) {
            super.onBackPressed();
            return;
        }
        firstClickTime = System.currentTimeMillis();
        MyToast.showToast(this, "再按一次返回键退出应用");
        return;
    }
}
