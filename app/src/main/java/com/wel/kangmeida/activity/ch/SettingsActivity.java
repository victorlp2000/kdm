package com.wel.kangmeida.activity.ch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.activity.LoginPassWordActivity;
import com.wel.kangmeida.utils.DanweiPicPopupWindow;
import com.wel.kangmeida.utils.SelectPicPopupWindow;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;
    private RelativeLayout rlXiugai;
    private RelativeLayout rlJianhu;
    private RelativeLayout rlGuanyu;
    private RelativeLayout rlDanwei;
    private RelativeLayout rlYuanyan;
    //自定义的弹出框类
    private SelectPicPopupWindow menuWindow;
    private DanweiPicPopupWindow danweiPicPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    private void initView() {
        //返回
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
        //修改注册密码
        rlXiugai = (RelativeLayout) this.findViewById(R.id.rl_xiugai);
        rlXiugai.setOnClickListener(this);
        //监护人
        rlJianhu = (RelativeLayout) this.findViewById(R.id.rl_jianhu);
        rlJianhu.setOnClickListener(this);
        //关于我们
        rlGuanyu = (RelativeLayout) this.findViewById(R.id.rl_guanyu);
        rlGuanyu.setOnClickListener(this);
        //单位切换
        rlDanwei = (RelativeLayout) this.findViewById(R.id.rl_danwei);
        rlDanwei.setOnClickListener(this);
        //语言切换
        rlYuanyan = (RelativeLayout) this.findViewById(R.id.rl_yuyan);
        rlYuanyan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.rl_fanhui:
                //返回
                finish();
                break;
            case R.id.rl_xiugai:
                //修改注册密码
                intent = new Intent(SettingsActivity.this, LoginPassWordActivity.class);
                break;
            case R.id.rl_jianhu:
                //修改注册密码
                intent = new Intent(SettingsActivity.this, GuardianshipActivity.class);
                break;
            case R.id.rl_guanyu:
                //关于我们
                intent = new Intent(SettingsActivity.this, SettingsMeActivity.class);
                break;
            case R.id.rl_danwei:
                //单位切换
                //实例化SelectPicPopupWindow
                danweiPicPopupWindow = new DanweiPicPopupWindow(SettingsActivity.this, itemsOnClick);
                //显示窗口
                danweiPicPopupWindow.showAtLocation(SettingsActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.rl_yuyan:
                //语言切换
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(SettingsActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(SettingsActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            danweiPicPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
                default:
                    break;
            }


        }

    };
}
