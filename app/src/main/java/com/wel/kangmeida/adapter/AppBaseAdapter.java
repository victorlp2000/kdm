package com.wel.kangmeida.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by TZJ on 2015/10/10.
 */
public abstract class AppBaseAdapter<T> extends BaseAdapter {

        protected LayoutInflater mInflater;
        protected Context mContext;
        protected List<T> mList;
        protected final int mItemLayoutId;

        public AppBaseAdapter(Context context, List<T> mList, int itemLayoutId)
        {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
            this.mList = mList;
            this.mItemLayoutId = itemLayoutId;
        }

        @Override
        public int getCount()
        {
            return mList.size();
        }

        @Override
        public T getItem(int position)
        {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolders viewHolder = getViewHolder(position, convertView,
                    parent);
            convert(viewHolder, getItem(position),position);
            return viewHolder.getConvertView();

        }

    public abstract void convert(ViewHolders holder, T item,int position);

    private ViewHolders getViewHolder(int position, View convertView,
                                      ViewGroup parent)
    {
        return ViewHolders.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

}



