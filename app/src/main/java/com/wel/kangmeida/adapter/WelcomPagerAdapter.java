package com.wel.kangmeida.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wel.kangmeida.R;

import java.util.List;

/**
 * Created by TZJ on 2015/9/23.
 */
public class WelcomPagerAdapter extends PagerAdapter{
    private  List<View> listPager;

    private int[] res = new int[]{R.mipmap.wc1,R.mipmap.wc2,R.mipmap.wc3,R.mipmap.wc4};
    public WelcomPagerAdapter(List<View> listPager) {
        this.listPager = listPager;
    }

    @Override
    public int getCount() {
        return listPager==null?0:listPager.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = listPager.get(position);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_welcome_pic);
        iv.setImageResource(res[position]);
        if(position==3){
            view.findViewById(R.id.ll_welcome_container).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.ll_welcome_container).setVisibility(View.GONE);
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listPager.get(position));
    }


}
