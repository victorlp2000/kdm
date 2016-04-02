/**
 *
 */
package com.wel.kangmeida.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author 杨拔纲
 */
public class AppDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public final static int DATABASE_VERSION = 5;
    public final static String DATABASE_NAME = "oum.db";
    // 血压记录
    public final static String CREATE_XY_TABLE = "create table xy_history(_id INTEGER primary key autoincrement,"
            + " sys smallint, dia smallint, pul smallint, createTime timestamp, flag smallint, remoteId long)";
    public final static String DROP_XY_TABLE = "drop table if exists xy_history";
    // 体重记录
    public final static String CREATE_TZ_TABLE = "create table tz_history(_id INTEGER primary key autoincrement,"
            + " tzValue float, tzZFValue float, tzJRValue float, tzSFValue float, tzBMIValue float, tzQZValue" +
            " float, tzGGValue float, tzNZValue smallint, tzJCValue smallint, tzSTValue float, createTime timestamp," +
            " flag smallint, remoteId long)";
    public final static String DROP_TZ_TABLE = "drop table if exists tz_history";
    // 运动记录
    public final static String CREATE_YD_TABLE = "create table yd_history(_id INTEGER primary key autoincrement,"
            + " steps integer, distance float, calorie float, ydtime integer, createDate date, createTime timestamp, " +
            " type smallint, flag smallint, remoteId long)";
    public final static String CREATE_YD_SYNC_ACTIVITY_TABLE = "create table yd_sync_activity(_id INTEGER primary key autoincrement,"
            + " steps integer, distance float, calorie float, createDate date, timeIndex smallint, " +
            " flag smallint, remoteId long)";
    public final static String CREATE_YD_SYNC_SLEEP_TABLE = "create table yd_sync_sleep(_id INTEGER primary key autoincrement,"
            + " sm1 smallint, sm2 smallint, sm3 smallint, sm4 smallint, sm5 smallint, sm6 smallint, sm7 smallint, " +
            " sm8 smallint, createDate date, timeIndex smallint,flag smallint, remoteId long)";
    public final static String CREATE_YD_SLEEP_TABLE = "create table yd_sleep(_id INTEGER primary key autoincrement,"
            + " deep smallint, shallow smallint, jittery smallint, createDate date, flag smallint, remoteId long)";
    public final static String DROP_YD_TABLE = "drop table if exists yd_history";
    public final static String DROP_YD_SLEEP_TABLE = "drop table if exists yd_sleep";
    public final static String DROP_YD_SYNC_TABLE = "drop table if exists yd_sync_activity";
    public final static String DROP_YD_SYNC_SLEEP_TABLE = "drop table if exists yd_sync_sleep";
    // 消息记录
    public final static String CREATE_MSG_TABLE = "create table msg_history(_id INTEGER primary key autoincrement,"
            + " img varchar(20), title varchar(40), lastMsg text, updateTime timestamp, type smallint, targetId long, userid long, flag smallint)";
    public final static String CREATE_MSG_DETAIL_TABLE = "create table msg_detail(_id INTEGER primary key autoincrement,"
            + " msgId integer, img varchar(20), username varchar(40), userid long, msg text, createTime timestamp)";
    public final static String DROP_MSG_TABLE = "drop table if exists msg_history";
    public final static String DROP_MSG_DETAIL_TABLE = "drop table if exists msg_detail";
    // 好友列表及好友添加请求
    public final static String CREATE_FRIEND_TABLE = "create table friends(_id INTEGER primary key autoincrement,"
            + " img varchar(20), name varchar(40), userid long, remoteId long)";
    public final static String CREATE_NEW_FRIEND_TABLE = "create table new_friends(_id INTEGER primary key autoincrement,"
            + " img varchar(20), name varchar(40), userid long, reason text, remoteId long)";
    public final static String DROP_FRIEND_TABLE = "drop table if exists friends";
    public final static String DROP_NEW_FRIEND_TABLE = "drop table if exists new_friends";
    // 圈子
    public final static String CREATE_CIRCLE_TABLE = "create table circle(_id INTEGER primary key autoincrement,"
            + " name varchar(40), circleId long, mem text, createTime timestamp, creator varchar(40), createid long)";
    public final static String CREATE_NEW_CIRCLE_TABLE = "create table new_circle(_id INTEGER primary key autoincrement,"
            + " name varchar(40), circleId long, mem text, createTime timestamp, creator varchar(40), createid long, reason text, remoteId long)";
    public final static String DROP_CIRCLE_TABLE = "drop table if exists circle";
    public final static String DROP_NEW_CIRCLE_TABLE = "drop table if exists new_circle";
    // 体温
    public final static String CREATE_TW_HISTORY = "create table tw_history(_id INTEGER primary key autoincrement,"
            + " tw float, createTime timestamp, flag smallint, remoteId long)";
    public final static String DROP_TW_HISTORY = "drop table if exists tw_history";

    public AppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
     * .SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 执行建表指令
        // 血压
        db.execSQL(CREATE_XY_TABLE);
        // 体重
        db.execSQL(CREATE_TZ_TABLE);
        // 运动
        db.execSQL(CREATE_YD_TABLE);
        db.execSQL(CREATE_YD_SLEEP_TABLE);
        db.execSQL(CREATE_YD_SYNC_ACTIVITY_TABLE);
        db.execSQL(CREATE_YD_SYNC_SLEEP_TABLE);
        // 消息
        db.execSQL(CREATE_MSG_TABLE);
        db.execSQL(CREATE_MSG_DETAIL_TABLE);
        // 好友
        db.execSQL(CREATE_FRIEND_TABLE);
        db.execSQL(CREATE_NEW_FRIEND_TABLE);
        // 圈子
        db.execSQL(CREATE_CIRCLE_TABLE);
        db.execSQL(CREATE_NEW_CIRCLE_TABLE);
        // 体温
        db.execSQL(CREATE_TW_HISTORY);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
     * .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            // 表结构发现变化
            // 删除旧表
            // 血压
            db.execSQL(DROP_XY_TABLE);
            // 体重
            db.execSQL(DROP_TZ_TABLE);
            // 运动
            db.execSQL(DROP_YD_TABLE);
            db.execSQL(DROP_YD_SLEEP_TABLE);
            db.execSQL(DROP_YD_SYNC_TABLE);
            db.execSQL(DROP_YD_SYNC_SLEEP_TABLE);
            // 消息
            db.execSQL(DROP_MSG_TABLE);
            db.execSQL(DROP_MSG_DETAIL_TABLE);
            // 好友
            db.execSQL(DROP_FRIEND_TABLE);
            db.execSQL(DROP_NEW_FRIEND_TABLE);
            // 圈子
            db.execSQL(DROP_CIRCLE_TABLE);
            db.execSQL(DROP_NEW_CIRCLE_TABLE);
            // 创建新表
            onCreate(db);
        } else if (oldVersion == 2) {
            // 修正睡眠表
            db.execSQL(DROP_YD_SYNC_SLEEP_TABLE);
            db.execSQL(CREATE_YD_SYNC_SLEEP_TABLE);
            rebuildMsgModel(db);
            // 从5开始，添加了蓝牙体温计，需要增加相关表格。
            db.execSQL(CREATE_TW_HISTORY);
        } else if (oldVersion == 3) {
            rebuildMsgModel(db);
            // 从5开始，添加了蓝牙体温计，需要增加相关表格。
            db.execSQL(CREATE_TW_HISTORY);
        } else if (oldVersion == 4) {
            // 从5开始，添加了蓝牙体温计，需要增加相关表格。
            db.execSQL(CREATE_TW_HISTORY);
        }
    }

    private void rebuildMsgModel(SQLiteDatabase db) {
        // 修正消息相关表与数据
        // 消息
        db.execSQL(DROP_MSG_TABLE);
        db.execSQL(DROP_MSG_DETAIL_TABLE);
        // 好友
        db.execSQL(DROP_FRIEND_TABLE);
        db.execSQL(DROP_NEW_FRIEND_TABLE);
        // 圈子
        db.execSQL(DROP_CIRCLE_TABLE);
        db.execSQL(DROP_NEW_CIRCLE_TABLE);
        // 消息
        db.execSQL(CREATE_MSG_TABLE);
        db.execSQL(CREATE_MSG_DETAIL_TABLE);
        // 好友
        db.execSQL(CREATE_FRIEND_TABLE);
        db.execSQL(CREATE_NEW_FRIEND_TABLE);
        // 圈子
        db.execSQL(CREATE_CIRCLE_TABLE);
        db.execSQL(CREATE_NEW_CIRCLE_TABLE);
    }

}
