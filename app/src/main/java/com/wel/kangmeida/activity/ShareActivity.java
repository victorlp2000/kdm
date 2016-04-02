package com.wel.kangmeida.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;

public class ShareActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_fanhui:
                finish();
                break;
        }
    }
}
