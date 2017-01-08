package com.yhcloud.thankyou.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.adapter.ClassDrawerListAdapter;
import com.yhcloud.thankyou.adapter.FragmentViewPagerAdapter;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.mInterface.IOnClickListener;
import com.yhcloud.thankyou.manage.MainManage;
import com.yhcloud.thankyou.utils.ServiceAPI;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.utils.myview.NoScrollViewPager;
import com.yhcloud.thankyou.utils.myview.PopupMenu;
import com.yhcloud.thankyou.utils.myview.toprightmenu.TopRightMenu;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.yhcloud.thankyou.R.id.iv_header_left;

public class MainActivity extends AppCompatActivity implements IMainView,
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
    private ProgressDialog mProgressDialog;
    //适配器
    private ClassDrawerListAdapter cdla;
    private FragmentViewPagerAdapter mfvpa;
    //管理器
    private MainManage mManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManage = new MainManage(this);
    }

    @Override
    public void initView() {
        llHeaderLeft = (LinearLayout) findViewById(R.id.ll_header_left);
        ivHeaderLeft = (ImageView) findViewById(iv_header_left);
        tvHeaderTitle = (TextView) findViewById(R.id.tv_header_title);
        llHeaderRight = (LinearLayout) findViewById(R.id.ll_header_right);
        dlDrawer = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        ivDrawerHeadimg = (ImageView) findViewById(R.id.iv_drawer_image);
        tvDraweUsername = (TextView) findViewById(R.id.tv_drawer_username);
        tvDrawerClass = (TextView) findViewById(R.id.tv_drawer_class);
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

    @Override
    public void initData(){
        ivHeaderLeft.setImageResource(R.mipmap.icon_default_avatar);
    }

    @Override
    public void initEvent() {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        };
        llHeaderLeft.setOnClickListener(myOnClickListener);
        llHeaderRight.setOnClickListener(myOnClickListener);
        llFooterHome.setOnClickListener(myOnClickListener);
        llFooterClass.setOnClickListener(myOnClickListener);
        llFooterMine.setOnClickListener(myOnClickListener);
    }

    @Override
    public void showDefault(boolean showed) {

    }

    @Override
    public void showLoading(int msgId) {
        mProgressDialog = ProgressDialog.show(this, null, getString(msgId));
    }

    @Override
    public void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setTitle(String str) {
        tvHeaderTitle.setText(str);
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
    public void initFragments(ArrayList<Fragment> list) {
        if (null == mfvpa) {
            mfvpa = new FragmentViewPagerAdapter(getSupportFragmentManager(), list);
            nsvpList.setOffscreenPageLimit(list.size());
            nsvpList.setAdapter(mfvpa);
        }
    }

    @Override
    public void showDrawer(final ArrayList<ClassInfoBean> classInfoBeen) {
        if (null == cdla) {
            cdla = new ClassDrawerListAdapter(this, classInfoBeen);
            cdla.setMyOnClickListener(new IOnClickListener() {
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
//            setHeaderRightImage(R.mipmap.icon_add_menu);
        } else {
            llHeaderRight.setVisibility(View.INVISIBLE);
        }
        llHeaderRight.setClickable(showed);
    }

//    @Override
//    public void setHeaderRightImage(int resId) {
//        Glide.with(this).load(resId).into(ivHeaderRight);
//    }

    @Override
    public void showTrm(final ArrayList<FunctionBean> list) {
        if (null == mTopRightMenu) {
            mTopRightMenu = new TopRightMenu(this);
            mTopRightMenu.setHeight(450)
                    .setWidth(320)
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
                    .showAsDropDown(llHeaderRight, -200, 0);
        } else {
            mTopRightMenu.showAsDropDown(llHeaderRight, -200, 0);
        }
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
