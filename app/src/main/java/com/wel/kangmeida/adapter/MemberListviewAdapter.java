package com.wel.kangmeida.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wel.kangmeida.R;
import com.wel.kangmeida.bean.DbChengyuan;
import com.wel.kangmeida.utils.AppConstat;

import java.util.List;

/**
 * Created by TZJ on 2016/3/17.
 */
public class MemberListviewAdapter extends AppBaseAdapter<DbChengyuan> {


    public MemberListviewAdapter(Context context, List<DbChengyuan> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolders holder, DbChengyuan item, int position) {
        ((TextView) holder.getView(R.id.tv_cy_name)).setText(item.getCyName());
        ((TextView) holder.getView(R.id.tv_cy_sex)).setText(item.getCySex() + "  ");
        int age = Integer.parseInt(item.getCyAge().substring(0, 4));
        ((TextView) holder.getView(R.id.tv_cy_age)).setText(String.valueOf(2016 - age) + "岁");
        if (position % 4 == 0) {
            ((ImageView) holder.getView(R.id.iv_yuan)).setImageResource(AppConstat.circle[0]);
        }
        if (position % 4 == 1) {
            ((ImageView) holder.getView(R.id.iv_yuan)).setImageResource(AppConstat.circle[1]);
        }
        if (position % 4 == 2) {
            ((ImageView) holder.getView(R.id.iv_yuan)).setImageResource(AppConstat.circle[2]);
        }
        if (position % 4 == 3) {
            ((ImageView) holder.getView(R.id.iv_yuan)).setImageResource(AppConstat.circle[3]);
        }
    }
}
