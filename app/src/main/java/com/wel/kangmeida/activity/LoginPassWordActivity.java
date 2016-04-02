package com.wel.kangmeida.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wel.kangmeida.R;

public class LoginPassWordActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;
    private Button btnTijiao;
    private TextView tvYanzheng;
    private RelativeLayout rlQueren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpassword);
        initView();
    }

    private void initView() {
        //返回
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        btnTijiao = (Button) this.findViewById(R.id.btn_tijiao);
        rlFanhui.setOnClickListener(this);
        btnTijiao.setOnClickListener(this);
        //验证码
        tvYanzheng = (TextView) this.findViewById(R.id.tv_huoquyanzhengma);
        tvYanzheng.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_fanhui:
                //返回
                finish();
                break;
            case R.id.btn_tijiao:
                //提交
                intent = new Intent(LoginPassWordActivity.this, LoginActivity.class);
                //关闭所有activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                break;
            case R.id.tv_huoquyanzhengma:
                //验证码此处直接new一个Dialog对象出来，在实例化的时候传入主题
                final Dialog dialog = new Dialog(LoginPassWordActivity.this, R.style.Dialog);
                //设置它的ContentView
                dialog.setContentView(R.layout.dialog_yanzhengma);
                dialog.setCancelable(false);
                dialog.show();
                //dialog是否相应事件
                rlQueren = (RelativeLayout) dialog.findViewById(R.id.rl_queren);
                rlQueren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }


}
