/**
 * 
 */
package com.wel.kangmeida.xy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.wel.kangmeida.R;
import com.wel.kangmeida.utils.OnResultAvailableListener;


/**
 * @author 杨拔纲
 * 
 */
@SuppressWarnings("deprecation")
public class XYWelcomeActivity extends Activity {

	private RadioGroup radioGroup;

	// 页卡内容
	private ViewPager mPager;
	// Tab页面列表
	private List<View> listViews;
	// 当前页卡编号
	private LocalActivityManager manager = null;

	private MyPagerAdapter mpAdapter = null;
	private int index;

	private View view0 = null;
	private View view1 = null;
	private View view2 = null;
	private View view3 = null;

	// 更新intent传过来的值
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		manager.removeAllActivities();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (getIntent() != null) {
			index = getIntent().getIntExtra("index", 0);
			mPager.setCurrentItem(index);
			setIntent(null);
		} else {
			if (index < 3) {
				index = index + 1;
				mPager.setCurrentItem(index);
				index = index - 1;
				mPager.setCurrentItem(index);

			} else if (index == 3) {
				index = index - 1;
				mPager.setCurrentItem(index);
				index = index + 1;
				mPager.setCurrentItem(index);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xy_container);
		mPager = (ViewPager) findViewById(R.id.vPager);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		view0 = getView("A", new Intent(XYWelcomeActivity.this,
				XYMainActivity.class));
		view1 = getView("B", new Intent(XYWelcomeActivity.this,
				XYHistoryActivity.class));
		view2 = getView("C", new Intent(XYWelcomeActivity.this,
				XYDiscoverActivity.class));
		view3 = getView("D", new Intent(XYWelcomeActivity.this,
				XYPaiMinActivity.class));

		initViewPager();

		radioGroup = (RadioGroup) this.findViewById(R.id.xy_tab_btns);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {

						case R.id.xyMainTab:
							index = 0;
							listViews.set(0, view0);
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(0);
							break;

						case R.id.xyHistoryTab:
							index = 1;
							listViews.set(1, view1);
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(1);
							break;

						case R.id.xyDiscoverTab:
							index = 2;
							listViews.set(2, view2);
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(2);
							break;

						case R.id.xyPaiMinTab:
							index = 3;
							listViews.set(3, view3);
							mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(3);
							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		listViews = new ArrayList<View>();
		mpAdapter = new MyPagerAdapter(listViews);
		listViews.add(view0);
		listViews.add(view1);
		listViews.add(view2);
		listViews.add(view3);
		mPager.setOffscreenPageLimit(1);
		mPager.setAdapter(mpAdapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 页卡切换监听，ViewPager改变同样改变TabHost内容
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			manager.dispatchResume();
			switch (arg0) {
			case 0:
				index = 0;
				radioGroup.check(R.id.xyMainTab);
				listViews.set(0, view0);
				mpAdapter.notifyDataSetChanged();
				break;
			case 1:
				index = 1;
				radioGroup.check(R.id.xyHistoryTab);
				listViews.set(1, view1);
				mpAdapter.notifyDataSetChanged();
				break;
			case 2:
				index = 2;
				radioGroup.check(R.id.xyDiscoverTab);
				listViews.set(2, view2);
				mpAdapter.notifyDataSetChanged();
				break;
			case 3:
				index = 3;
				radioGroup.check(R.id.xyPaiMinTab);
				listViews.set(3, view3);
				mpAdapter.notifyDataSetChanged();
				break;
			}
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String[] ids = { "A", "B", "C", "D" };
		OnResultAvailableListener currentActivity = (OnResultAvailableListener) manager
				.getActivity(ids[index]);
		currentActivity.setActivityResult(requestCode, resultCode, data);
	}

}
