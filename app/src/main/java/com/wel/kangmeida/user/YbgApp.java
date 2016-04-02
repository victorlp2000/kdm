/**
 * 
 */
package com.wel.kangmeida.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.wel.kangmeida.R;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.utils.AppPreferences;


/**
 * 
 * @author 杨拔纲
 * 
 */
public class YbgApp {

	private AppPreferences preference = AppPreferences.getInstance();

	private static YbgApp app = null;

	private YbgApp() {

	}

	public static YbgApp getInstance() {
		if (app == null) {
			app = new YbgApp();
		}
		return app;
	}

	public boolean isFirstUse() {
		return preference.getBoolean(AppConstat.IS_FIRST_USE, true);
	}

	public void setFirstUse(boolean isFirstUse) {
		preference.setBoolean(AppConstat.IS_FIRST_USE, isFirstUse);
	}

	// 操作系统
	public String getSysName() {
		return android.os.Build.DEVICE;
	}

	// 操作系统版本
	public String getSysVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	// 客户端或浏览器名称
	public String getAppName() {
		return "okd app";
	}

	// 客户端或浏览器版本
	public String getAppVersion(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					"com.oum.app.okd", 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	// 品牌
	public String getBrandInfo() {
		return android.os.Build.BRAND;
	}

	// 型号
	public String getModelInfo() {
		return android.os.Build.MODEL;
	}

	// 进网号
	public String getImeiNo(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public void showMessage(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public ProgressDialog getProgressDialog(Context context, String message) {
		ProgressDialog dialog = new ProgressDialog(context, R.style.tmDialog);
		dialog.setMessage(message);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.6f;
		window.setAttributes(lp);
		return dialog;
	}
}
