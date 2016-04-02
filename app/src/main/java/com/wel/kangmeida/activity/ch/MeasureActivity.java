package com.wel.kangmeida.activity.ch;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.wel.kangmeida.R;
import com.wel.kangmeida.activity.BaseActivity;
import com.wel.kangmeida.adapter.MemberListviewAdapter;
import com.wel.kangmeida.bean.DbChengyuan;

import java.util.ArrayList;
import java.util.List;

public class MeasureActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivFangke;
    private RelativeLayout rlFanhui;
    private TextView tvFon;
    private TextView tvShi;
    private ListView listView;
    private MemberListviewAdapter lvAdapter;
    private List<DbChengyuan> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        initView();
    }

    private void initView() {
        //访客
        ivFangke = (ImageView) this.findViewById(R.id.iv_fangke);
        ivFangke.setOnClickListener(this);
        //返回
        rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(this);
        //赋值
        list = Hawk.get("cyList");
        if (list == null) {
            list = new ArrayList<>();
        }
        listView = (ListView) this.findViewById(R.id.lv_chengyuan);
        lvAdapter = new MemberListviewAdapter(this, list, R.layout.listview_member_item);
        listView.setAdapter(lvAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fangke:
                //此处直接new一个Dialog对象出来，在实例化的时候传入主题
                final Dialog dialog = new Dialog(MeasureActivity.this, R.style.Dialog);
                //设置它的ContentView
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(false);
                dialog.show();
                //dialog是否相应事件
                tvFon = (TextView) dialog.findViewById(R.id.tv_fon);
                tvFon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tvShi = (TextView) dialog.findViewById(R.id.tv_shi);
                tvShi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MeasureActivity.this, NewDeviceActivity.class);
                        //关闭所有activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.rl_fanhui:
                //返回
                finish();
                break;
        }
    }
}
