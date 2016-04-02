package com.wel.kangmeida.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wel.kangmeida.R;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button btnZhuce;
    private RelativeLayout rlYiyou;
    private TextView tvYanzheng;
    private RelativeLayout rlQueren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
    }

    private void initview() {
        //注册
        btnZhuce = (Button) this.findViewById(R.id.btn_zhuce);
        btnZhuce.setOnClickListener(this);
        //已有账号
        rlYiyou = (RelativeLayout) this.findViewById(R.id.rl_yiyou);
        rlYiyou.setOnClickListener(this);
        //获取验证码
        tvYanzheng = (TextView) this.findViewById(R.id.tv_huoquyanzhengma);
        tvYanzheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_zhuce:
                //注册
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                break;
            case R.id.rl_yiyou:
                //已有账号
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                break;
            case R.id.tv_huoquyanzhengma:
                //验证码此处直接new一个Dialog对象出来，在实例化的时候传入主题
                final Dialog dialog = new Dialog(RegisterActivity.this, R.style.Dialog);
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
