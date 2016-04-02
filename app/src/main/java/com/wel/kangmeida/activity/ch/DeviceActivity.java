package com.wel.kangmeida.activity.ch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;

public class DeviceActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;
    private RelativeLayout rlAnzhuang;
    private RelativeLayout rlTianjia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initView();
    }

    private void initView() {
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlAnzhuang = (RelativeLayout) this.findViewById(R.id.rl_anzhuang);
        rlTianjia = (RelativeLayout) this.findViewById(R.id.rl_tianjia);
        rlFanhui.setOnClickListener(this);
        rlAnzhuang.setOnClickListener(this);
        rlTianjia.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_fanhui:
                //返回
                finish();
                break;
            case R.id.rl_tianjia:
                //添加
                intent = new Intent(DeviceActivity.this, NewDeviceActivity.class);
                break;
            case R.id.rl_anzhuang:
                //添加
                intent = new Intent(DeviceActivity.this, NewDeviceActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
