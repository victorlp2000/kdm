/**
 * 
 */
package com.wel.kangmeida.activity;

import android.app.Activity;
import android.content.Intent;

import com.wel.kangmeida.utils.OnResultAvailableListener;

/**
 * @author 杨拔纲
 * 
 */
public class SubActivity extends Activity implements OnResultAvailableListener {

	public void setActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResult(requestCode, resultCode, data);
	}

}
