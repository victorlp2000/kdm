package com.wel.kangmeida.tw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.utils.AppConstat;

/**
 * Created by yangbagang on 2015/5/28.
 */
public class TWDeviceListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tw_device_list);
       fanhui();
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

    public void selectTWDevice(View view) {
        Intent intent = new Intent();
        if(view.getId() == R.id.twIRLabel1) {
            intent.putExtra("twDeviceName", "KDM-BT100 体温计");
            intent.putExtra("twDeviceModel", "ir");
        }
        setResult(AppConstat.TW_DEVICE_RESULT_CODE, intent);
        finish();

    }

}
