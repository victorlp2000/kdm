package com.wel.kangmeida.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wel.kangmeida.R;
import com.wel.kangmeida.bean.DbDoctor;
import com.wel.kangmeida.utils.AppConstat;

import java.util.List;

/**
 * Created by TZJ on 2016/3/17.
 */
public class DoctorListviewAdapter extends AppBaseAdapter<DbDoctor> {


    public DoctorListviewAdapter(Context context, List<DbDoctor> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolders holder, DbDoctor item, int position) {
        ((TextView) holder.getView(R.id.tv_yishengname)).setText(item.getDoctorname());
        ((TextView) holder.getView(R.id.tv_zhuanke)).setText(item.getExpert());
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
