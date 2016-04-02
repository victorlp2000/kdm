/**
 * 
 */
package com.wel.kangmeida.xy;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wel.kangmeida.R;
import com.wel.kangmeida.bt.BTAction;
import com.wel.kangmeida.bt.BTDeviceListActivity;
import com.wel.kangmeida.bt.BTMessage;
import com.wel.kangmeida.bt.BTPrefix;
import com.wel.kangmeida.bt.BTStatus;
import com.wel.kangmeida.preference.XYPreference;
import com.wel.kangmeida.activity.SubActivity;
import com.wel.kangmeida.user.YbgApp;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.xy.urion.XYUrionService;

/**
 * @author 杨拔纲
 * 
 */
public class XYMainActivity extends SubActivity
{

	private XYPreference xyPreference = XYPreference.getInstance();
	private YbgApp ybgApp = YbgApp.getInstance();
	private Intent xyMeasureServiceIntent = null;

	private TextView xyPJName = null;
	private Button xyPJOperator = null;

	private TextView xyMeasureData1 = null;
	private TextView xyMeasureData2 = null;
	private TextView xyMeasureData3 = null;
	private TextView xyMeasureTimeLabel = null;
	private TextView xyMeasureResult = null;
	private ImageView xyMeasureImage1 = null;
	private ImageView xyMeasureImage2 = null;
	private ImageView xyMeasureImage3 = null;

	// 使用进度条提示当前正在读取数据
	private ProgressDialog readProgressDialog = null;
	private ProgressBar xyProgressBar = null;

