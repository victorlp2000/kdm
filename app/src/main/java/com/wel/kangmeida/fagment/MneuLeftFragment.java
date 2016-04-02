package com.wel.kangmeida.fagment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;


public class MneuLeftFragment extends Fragment{

    private RelativeLayout rlCeliang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_left_layout, null);
        return view;
    }

}
