package com.wel.kangmeida.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wel.kangmeida.utils.AppManager;


/**
 * Created by ycdyus on 2015/10/9.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    /**
     * 返回监听
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity();
    }
}
