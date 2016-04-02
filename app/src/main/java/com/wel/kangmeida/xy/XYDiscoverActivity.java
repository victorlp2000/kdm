/**
 * 
 */
package com.wel.kangmeida.xy;

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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨拔纲
 * 
 */
public class XYDiscoverActivity extends SubActivity
{

	private XYDataService xyDataService = null;
	private Intent bindIntent = null;

	private YListView yListView;
	private List<String[]> list;
	private XYFriendDataAdapter adapter;
	private int pageSize = 15;
	private int pageNum = 1;
	private String beginDate = "1900-01-01";
	private String endDate = "2100-12-12";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xy_friend_data);
		fanhui();
		yListView = (YListView) findViewById(R.id.xyFriendDataList);
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
		intentFilter.addAction(BTAction.getUpdateAction(BTPrefix.XY));
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
		getApplicationContext().unbindService(mConnection);
		super.onDestroy();
	}

	private void initList() {
		list = new ArrayList<String[]>();

		adapter = new XYFriendDataAdapter(this, list);
		yListView.setAdapter(adapter);

		bindIntent = new Intent(XYDiscoverActivity.this, XYDataService.class);
		getApplicationContext().bindService(bindIntent, mConnection,
				Context.BIND_AUTO_CREATE);
	}

	private void loadingData() {
		xyDataService.listFriend(beginDate, endDate, pageSize, (pageNum - 1) * pageSize);
	}

	public void search(View view) {
		Intent intent = new Intent(this, XYFriendDataSearchActivity.class);
		getParent().startActivityForResult(intent, AppConstat.XY_SEARCH_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppConstat.XY_SEARCH_REQUEST_CODE
				&& resultCode == AppConstat.XY_SEARCH_RESULT_CODE) {
			if (list != null) {
				list.clear();
			}
			if (adapter != null) {
				adapter.reFresh();
			}
			beginDate = data.getExtras().getString("beginDate");
			endDate = data.getExtras().getString("endDate");
			pageNum = 1;
			loadingData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private class XYFriendDataAdapter extends BaseAdapter {
		private Context mContext;
		private List<String[]> mData;

		public XYFriendDataAdapter(Context context, List<String[]> data) {
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
					R.layout.xy_friend_item, null);
			ImageView userImg = (ImageView) mView.findViewById(R.id.xyFriendUserImage);
			String imgName = xys[6];
			if ("0".equals(imgName)) {
				userImg.setImageResource(R.mipmap.default_uimg);
			} else {
				ImageLoader.getInstance().displayImage(AppConstat.APP_HOST + imgName, userImg);
			}
			TextView name = (TextView) mView
					.findViewById(R.id.xyFriendUserName);
			TextView content = (TextView) mView.findViewById(R.id.xyFriendData);
			TextView createTime = (TextView) mView
					.findViewById(R.id.xyFriendTime);
			name.setText(xys[4]);
			createTime.setText(xys[3]);
			content.setText(String.format("高压：%s，低压：%s，心率：%s。", xys[0], xys[1],
					xys[2]));
			return mView;
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {

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
			if (BTAction.getUpdateAction(BTPrefix.XY).equals(action)) {
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
