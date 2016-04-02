/**
 * 
 */
package com.wel.kangmeida.xy;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;


import com.wel.kangmeida.bt.BTAction;
import com.wel.kangmeida.bt.BTPrefix;
import com.wel.kangmeida.user.UserPreferences;
import com.wel.kangmeida.utils.AppConstat;
import com.wel.kangmeida.utils.AppDbHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 血压数据处理相关service
 * 
 * @author 杨拔纲
 * 
 */
public class XYDataService extends Service {

	private UserPreferences userPreference = UserPreferences.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final OkHttpClient client = new OkHttpClient();
	private AppDbHelper dbHelper;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		dbHelper = new AppDbHelper(this);
		super.onCreate();

		// 启动新线程，尝试上传还未上传的数据
		if (userPreference.hasLogin()) {
			uploadData();
		}
	}

	private void uploadData() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String table = "xy_history";
		String[] columns = { "_id", "sys", "dia", "pul", "createTime" };
		String where = "flag=0";
		String[] whereArgs = null;
		String groupBy = null;
		String having = null;
		String order = "_id asc";
		Cursor c = db.query(table, columns, where, whereArgs, groupBy, having,
				order);
		while (c.moveToNext()) {
			int id = c.getInt(0);
			int sys = c.getInt(1);
			int dia = c.getInt(2);
			int pul = c.getInt(3);
			String createTime = c.getString(4);

			new XYUploadThread(sys, dia, pul, createTime, id).start();
		}
	}

	/**
	 * 保存血压数据，如己经登录，则同时上传到服务器。
	 * 
	 * @param sys
	 * @param dia
	 * @param pul
	 * @return
	 */
	public int save(int sys, int dia, int pul) {
		// 保存数据
		String now = sdf.format(new Date());
		ContentValues newXYValus = new ContentValues();
		newXYValus.put("sys", sys);
		newXYValus.put("dia", dia);
		newXYValus.put("pul", pul);
		newXYValus.put("createTime", now);
		newXYValus.put("flag", 0);

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert("xy_history", null, newXYValus);
		if (rowId == -1) {
			// 保存失败
			return 0;
		}
		int id = getLastId(db);
		// 上传到服务器
		if (userPreference.hasLogin()) {
			new XYUploadThread(sys, dia, pul, now, id).start();
		}
		return id;
	}

	public void delete(long xyLocalId, long xyRemoteId) {
		String where = "_id=" + xyLocalId;
		String[] whereArgs = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("xy_history", where, whereArgs);
		// 如果己经登录，则同时删除服务器的数据
		if (userPreference.hasLogin()) {
			new XYDeleteThread(xyRemoteId).start();
		}
	}

	public Cursor list(String beginDate, String endDate) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String table = "xy_history";
		String[] columns = { "_id", "sys", "dia", "pul", "createTime",
				"remoteId" };
		String where = "createTime >= ? and createTime <= ?";
		String[] whereArgs = { beginDate, endDate };
		String groupBy = null;
		String having = null;
		String order = "createTime asc";
		Cursor c = db.query(table, columns, where, whereArgs, groupBy, having,
				order);
		return c;
	}

	public void listFriend(String beginDate, String endDate, int max, int offset) {
		String url = AppConstat.APP_HOST + "/xy/getFriendData";
		String userId = userPreference.getUserId();
		RequestBody formBody = new FormBody.Builder()
				.add("userId", userId)
				.add("beginDate", beginDate)
				.add("endDate", endDate)
				.add("max", "" + max)
				.add("offset", "" + offset)
				.build();
		Request request = new Request.Builder()
				.url(url)
				.post(formBody)
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println("error=" + e.getLocalizedMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String remoteText = response.body().string();
				if (!"".equals(remoteText)) {
					Intent intent = new Intent(BTAction.getUpdateAction(BTPrefix.XY));
					intent.putExtra("remoteText", remoteText);
					sendBroadcast(intent);
				}
			}
		});
	}

	public void listPaiMin(String beginDate, String endDate, int max, int offset) {
		new XYPaiMinThread(beginDate, endDate, max, offset).start();
	}

	private class XYUploadThread extends Thread {
		private int sys;
		private int dia;
		private int pul;
		private int xyId;
		private String createTime;

		public XYUploadThread(int sys, int dia, int pul, String createTime,
				int xyId) {
			this.sys = sys;
			this.dia = dia;
			this.pul = pul;
			this.createTime = createTime;
			this.xyId = xyId;
		}

		public void run() {
			String userId = userPreference.getUserId();
			URL url = null;
			Intent uploadInfoIntent = new Intent(
					BTAction.getSendInfoAction(BTPrefix.XY));
			try {
				url = new URL(AppConstat.APP_HOST + "/xy/save");
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				urlConn.setDoInput(true);// 字节流
				urlConn.setDoOutput(true);// 字节流
				urlConn.setRequestMethod("POST");
				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content_Type",
						"application/x-www-form-urlencoded");
				urlConn.setRequestProperty("Charset", "UTF-8");

				urlConn.connect();

				DataOutputStream dos = new DataOutputStream(
						urlConn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append("userId=" + userId);
				sb.append("&sys=" + sys);
				sb.append("&dia=" + dia);
				sb.append("&pul=" + pul);
				sb.append("&createTime=" + createTime);
				dos.writeBytes(sb.toString());
				dos.flush();
				dos.close();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream()));
				String readLine = null;
				String xyRemoteId = "";
				while ((readLine = br.readLine()) != null) {
					xyRemoteId += readLine;
				}

				br.close();
				urlConn.disconnect();

				if ("0".equals(xyRemoteId)) {
					// 数据上传失败
					uploadInfoIntent.putExtra(BTAction.INFO, "数据上传失败");
				} else {
					ContentValues updateValues = new ContentValues();
					updateValues.put("flag", 1);
					updateValues.put("remoteId", xyRemoteId);
					String where = "_id=" + xyId;
					String[] whereArgs = null;
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					db.update("xy_history", updateValues, where, whereArgs);
					uploadInfoIntent.putExtra(BTAction.INFO, "数据上传成功");
				}
			} catch (MalformedURLException e) {
				uploadInfoIntent.putExtra(BTAction.INFO, "地址格式错误");
			} catch (IOException e) {
				uploadInfoIntent.putExtra(BTAction.INFO, "网络错误");
			}
			sendBroadcast(uploadInfoIntent);
		}
	}

	private class XYDeleteThread extends Thread {

		private long xyRemoteId;

		public XYDeleteThread(long xyRemoteId) {
			this.xyRemoteId = xyRemoteId;
		}

		public void run() {
			String userId = userPreference.getUserId();
			URL url = null;
			Intent uploadInfoIntent = new Intent(
					BTAction.getSendInfoAction(BTPrefix.XY));
			try {
				url = new URL(AppConstat.APP_HOST + "/xy/delete");
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				urlConn.setDoInput(true);// 字节流
				urlConn.setDoOutput(true);// 字节流
				urlConn.setRequestMethod("POST");
				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content_Type",
						"application/x-www-form-urlencoded");
				urlConn.setRequestProperty("Charset", "UTF-8");

				urlConn.connect();

				DataOutputStream dos = new DataOutputStream(
						urlConn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append("userId=" + userId);
				sb.append("&id=" + xyRemoteId);
				dos.writeBytes(sb.toString());
				dos.flush();
				dos.close();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream()));
				String readLine = null;
				String remoteId = "";
				while ((readLine = br.readLine()) != null) {
					remoteId += readLine;
				}

				br.close();
				urlConn.disconnect();

				if ("0".equals(remoteId)) {
					// 数据删除失败
					uploadInfoIntent.putExtra(BTAction.INFO, "数据删除失败");
				} else {
					uploadInfoIntent.putExtra(BTAction.INFO, "数据己删除");
				}
			} catch (MalformedURLException e) {
				uploadInfoIntent.putExtra(BTAction.INFO, "地址格式错误");
			} catch (IOException e) {
				uploadInfoIntent.putExtra(BTAction.INFO, "网络错误");
			}
			sendBroadcast(uploadInfoIntent);
		}
	}

	private class XYPaiMinThread extends Thread {

		private String beginDate;
		private String endDate;
		private int max;
		private int offset;

		public XYPaiMinThread(String beginDate, String endDate, int max,
				int offset) {
			this.beginDate = beginDate;
			this.endDate = endDate;
			this.max = max;
			this.offset = offset;
		}

		public void run() {
			URL url = null;
			Intent pmIntent = new Intent(
					BTAction.getPaiMinAction(BTPrefix.XY));
			try {
				url = new URL(AppConstat.APP_HOST + "/xy/listPaiMin");
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				urlConn.setDoInput(true);// 字节流
				urlConn.setDoOutput(true);// 字节流
				urlConn.setRequestMethod("POST");
				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content_Type",
						"application/x-www-form-urlencoded");
				urlConn.setRequestProperty("Charset", "UTF-8");

				urlConn.connect();

				DataOutputStream dos = new DataOutputStream(
						urlConn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append("beginDate=" + beginDate);
				sb.append("&endDate=" + endDate);
				sb.append("&max=" + max);
				sb.append("&offset=" + offset);
				dos.writeBytes(sb.toString());
				dos.flush();
				dos.close();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream()));
				String readLine = null;
				String strResult = "";
				while ((readLine = br.readLine()) != null) {
					strResult += readLine;
				}

				br.close();
				urlConn.disconnect();

				pmIntent.putExtra("PM", strResult);
			} catch (MalformedURLException e) {
				// nothing
				pmIntent.putExtra("PM", "");
			} catch (IOException e) {
				// nothing
				pmIntent.putExtra("PM", "");
			} catch (Exception e) {
				// nothing
				pmIntent.putExtra("PM", "");
			}
			sendBroadcast(pmIntent);
		}
	}

	public class XYDataBinder extends Binder {
		XYDataService getService() {
			return XYDataService.this;
		}
	}

	private final IBinder binder = new XYDataBinder();

	private int getLastId(SQLiteDatabase db) {
		Cursor cursor = db.rawQuery(
				"select last_insert_rowid() from xy_history", null);
		int id = 0;
		if (cursor.moveToFirst())
			id = cursor.getInt(0);
		return id;
	}
}
