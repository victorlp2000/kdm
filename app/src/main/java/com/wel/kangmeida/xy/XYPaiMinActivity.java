/**
 * 
 */
package com.wel.kangmeida.xy;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.wel.kangmeida.R;
import com.wel.kangmeida.bt.BTAction;
import com.wel.kangmeida.bt.BTPrefix;
import com.wel.kangmeida.activity.SubActivity;
import com.wel.kangmeida.ui.YListView;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.utils.StringUtil;

/**
 * @author 杨拔纲
 * 
 */
public class XYPaiMinActivity extends SubActivity
{

	private XYDataService xyDataService = null;
	private Intent bindIntent = null;

	private YListView yListView;
	private List<String[]> list;
	private XYPaiMinAdapter adapter;
	private int pageSize = 15;
	private int pageNum = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xy_paimin);
		fanhui();
		yListView = (YListView) findViewById(R.id.xyPaiMinList);
		yListView.setPullLoadEnable(true);
		yListView.setPullRefreshEnable(true);
		yListView.setYListViewListener(new YListView.IYListViewListener() {
			@Override
			public void onRefresh() {
				pageNum = 1;
				loadingData();
			}

			@Override
			public void onLoadMore() {
				pageNum += 1;
				loadingData();
			}
		});
		initList();
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
	protected void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BTAction.getPaiMinAction(BTPrefix.XY));
		registerReceiver(xyBroadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onStop() {
		unregisterReceiver(xyBroadcastReceiver);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		getApplicationContext().unbindService(pConnection);
		super.onDestroy();
	}

	private void initList() {
		list = new ArrayList<String[]>();

		adapter = new XYPaiMinAdapter(this, list);
		yListView.setAdapter(adapter);

		bindIntent = new Intent(XYPaiMinActivity.this, XYDataService.class);
		getApplicationContext().bindService(bindIntent, pConnection,
				Context.BIND_AUTO_CREATE);
	}

	private void loadingData() {
		xyDataService.listPaiMin("1900-01-01", "2100-12-12", pageSize,
				(pageNum - 1) * pageSize);
	}

	private class XYPaiMinAdapter extends BaseAdapter {
		private Context mContext;
		private List<String[]> mData;

		public XYPaiMinAdapter(Context context, List<String[]> data) {
			this.mContext = context;
			this.mData = data;
		}

		public void reFresh() {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int Index) {
			return mData.get(Index);
		}

		@Override
		public long getItemId(int Index) {
			return Index;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(int index, View mView, ViewGroup mParent) {
			String[] xys = mData.get(index);
			// xy.sys,xy.dia,xy.pul,xy.createTime,xy.user.nickName,xy.user.id
			mView = LayoutInflater.from(mContext).inflate(
					R.layout.xy_paimin_item, null);
			ImageView userImg = (ImageView) mView.findViewById(R.id.xyPaiMinUserImage);
			String imgName = xys[6];
			if ("0".equals(imgName)) {
				userImg.setImageResource(R.mipmap.default_uimg);
			} else {
				ImageLoader.getInstance().displayImage(AppConstat.APP_HOST + imgName, userImg);
			}
			TextView name = (TextView) mView
					.findViewById(R.id.xyPaiMinUserName);
			TextView content = (TextView) mView.findViewById(R.id.xyPaiMinData);
			TextView createTime = (TextView) mView
					.findViewById(R.id.xyCreateTime);
			TextView pmView = (TextView) mView.findViewById(R.id.xyPaiMinOrder);
			name.setText(xys[4]);
			createTime.setText(xys[3]);
			content.setText(String.format("高压：%s，低压：%s，心率：%s。", xys[0], xys[1],
					xys[2]));
			pmView.setText(String.format("第  %d 名", index + 1));
			return mView;
		}
	}

	private ServiceConnection pConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			xyDataService = ((XYDataService.XYDataBinder) service).getService();
			loadingData();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			xyDataService = null;
		}

	};

	private BroadcastReceiver xyBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BTAction.getPaiMinAction(BTPrefix.XY).equals(action)) {
				if (intent != null && intent.getExtras() != null) {
					// 获得数据
					String info = intent.getExtras().getString("PM");
					// 开始解析
					if (info != null && !"".equals(info)) {
						List<String[]> pmList = StringUtil
								.parseStringToList(info);
						if (pmList != null && pmList.size() > 0) {
							// 获得数据
							if (pageNum == 1 && list.size() > 0) {
								// 刷新操作，先清除己有数据
								list.clear();
							}
							list.addAll(pmList);
							adapter.reFresh();
						}
					}
				}
				// 解除状态
				yListView.stopRefresh();
				yListView.stopLoadMore();
				yListView.setRefreshTime("刚刚");
			}
		}

	};
}
