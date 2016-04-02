package com.wel.kangmeida.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wel.kangmeida.utils.AppConstat;

/**
 * Created by admin on 2016/3/28.
 */
public class HobbyAdapter extends BaseAdapter {
    //定义Context
    private Context mContext;
    public HobbyAdapter(Context c)
    {
        mContext=c;
    }
    @Override
    public int getCount() {
        return AppConstat.icon.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mimageView;
        if(convertView==null)
        {
            //给Imageview设置资源
            mimageView = new ImageView(mContext);
        }else
        {
            mimageView=(ImageView) convertView;
        }
        mimageView.setImageResource(AppConstat.icon[position]);
        return mimageView;
    }
    }

