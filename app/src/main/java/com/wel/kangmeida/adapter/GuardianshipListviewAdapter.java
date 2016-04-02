package com.wel.kangmeida.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wel.kangmeida.R;
import com.wel.kangmeida.bean.GuardianshipListViewBean;

import java.util.List;

/**
 * Created by TZJ on 2016/3/17.
 */
public class GuardianshipListviewAdapter extends AppBaseAdapter<GuardianshipListViewBean> {
    private List<GuardianshipListViewBean> list;

    public List<GuardianshipListViewBean> getList() {
        return list;
    }

    public void setList(List<GuardianshipListViewBean> list) {
        this.list = list;
    }

    public GuardianshipListviewAdapter(Context context, List<GuardianshipListViewBean> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
        this.list = mList;
    }

    @Override
    public void convert(ViewHolders holder, GuardianshipListViewBean item, final int position) {
        CheckBox cb = holder.getView(R.id.cb_szjianting_check);
        ((TextView)holder.getView(R.id.tv_szjianhu_name)).setText(item.getName());
        //对CheckBox进行事件监听
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.get(position).setCheck(b);
            }
        });
        cb.setChecked(item.isCheck());
    }
}
