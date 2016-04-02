package com.wel.kangmeida.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.ch.NewMemberActivity;
import com.wel.kangmeida.adapter.HobbyAdapter;
import com.wel.kangmeida.utils.AppConstat;

public class HobbyActivity extends BaseActivity implements View.OnClickListener {
    private GridView gview;
    private RelativeLayout rlFanhui;
    private HobbyAdapter hobbyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);
        initView();
    }


    private void initView() {
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
        gview = (GridView) this.findViewById(R.id.gv_hobby);
        hobbyAdapter = new HobbyAdapter(this);
        gview.setAdapter(hobbyAdapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                setResult(AppConstat.HOBBY, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_fanhui:
                finish();
                break;
        }
    }
}
