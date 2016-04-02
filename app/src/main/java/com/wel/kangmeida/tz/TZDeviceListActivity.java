/**
 *
 */
package com.wel.kangmeida.tz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wel.kangmeida.R;
import com.wel.kangmeida.utils.AppConstat;


/**
 * @author 杨拔纲
 */
public class TZDeviceListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tz_device_list);
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

    public void selectTZDevice(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.tzLefu1) {
            intent.putExtra("tzDeviceName", "KDM-WF200 体重计");
            intent.putExtra("tzDeviceModel", TzUtil.TZ_DEVICE_LEFU);
        }
//		} else if (view.getId() == R.id.tzFuruik1){
//			intent.putExtra("tzDeviceName", "AS-F16");
//			intent.putExtra("tzDeviceModel", TzUtil.TZ_DEVICE_FURUIK);
//		}
        setResult(AppConstat.TZ_DEVICE_RESULT_CODE, intent);
        finish();
    }

}