	private XYDataService xyDataService = null;
	private Intent bindIntent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xy_main);
		fanhui();
		/** 尝试初始化视图实例 **/
		initView();
		/** 初始化按钮事件 **/
		initEvent();
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

	private void initView() {
		// 标签及按钮
		xyPJName = (TextView) findViewById(R.id.xyStateLabel);
		xyPJOperator = (Button) findViewById(R.id.xyOperatorButton);
		// 各项数据
		xyMeasureData1 = (TextView) findViewById(R.id.xyMeasureData1);
		xyMeasureData2 = (TextView) findViewById(R.id.xyMeasureData2);
		xyMeasureData3 = (TextView) findViewById(R.id.xyMeasureData3);
		xyMeasureTimeLabel = (TextView) findViewById(R.id.xyMeasureTimeLabel);
		xyMeasureResult = (TextView) findViewById(R.id.xyMeasureResult);
		// 各项数据是否正常的图
		xyMeasureImage1 = (ImageView) findViewById(R.id.xyMeasureImage1);
		xyMeasureImage2 = (ImageView) findViewById(R.id.xyMeasureImage2);
		xyMeasureImage3 = (ImageView) findViewById(R.id.xyMeasureImage3);
		// 测量中的值
		xyProgressBar = (ProgressBar) findViewById(R.id.xyProgressBar);
		xyProgressBar.setMax(300);
	}

	private void initEvent() {
		if (xyPreference.hasAssign()) {
			// 己绑定，显示设备名称。准备连接操作
			xyPJName.setText(xyPreference.getXyDeviceName());
			xyPJOperator
					.setText(BTStatus.BT_BUTTONS[BTStatus.BT_STATU_NOT_START]);
		} else {
			// 未绑定，提示需要绑定。准备进行设备绑定操作。
			xyPJName.setText(BTStatus.BT_LABELS[BTStatus.BT_STATU_NOT_ASSIGN]);
			xyPJOperator
					.setText(BTStatus.BT_BUTTONS[BTStatus.BT_STATU_NOT_ASSIGN]);
		}
	}

	public void xyOperation(View view) {
		String operator = ((Button) view).getText().toString();
		if (BTStatus.BT_BUTTONS[BTStatus.BT_STATU_NOT_ASSIGN].equals(operator)) {
			// 还未绑定，开始绑定过程
			Intent intent = new Intent(this, XYDeviceListActivity.class);
			getParent().startActivityForResult(intent,
					AppConstat.XY_DEVICE_REQUEST_CODE);
		} else if (BTStatus.BT_BUTTONS[BTStatus.BT_STATU_NOT_START]
				.equals(operator)) {
			// 尝试启动设备并获取数据
			startMeasure();
			// 禁用此按钮，避免重复启动
			view.setEnabled(false);
			// 启动进度条
			readProgressDialog = ybgApp.getProgressDialog(XYMainActivity.this,
					"正在测量...");
			readProgressDialog.show();
		}
	}

	public void enterXySetting(View view) {
		Intent intent = new Intent(this, XySettingActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppConstat.XY_DEVICE_REQUEST_CODE
				&& resultCode == AppConstat.XY_DEVICE_RESULT_CODE) {
			// 体重设备列表
			String xyDeviceName = data.getExtras().getString("xyDeviceName");
			String xyDeviceModel = data.getExtras().getString("xyDeviceModel");
			if (!"".equals(xyDeviceName) && !"".equals(xyDeviceModel)) {
				// 记录下设备名称及代号
				xyPreference.setXyDeviceName(xyDeviceName);
				xyPreference.setXyDeviceModel(xyDeviceModel);
				xyPJName.setText(xyDeviceName);
				// 开始扫描蓝牙设备
				Intent intent = new Intent(this, BTDeviceListActivity.class);
				getParent().startActivityForResult(intent,
						AppConstat.BT_FOUND_REQUEST_CODE);
			}
		} else if (requestCode == AppConstat.BT_FOUND_REQUEST_CODE
				&& resultCode == AppConstat.BT_FOUND_RESULT_CODE) {
			// 取得需要连接的蓝牙地址
			String xyDeviceAddr = data.getExtras().getString(
					BTAction.EXTRA_DEVICE_ADDRESS);
			xyPreference.setXyDeviceAddr(xyDeviceAddr);
			// 修改状态，准备连接
			xyPJName.setText(BTStatus.BT_LABELS[BTStatus.BT_STATU_NOT_START]);
			xyPJOperator
					.setText(BTStatus.BT_BUTTONS[BTStatus.BT_STATU_NOT_START]);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStart() {
		// 开启蓝牙
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			ybgApp.showMessage(getApplicationContext(),
					BTMessage.BLUETOOTH_ADAPTER_NOTFOUND);
		} else {
			// 如未开启，则先开启
			if (!bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.enable();
			}
			// 开启后台程序
			xyMeasureServiceIntent = new Intent(this, XYUrionService.class);
			getApplication().startService(xyMeasureServiceIntent);
			// 注册广播
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(BTAction.getSendInfoAction(BTPrefix.XY));
			intentFilter.addAction(BTAction.getSendDataAction(BTPrefix.XY));
			intentFilter.addAction(BTAction.getSendErrorAction(BTPrefix.XY));
			intentFilter.addAction(BTAction.sendProgressAction(BTPrefix.XY));
			getApplication().registerReceiver(xyMeasureBroadcastReceiver,
					intentFilter);
		}
		bindIntent = new Intent(XYMainActivity.this, XYDataService.class);
		getApplication().bindService(bindIntent, mConnection,
				Context.BIND_AUTO_CREATE);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// 停止测量
		stopMeasure();
		// 停止接收广播
		if (null != xyMeasureServiceIntent) {
			getApplication().stopService(xyMeasureServiceIntent);
		}
		getApplication().unregisterReceiver(xyMeasureBroadcastReceiver);
		getApplication().unbindService(mConnection);
		super.onStop();
	}

	private void startMeasure() {
		// 发送测量指令
		Intent startMeasureIntent = new Intent(
				BTAction.getConnectStartAction(BTPrefix.XY));
		startMeasureIntent.putExtra(BTAction.EXTRA_DEVICE_ADDRESS,
				xyPreference.getXyDeviceAddr());
		sendBroadcast(startMeasureIntent);
	}

	private void stopMeasure() {
		// 发送停止指令
		Intent stopMeasureIntent = new Intent(
				BTAction.getStopAction(BTPrefix.XY));
		sendBroadcast(stopMeasureIntent);
	}

	private BroadcastReceiver xyMeasureBroadcastReceiver = new BroadcastReceiver() {

		@SuppressLint("SimpleDateFormat")
		private SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equalsIgnoreCase(BTAction
					.getSendErrorAction(BTPrefix.XY))) {
				String info = intent.getExtras().getString(BTAction.INFO);
				ybgApp.showMessage(getApplicationContext(), info);
				readProgressDialog.dismiss();
				xyPJOperator.setEnabled(true);
			} else if (action.equalsIgnoreCase(BTAction
					.getSendInfoAction(BTPrefix.XY))) {
				String info = intent.getExtras().getString(BTAction.INFO);
				ybgApp.showMessage(getApplicationContext(), info);
			} else if (action.equalsIgnoreCase(BTAction
					.getSendDataAction(BTPrefix.XY))) {
				String data = intent.getExtras().getString(BTAction.DATA);
				if (!xyPreference.hasAssign()) {
					// 还未设置绑定状态，先设置绑定状态，避免重复动作
					xyPreference.setHasAssign(true);
				}
				String[] xyData = data.split(",");
				// 显示数据
				xyMeasureData1.setText(xyData[0]);
				xyMeasureData2.setText(xyData[1]);
				xyMeasureData3.setText(xyData[2]);
				xyMeasureTimeLabel.setText("测量时间：" + sdf.format(new Date()));
				// 显示是否正常
				xyMeasureImage1.setImageResource(XYCheckUtil
						.getImageResourceId(0, xyData[0]));
				xyMeasureImage2.setImageResource(XYCheckUtil
						.getImageResourceId(1, xyData[1]));
				xyMeasureImage3.setImageResource(XYCheckUtil
						.getImageResourceId(2, xyData[2]));
				xyMeasureResult.setText(XYCheckUtil.getNoticeMsg(xyData[0],
						xyData[1], xyData[2]));
				readProgressDialog.dismiss();
				xyPJOperator.setEnabled(true);

				// 保存存数据
				try {
					xyDataService.save(Integer.valueOf(xyData[0]),
							Integer.valueOf(xyData[1]),
							Integer.valueOf(xyData[2]));
				} catch (Exception e) {
					ybgApp.showMessage(getApplicationContext(), "数据保存失败");
				}
			} else if (action.equals(BTAction.sendProgressAction(BTPrefix.XY))) {
				int progressValue = intent.getExtras()
						.getInt(BTAction.PROGRESS);
				xyProgressBar.setProgress(progressValue);
			}
		}

	};

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			xyDataService = ((XYDataService.XYDataBinder) service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// nothing
			xyDataService = null;
		}

	};
}
