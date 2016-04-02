/**
 * 
 */
package com.wel.kangmeida.xy;

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
 *
 */
public class XYDeviceListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xy_device_list);
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

	public void selectXYDevice(View view) {
		Intent intent = new Intent();
		if(view.getId() == R.id.xyUrionLabel1) {
			intent.putExtra("xyDeviceName", "KDM-BP80 血压计");
			intent.putExtra("xyDeviceModel", "urion");
		}
		setResult(AppConstat.XY_DEVICE_RESULT_CODE, intent);
		finish();
	}
}
