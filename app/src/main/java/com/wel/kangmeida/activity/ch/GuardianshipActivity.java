package com.wel.kangmeida.activity.ch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.adapter.GuardianshipListviewAdapter;
import com.wel.kangmeida.bean.GuardianshipListViewBean;

import java.util.ArrayList;
import java.util.List;

public class GuardianshipActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;
    private ListView listView;
    private List<GuardianshipListViewBean> list = new ArrayList<>();
    private GuardianshipListviewAdapter adapter;
    private Button delete;
    private List<Integer> intList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardianship);
        initView();

        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            GuardianshipListViewBean bean = new GuardianshipListViewBean();
            bean.setName("admin00"+i);
            if(i == 0){
                bean.setCheck(true);
            }else{
                bean.setCheck(false);
            }
            list.add(bean);
        }
        adapter = new GuardianshipListviewAdapter(this,list,R.layout.item_listview_guardianship);
        listView.setAdapter(adapter);
    }

    private void initView() {
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        listView = (ListView) findViewById(R.id.listview_szjianhu);
        delete = (Button) findViewById(R.id.btn_shanchu);
        rlFanhui.setOnClickListener(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    GuardianshipListViewBean bean = list.get(i);
                    if(bean.isCheck()){
                        list.remove(bean);
                        i --;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
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
