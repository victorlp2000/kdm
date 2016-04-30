package com.wel.kangmeida.activity.ch;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.tw.TWWelcomeActivity;
import com.wel.kangmeida.tz.TZWelcomeActivity;
import com.wel.kangmeida.xy.XYWelcomeActivity;

public class NewDeviceActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "NewDeviceActivity";
    private RelativeLayout rlFanhui;
    private RelativeLayout rlTw;
    private RelativeLayout rlXy;
    private RelativeLayout rlTz;
    private RelativeLayout rlEcg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdevice);
        initView();
    }

    private void initView() {
        //返回
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
        //体温计
        rlTw = (RelativeLayout) this.findViewById(R.id.rl_tw);
        rlXy = (RelativeLayout) this.findViewById(R.id.rl_xy);
        rlTz = (RelativeLayout) this.findViewById(R.id.rl_tz);
        rlEcg = (RelativeLayout) this.findViewById(R.id.rl_ecg);
        rlTw.setOnClickListener(this);
        rlXy.setOnClickListener(this);
        rlTz.setOnClickListener(this);
        rlEcg.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_fanhui:
                //返回
                finish();
                break;
            case R.id.rl_tw:
                intent = new Intent(this, TWWelcomeActivity.class);
                break;
            case R.id.rl_xy:
                intent = new Intent(this, XYWelcomeActivity.class);
                break;
            case R.id.rl_tz:
                intent = new Intent(this, TZWelcomeActivity.class);
                break;
            case R.id.rl_ecg:
                Log.d(TAG, "launch ECG app");
                String ecgPackageName = "com.example.ti.ble.sensortag";
                String ecgActivity = "com.example.ti.ble.sensortag.MainActivity";
                intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName(ecgPackageName, ecgActivity));
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
