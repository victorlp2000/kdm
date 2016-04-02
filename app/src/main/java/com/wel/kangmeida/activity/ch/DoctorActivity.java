package com.wel.kangmeida.activity.ch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.orhanobut.hawk.Hawk;
import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.adapter.DoctorListviewAdapter;
import com.wel.kangmeida.bean.DbDoctor;
import com.wel.kangmeida.utils.AppConstat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by TZJ on 2016/3/14.
 */
public class DoctorActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvXinZeng;
    private RelativeLayout rlFanHui;
    private RelativeLayout rlNew;
    private ListView lvDoctor;
    private DoctorListviewAdapter lvAdapter;
    private List<DbDoctor> lvList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        initView();

    }

    private void initView() {
        lvXinZeng = (ListView) this.findViewById(R.id.lv_xinzeng);
        rlFanHui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanHui.setOnClickListener(this);
        rlNew = (RelativeLayout) this.findViewById(R.id.rl_xinzeng);
        rlNew.setOnClickListener(this);
        lvDoctor = (ListView) this.findViewById(R.id.lv_xinzeng);

        //每次都给医生列表赋值
        lvList = Hawk.get("doctorList");
        if (lvList == null) {
            lvList = new ArrayList<>();
        }
        lvAdapter = new DoctorListviewAdapter(this, lvList, R.layout.listview_doctor_item);
        lvDoctor.setAdapter(lvAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_fanhui:
                finish();
                break;
            case R.id.rl_xinzeng:
                //新增医生
                intent = new Intent(DoctorActivity.this, NewDoctorActivity.class);
                startActivityForResult(intent, AppConstat.DOCTOR_NEW_RESULT_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstat.DOCTOR_NEW_RESULT_CODE) {
            lvList.clear();
            if (Hawk.get("doctorList") != null) {
                lvList.addAll(Hawk.<Collection<? extends DbDoctor>>get("doctorList"));
                lvAdapter.notifyDataSetChanged();
            }

        }
    }
}
