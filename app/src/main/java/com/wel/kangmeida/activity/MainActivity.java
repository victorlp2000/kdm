package com.wel.kangmeida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wel.kangmeida.BaseApp;
import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.ch.MeasureActivity;
import com.wel.kangmeida.activity.ch.MemberActivity;
import com.wel.kangmeida.activity.ch.DeviceActivity;
import com.wel.kangmeida.activity.ch.SettingsActivity;
import com.wel.kangmeida.activity.ch.NewMemberActivity;
import com.wel.kangmeida.activity.ch.DoctorActivity;
import com.wel.kangmeida.fagment.MneuLeftFragment;
import com.wel.kangmeida.utils.AppManager;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup rgsTime;
    private ImageView ivTitle;
    private ImageView ivFenxiang;
    private SlidingMenu leftSlidingMenu;
    private Fragment mContent;
    private ImageView stateDetails;
    private Boolean tag = true;
    private LinearLayout llZhankai;
    private LinearLayout llState;
    private RelativeLayout rlCeliang;
    private RelativeLayout rlJiatingchengyuan;
    private RelativeLayout rlYishengziliao;
    private RelativeLayout rlShebeiliebiao;
    private RelativeLayout rlShezhi;
    private Button btnDenglv;
    private ImageView ivXinzang;
    private ImageView ivShuimian;
    private ImageView ivYundong;
    private RelativeLayout rlYibiaopan;
    private RelativeLayout rlYonghu;

    /**
     * 处理返回操作的变量
     */
    private long lastBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //侧滑页面
        initLeftRightSlidingMenu();
    }

    private void initLeftRightSlidingMenu() {
        //初始化
        leftSlidingMenu = new SlidingMenu(this);
        //设置侧滑界面在哪
        leftSlidingMenu.setMode(SlidingMenu.LEFT);
        //设置滑出位置
        leftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //设置ActionBar 是否跟随一起移动
        leftSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置侧滑菜单的宽度
        //获取屏幕的宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        leftSlidingMenu.setBehindOffset(width * 1 / 5);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_menu, new MneuLeftFragment()).commit();
        leftSlidingMenu.setMenu(R.layout.menu_left_layout);
        //侧滑菜单控件
        //用户名
        rlYonghu = (RelativeLayout) this.findViewById(R.id.rl_user);
        rlYonghu.setOnClickListener(this);
        //仪表盘
        rlYibiaopan = (RelativeLayout) this.findViewById(R.id.rl_yibiaopan);
        rlYibiaopan.setOnClickListener(this);
        //测量
        rlCeliang = (RelativeLayout) this.findViewById(R.id.rl_celiang);
        rlCeliang.setOnClickListener(this);
        //家庭成员
        rlJiatingchengyuan = (RelativeLayout) this.findViewById(R.id.rl_jiatingchenyuan);
        rlJiatingchengyuan.setOnClickListener(this);
        //医生资料
        rlYishengziliao = (RelativeLayout) this.findViewById(R.id.rl_yishengziliao);
        rlYishengziliao.setOnClickListener(this);
        //设备列表
        rlShebeiliebiao = (RelativeLayout) this.findViewById(R.id.rl_shebeiliebiao);
        rlShebeiliebiao.setOnClickListener(this);
        //设置
        rlShezhi = (RelativeLayout) this.findViewById(R.id.rl_shezhi);
        rlShezhi.setOnClickListener(this);
        //注销登录
        btnDenglv = (Button) this.findViewById(R.id.btn_zhuxiaodenglv);
        btnDenglv.setOnClickListener(this);

    }

    private void initView() {
        //初始化单选按钮
        rgsTime = (RadioGroup) this.findViewById(R.id.rgs_time);
        rgsTime.setBackgroundResource(R.drawable.rg_time_shape);
        //单选按钮事件
        rgsTime.setOnCheckedChangeListener(this);
        //单选按钮默认选择
        ((RadioButton) rgsTime.getChildAt(0)).setChecked(true);
        //初始化view
        ivTitle = (ImageView) this.findViewById(R.id.iv_item_title);
        ivTitle.setOnClickListener(this);
        //状态展开图片
        stateDetails = (ImageView) this.findViewById(R.id.iv_state_details);
        stateDetails.setOnClickListener(this);
        //展开的布局
        llState = (LinearLayout) this.findViewById(R.id.rl_state);
        llZhankai = (LinearLayout) this.findViewById(R.id.ll_zhankai);
        //心脏
        ivXinzang = (ImageView) this.findViewById(R.id.iv_state_heart);
        ivXinzang.setOnClickListener(this);
        //睡眠
        ivShuimian = (ImageView) this.findViewById(R.id.iv_state_sleep);
        ivShuimian.setOnClickListener(this);
        //运动
        ivYundong = (ImageView) this.findViewById(R.id.iv_state_sport);
        ivYundong.setOnClickListener(this);
        //分享
        ivFenxiang = (ImageView) this.findViewById(R.id.iv_item_fenxiang);
        ivFenxiang.setOnClickListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_item_title:
                //侧滑
                leftSlidingMenu.showMenu();
                break;
            case R.id.iv_item_fenxiang:
                //分享
                intent = new Intent(MainActivity.this, ShareActivity.class);
                break;
            case R.id.iv_state_details:
                //首页展开切换
                if (tag.equals(false)) {
                    llZhankai.setVisibility(View.GONE);
                    llState.setVisibility(View.VISIBLE);
                    tag = true;
                } else {
                    llState.setVisibility(View.GONE);
                    llZhankai.setVisibility(View.VISIBLE);
                    tag = false;
                }
                break;
            case R.id.rl_yibiaopan:
                //仪表盘
                intent = new Intent(MainActivity.this,MainActivity.class);
                finish();
                break;
            case R.id.rl_user:
                //用户
                intent = new Intent(MainActivity.this,NewMemberActivity.class);
                break;
            case R.id.rl_celiang:
                //侧滑菜单测量的跳转
                intent = new Intent(MainActivity.this, MeasureActivity.class);
                break;
            case R.id.rl_jiatingchenyuan:
                //侧滑菜单家庭成员的跳转
                intent = new Intent(MainActivity.this, MemberActivity.class);
                break;
            case R.id.rl_yishengziliao:
                //侧滑菜单医生的跳转
                intent = new Intent(MainActivity.this, DoctorActivity.class);
                break;
            case R.id.rl_shebeiliebiao:
                //侧滑菜单设备列表的跳转
                intent = new Intent(MainActivity.this, DeviceActivity.class);
                break;
            case R.id.rl_shezhi:
                //设置
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                break;
            case R.id.btn_zhuxiaodenglv:
                //注销登录
                intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                break;
            case R.id.iv_state_heart:
                //心脏详细
                intent = new Intent(MainActivity.this, HeartActivity.class);
                break;
            case R.id.iv_state_sleep:
                //睡眠详细
                intent = new Intent(MainActivity.this, SleepActivity.class);
                break;
            case R.id.iv_state_sport:
                //运动详细
                intent = new Intent(MainActivity.this, SportActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackTime > 1 * 1000) {
            lastBackTime = currentTime;
            BaseApp.showToast(getString(R.string.tuichu));
        } else {
            AppManager.getAppManager().AppExit(BaseApp.getInstance());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishAllActivity();
    }
}
