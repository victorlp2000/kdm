package com.wel.kangmeida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wel.kangmeida.R;
import com.wel.kangmeida.adapter.WelcomPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WelcomActivity extends BaseActivity implements View.OnClickListener {
    private List<View> list = new ArrayList<>();
    private WelcomPagerAdapter adapter;
    private ViewPager vp;
    private TextView tvTiaoguo;
    private Button denglv;
    private Button zhuce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        vp = (ViewPager) findViewById(R.id.vp_welcome);
        addView();
    }


    private void addView() {

        for (int i = 0; i < 4; i++) {
            View view = getLayoutInflater().inflate(R.layout.layout_welcome_item, null);
            tvTiaoguo = (TextView) view.findViewById(R.id.tv_tiaoguo);
            tvTiaoguo.setOnClickListener(this);
            denglv = (Button) view.findViewById(R.id.btn_welcome_login);
            denglv.setOnClickListener(this);
            zhuce = (Button) view.findViewById(R.id.btn_welcome_register);
            zhuce.setOnClickListener(this);
            list.add(view);
        }
        adapter = new WelcomPagerAdapter(list);
        vp.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_tiaoguo:
                intent = new Intent(WelcomActivity.this, LoginActivity.class);
                finish();
                break;
            case R.id.btn_welcome_login:
                intent = new Intent(WelcomActivity.this, LoginActivity.class);
                finish();
                break;
            case R.id.btn_welcome_register:
                intent = new Intent(WelcomActivity.this, RegisterActivity.class);
                finish();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
