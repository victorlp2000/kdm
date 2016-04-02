package com.wel.kangmeida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wel.kangmeida.BaseApp;
import com.wel.kangmeida.R;
import com.wel.kangmeida.utils.AppManager;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btnDl;
    private TextView tvWj;
    private EditText etUser;
    private EditText etPs;

    /**
     * 处理返回操作的变量
     */
    private long lastBackTime;
    private RelativeLayout rlXinjian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        //登录
        btnDl = (Button) this.findViewById(R.id.btn_denglv);
        btnDl.setOnClickListener(this);
        //忘记密码
        tvWj = (TextView) this.findViewById(R.id.tv_wangji);
        tvWj.setText(Html.fromHtml("<u>" + "忘记密码" + "</u>"));
        tvWj.setOnClickListener(this);
        //新建账号
        rlXinjian = (RelativeLayout) this.findViewById(R.id.rl_xinjian);
        rlXinjian.setOnClickListener(this);
        etUser = (EditText) this.findViewById(R.id.et_user);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_denglv:
                //登录
                intent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                break;
            case R.id.tv_wangji:
                //忘记密码
                intent = new Intent(LoginActivity.this, LoginPassWordActivity.class);
                break;
            case R.id.rl_xinjian:
                //新建账号
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackTime > 1000) {
            lastBackTime = currentTime;
            BaseApp.showToast(getString(R.string.tuichu));
        } else {
            AppManager.getAppManager().AppExit(BaseApp.getInstance());
        }
    }
}
