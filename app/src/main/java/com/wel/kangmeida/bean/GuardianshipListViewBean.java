package com.wel.kangmeida.bean;

import android.widget.CheckBox;

/**
 * Created by TZJ on 2016/3/18.
 */
public class GuardianshipListViewBean {
    private String name;
    private boolean check = true;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
