package com.wel.kangmeida.tw;

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
import com.wel.kangmeida.preference.TWPreference;
import com.wel.kangmeida.activity.SubActivity;
import com.wel.kangmeida.ui.YListView;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.utils.StringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangbagang on 2015/5/28.
 */
public class TWDiscoverActivity extends SubActivity
{

    private TWPreference twPreference = TWPreference.getInstance();
    private TWDataService twDataService = null;
    private Intent bindIntent = null;

    private YListView yListView;
    private List<String[]> list;
    private TWFriendDataAdapter adapter;
    private int pageSize = 15;
    private int pageNum = 1;
    private String beginDate = "1900-01-01";
    private String endDate = "2100-12-12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tw_friend_data);
        fanhui();
        yListView = (YListView) findViewById(R.id.twFriendDataList);
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
        intentFilter.addAction(BTAction.getUpdateAction(BTPrefix.TW));
        registerReceiver(twBroadcastReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(twBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        getApplicationContext().unbindService(mConnection);
        super.onDestroy();
    }

    private void initList() {
        list = new ArrayList<String[]>();

        adapter = new TWFriendDataAdapter(this, list);
        yListView.setAdapter(adapter);

        bindIntent = new Intent(TWDiscoverActivity.this, TWDataService.class);
        getApplicationContext().bindService(bindIntent, mConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void loadingData() {
        twDataService.listFriend(beginDate, endDate, pageSize, (pageNum - 1) * pageSize);
    }

    public void search(View view) {
        Intent intent = new Intent(this, TWFriendDataSearchActivity.class);
        getParent().startActivityForResult(intent, AppConstat.TW_SEARCH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstat.TW_SEARCH_REQUEST_CODE
                && resultCode == AppConstat.TW_SEARCH_RESULT_CODE) {
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

    private class TWFriendDataAdapter extends BaseAdapter {
        private Context mContext;
        private List<String[]> mData;

        public TWFriendDataAdapter(Context context, List<String[]> data) {
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
            // tw.tw,tw.createTime,tw.user.nickName,tw.user.id,tw.user.userImg
            mView = LayoutInflater.from(mContext).inflate(
                    R.layout.tw_friend_item, null);
            ImageView userImg = (ImageView) mView.findViewById(R.id.twFriendUserImage);
            String imgName = xys[4];
            if ("0".equals(imgName)) {
                userImg.setImageResource(R.mipmap.default_uimg);
            } else {
                ImageLoader.getInstance().displayImage(AppConstat.APP_HOST + imgName, userImg);
            }
            TextView name = (TextView) mView.findViewById(R.id.twFriendUserName);
            TextView content = (TextView) mView.findViewById(R.id.twFriendData);
            TextView createTime = (TextView) mView.findViewById(R.id.twFriendTime);
            name.setText(xys[2]);
            createTime.setText(xys[1]);
            ImageView twUnit = (ImageView) mView.findViewById(R.id.twUnit);

            float tw = Float.valueOf(xys[0]);

            if (twPreference.isCAsDefaultUnit()) {
                content.setText(String.format("温度：%s", new DecimalFormat("#.#").format(tw)));
                twUnit.setImageResource(R.mipmap.cc);
            } else {
                content.setText(String.format("温度：%s", new DecimalFormat("#.#").format(WenduTool.c2f(tw))));
                twUnit.setImageResource(R.mipmap.ff);
            }
            return mView;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            twDataService = ((TWDataService.TWDataBinder) service).getService();
            loadingData();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            twDataService = null;
        }

    };

    private BroadcastReceiver twBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BTAction.getUpdateAction(BTPrefix.TW).equals(action)) {
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
