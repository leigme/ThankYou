package com.yhcloud.thankyou.module.index.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
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
import com.yhcloud.thankyou.adapter.FragmentViewPagerAdapter;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.comm.BaseActivity;
import com.yhcloud.thankyou.comm.ItemClinkListener;
import com.yhcloud.thankyou.module.index.manage.MainManage;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.utils.myview.NoScrollViewPager;
import com.yhcloud.thankyou.utils.myview.toprightmenu.TopRightMenu;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainActivityView,
        HomeFragment.OnFragmentInteractionListener, ClassFragment.OnFragmentInteractionListener,
        MineFragment.OnFragmentInteractionListener {

    private String TAG = getClass().getSimpleName();

    //视图控件
    private ImageView ivHeaderLeft, ivDrawerHeadimg, ivFooterHome, ivFooterClass, ivFooterMine;
    private TextView tvDraweUsername, tvDrawerClass, tvHeaderTitle, tvFooterHome, tvFooterClass, tvFooterMine;
    private DrawerLayout dlDrawer;
    private TopRightMenu mTopRightMenu;
    private RecyclerView rvDrawerList;
    private LinearLayout llHeaderLeft, llHeaderRight, llFooterHome, llFooterClass, llFooterMine;
    private NoScrollViewPager nsvpList;
    //适配器
    private ClassDrawerListAdapter cdla;
    private FragmentViewPagerAdapter mfvpa;
    //管理器
    private MainManage mManage;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        llHeaderLeft = findView(R.id.ll_header_left);
        ivHeaderLeft = findView(R.id.iv_header_left);
        tvHeaderTitle = findView(R.id.tv_header_title);
        llHeaderRight = findView(R.id.ll_header_right);
        dlDrawer = findView(R.id.dl_main_drawer);
        ivDrawerHeadimg = findView(R.id.iv_drawer_image);
        tvDraweUsername = findView(R.id.tv_drawer_username);
        tvDrawerClass = findView(R.id.tv_drawer_class);
        rvDrawerList = findView(R.id.rv_drawer_list);
        llFooterHome = findView(R.id.ll_footer_home);
        llFooterClass = findView(R.id.ll_footer_class);
        llFooterMine = findView(R.id.ll_footer_mine);
        ivFooterHome = findView(R.id.iv_footer_home);
        ivFooterClass = findView(R.id.iv_footer_class);
        ivFooterMine = findView(R.id.iv_footer_mine);
        tvFooterHome = findView(R.id.tv_footer_home);
        tvFooterClass = findView(R.id.tv_footer_class);
        tvFooterMine = findView(R.id.tv_footer_mine);
        nsvpList = findView(R.id.nsvp_main);
    }

    @Override
    public void initEvents() {
        setOnClick(llHeaderLeft);
        setOnClick(llHeaderRight);
        setOnClick(llFooterHome);
        setOnClick(llFooterClass);
        setOnClick(llFooterMine);
    }

    @Override
    public void initDatas() {
        mManage = new MainManage(this);
        ivHeaderLeft.setImageResource(R.mipmap.icon_default_avatar);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.ll_header_left:
                mManage.showDrawer();
                break;
            case R.id.ll_header_right:
                mManage.showTrm();
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

    @Override
    public void initFragments(ArrayList<Fragment> list) {
        if (null == mfvpa) {
            mfvpa = new FragmentViewPagerAdapter(getSupportFragmentManager(), list);
            nsvpList.setOffscreenPageLimit(list.size());
            nsvpList.setAdapter(mfvpa);
        }
    }

    //抽屉弹出框
    @Override
    public void showDrawer(final ArrayList<ClassInfoBean> classInfoBeen) {
        if (null == cdla) {
            cdla = new ClassDrawerListAdapter(this, classInfoBeen);
            cdla.setMyOnClickListener(new ItemClinkListener() {
                @Override
                public void OnItemClickListener(View view, int position) {
                    Log.e(TAG, "设置默认班级:" + position);
                    for (ClassInfoBean ci: classInfoBeen) {
                        ci.setSelected(false);
                    }
                    mManage.setDefaultClassId(classInfoBeen.get(position).getClassId());
                    mManage.setTitle(1);
                    mManage.setClassPeopleList(classInfoBeen.get(position).getClassId());
                    classInfoBeen.get(position).setSelected(true);
                    cdla.reflreshList(classInfoBeen);
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
            cdla.reflreshList(classInfoBeen);
        }
        dlDrawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void setDrawerHeadImg(String url) {
        Tools.GlideCircleImageUrl(this, url, ivDrawerHeadimg);
    }

    @Override
    public void setDrawerUsername(String username) {
        tvDraweUsername.setText(username);
    }

    @Override
    public void setDrawerClassname(String classname) {
        tvDrawerClass.setText(classname);
    }

    //底部控制器
    @Override
    public void showFragment(int i) {
        mManage.setTitle(i);
        resetFooterBtn();
        switch (i) {
            case 0:
                ivFooterHome.setImageResource(R.mipmap.icon_home);
                tvFooterHome.setTextColor(Color.parseColor("#68c04a"));
                mManage.setRightButton(false);
                break;
            case 1:
                ivFooterClass.setImageResource(R.mipmap.icon_class);
                tvFooterClass.setTextColor(Color.parseColor("#68c04a"));
                mManage.setRightButton(true);
                break;
            case 2:
                ivFooterMine.setImageResource(R.mipmap.icon_mine);
                tvFooterMine.setTextColor(Color.parseColor("#68c04a"));
                mManage.setRightButton(false);
                break;
        }
        nsvpList.setCurrentItem(i);
    }

    @Override
    public void setHeaderLeftImage(String url) {
        Tools.GlideCircleImageUrl(this, url, R.mipmap.default_photo, ivHeaderLeft);
    }

    @Override
    public void initHeaderRightButton(boolean showed) {
        if (showed) {
            llHeaderRight.setVisibility(View.VISIBLE);
        } else {
            llHeaderRight.setVisibility(View.INVISIBLE);
        }
        llHeaderRight.setClickable(showed);
    }

    //班级界面右上角弹出框
    @Override
    public void showTrm(final ArrayList<FunctionBean> list) {
        if (null == mTopRightMenu) {
            mTopRightMenu = new TopRightMenu(this);
            mTopRightMenu
                    .showIcon(true)     //显示菜单图标，默认为true
                    .dimBackground(true)           //背景变暗，默认为true
                    .needAnimationStyle(true)   //显示动画，默认为true
                    .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                    .addMenuList(list)
                    .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                        @Override
                        public void onMenuItemClick(int position) {
                            startActivity(list.get(position).getIntent());
                        }
                    })
                    .showAsDropDown(llHeaderRight);
        } else {
            mTopRightMenu.showAsDropDown(llHeaderRight);
        }
    }

    //初始化底部控制器图标状态
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

    //返回首页
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tools.print(TAG, MessageFormat.format("requestCode:{0}, resultCode:{1}", requestCode, resultCode));
        mManage.refreshFuncations();
    }

    Long firstClickTime = (long) 0;

    //物理返回按钮事件
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

    public void setTitle(String title) {
        if (null != tvHeaderTitle) {
            tvHeaderTitle.setText(title);
        }
    }

}
