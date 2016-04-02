/**
 * 
 */
package com.wel.kangmeida.tz;

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
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.wel.kangmeida.R;
import com.wel.kangmeida.bt.BTAction;
import com.wel.kangmeida.bt.BTPrefix;
import com.wel.kangmeida.activity.SubActivity;
import com.wel.kangmeida.ui.YListView;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨拔纲
 * 
 */
public class TZDiscoverActivity extends SubActivity
{

	private TZDataService tzDataService = null;
	private Intent bindIntent = null;

	private YListView yListView;
	private List<String[]> list;
	private TZFriendDataAdapter adapter;
	private int pageSize = 15;
	private int pageNum = 1;
	private String beginDate = "1900-01-01";
	private String endDate = "2100-12-12";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tz_friend_data);
		fanhui();
		yListView = (YListView) findViewById(R.id.tzFriendDataList);
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
		ImageView ivFanhui = (ImageView) this.findViewById(R.id.iv_celiang_fanhui);
		ivFanhui.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BTAction.getUpdateAction(BTPrefix.TZ));
		registerReceiver(tzBroadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onStop() {
		unregisterReceiver(tzBroadcastReceiver);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		getApplicationContext().unbindService(pConnection);
		super.onDestroy();
	}

	private void initList() {
		list = new ArrayList<String[]>();

		adapter = new TZFriendDataAdapter(this, list);
		yListView.setAdapter(adapter);

		bindIntent = new Intent(TZDiscoverActivity.this, TZDataService.class);
		getApplicationContext().bindService(bindIntent, pConnection,
				Context.BIND_AUTO_CREATE);
	}

	private void loadingData() {
		tzDataService.listFriendData(beginDate, endDate, pageSize, (pageNum - 1) * pageSize);
	}

	public void search(View view) {
		Intent intent = new Intent(this, TZHistorySearchActivity.class);
		getParent().startActivityForResult(intent,
				AppConstat.TZ_SEARCH_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppConstat.TZ_SEARCH_REQUEST_CODE
				&& resultCode == AppConstat.TZ_SEARCH_RESULT_CODE) {
			if (list != null) {
				list.clear();
			}
			if (adapter != null) {
				adapter.reFresh();
			}
			beginDate = data.getExtras().getString("beginDate");
			endDate = data.getExtras().getString("endDate");
			loadingData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private class TZFriendDataAdapter extends BaseAdapter {
		private Context mContext;
		private List<String[]> mData;

		public TZFriendDataAdapter(Context context, List<String[]> data) {
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
			//String hql = "select tz.tzValue,tz.tzZFValue,tz.tzJRValue,tz.tzSFValue,tz.tzBMIValue,"+
			//		"tz.tzQZValue,tz.tzGGValue,tz.tzNZValue,tz.tzJCValue,tz.tzSTValue,tz.createTime,"+
			//		"tz.user.nickName,tz.user.id
			mView = LayoutInflater.from(mContext).inflate(
					R.layout.tz_friend_item, null);
			ImageView userImg = (ImageView) mView.findViewById(R.id.tzFriendUserImage);
			String imgName = xys[13];
			if ("0".equals(imgName)) {
				userImg.setImageResource(R.mipmap.default_uimg);
			} else {
				ImageLoader.getInstance().displayImage(AppConstat.APP_HOST + imgName, userImg);
			}
			TextView name = (TextView) mView.findViewById(R.id.tzFriendUserName);
			TextView content = (TextView) mView.findViewById(R.id.tzFriendData);
			TextView createTime = (TextView) mView.findViewById(R.id.tzFriendTime);
			name.setText(xys[11]);
			createTime.setText(xys[10]);
			content.setText(String.format("体重：%s，脂肪率：%s，肌肉率：%s，水份：%s，"
							+ "BMI：%s，去脂体重：%s，骨骼重量：%s，内脏脂肪等级：%s，基础代谢：%s。", xys[0],
					xys[1], xys[2], xys[3], xys[4], xys[5],
					xys[6], xys[7], xys[8]));
			return mView;
		}
	}

	private ServiceConnection pConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			tzDataService = ((TZDataService.TZDataBinder) service).getService();
			loadingData();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			tzDataService = null;
		}

	};

	private BroadcastReceiver tzBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BTAction.getUpdateAction(BTPrefix.TZ).equals(action)) {
				if (intent != null && intent.getExtras() != null) {
					// 获得数据
					String info = intent.getExtras().getString("remoteText");
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
