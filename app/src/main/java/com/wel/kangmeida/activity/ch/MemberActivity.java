package com.wel.kangmeida.activity.ch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.orhanobut.hawk.Hawk;
import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.adapter.MemberListviewAdapter;
import com.wel.kangmeida.bean.DbChengyuan;
import com.wel.kangmeida.utils.AppConstat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemberActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlFanhui;
    private RelativeLayout rlNew;
    private ListView lvMember;
    private List<DbChengyuan> lvList;
    private MemberListviewAdapter lvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        initView();
    }

    private void initView() {
        //返回
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
        //新增
        rlNew = (RelativeLayout) this.findViewById(R.id.rl_xinzeng);
        rlNew.setOnClickListener(this);
        //列表
        lvMember = (ListView) this.findViewById(R.id.lv_xinzeng);

        //每次都给成员列表赋值
        lvList = Hawk.get("cyList");
        if (lvList == null) {
            lvList = new ArrayList<>();
        }
        lvAdapter = new MemberListviewAdapter(this, lvList, R.layout.listview_member_item);
        lvMember.setAdapter(lvAdapter);

    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //返回
            case R.id.rl_fanhui:
                finish();
                break;
            case R.id.rl_xinzeng:
                //新增
                intent = new Intent(MemberActivity.this, NewMemberActivity.class);
                startActivityForResult(intent, AppConstat.CY_NEW_RESULT_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstat.CY_NEW_RESULT_CODE) {
            lvList.clear();
            if (Hawk.get("cyList") != null) {
                lvList.addAll(Hawk.<Collection<? extends DbChengyuan>>get("cyList"));
                lvAdapter.notifyDataSetChanged();
            }
        }
    }
}
