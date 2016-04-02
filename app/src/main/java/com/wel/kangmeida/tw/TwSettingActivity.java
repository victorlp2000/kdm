package com.wel.kangmeida.tw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.preference.TWPreference;
import com.wel.kangmeida.user.YbgApp;

/**
 * Created by yangbagang on 2015/5/28.
 */
public class TwSettingActivity extends Activity {

    private TWPreference twPreference = TWPreference.getInstance();
    private YbgApp ybgApp = YbgApp.getInstance();

    private RadioButton userTWCC = null;
    private RadioButton userTWFF = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tw_setting);
        fanhui();
        initView();
    }

    private void fanhui() {
        RelativeLayout rlFanhui = (RelativeLayout) this.findViewById(R.id.rl_fanhui);
        rlFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        userTWCC = (RadioButton) findViewById(R.id.userTWCC);
        userTWFF = (RadioButton) findViewById(R.id.userTWFF);

        boolean ccIsDefaultUnit = twPreference.isCAsDefaultUnit();
        if (ccIsDefaultUnit) {
            userTWCC.setChecked(true);
            userTWFF.setChecked(false);
        } else {
            userTWCC.setChecked(false);
            userTWFF.setChecked(true);
        }

        userTWCC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userTWCC.setSelected(true);
                userTWFF.setSelected(false);

                twPreference.setCAsDefaultUnit(true);
            }

        });
        userTWFF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userTWCC.setSelected(false);
                userTWFF.setSelected(true);

                twPreference.setCAsDefaultUnit(false);
            }

        });
    }

    public void saveUserSetting(View view) {
        ybgApp.showMessage(getApplication(), "设置己经保存");
        finish();
    }

}
