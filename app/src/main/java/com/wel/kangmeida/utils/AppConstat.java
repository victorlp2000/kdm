/**
 *
 */
package com.wel.kangmeida.utils;

import com.wel.kangmeida.R;

/**
 * @author 杨拔纲
 */
public class AppConstat {

    // 首选项文件名
    public static String PREFERENCE_FILE_NAME = "oum_data";

    // 是否首次使用
    public static String IS_FIRST_USE = "isFirstUse";

    //public static String APP_HOST = "http://192.168.2.233:8080/app";
    public static String APP_HOST = "http://183.57.41.230/app";
    //b6bc6da44a4e6bd6bad56c3eb49311b5
    //b5454413feaf0e4c49633b1c729e44c6
    //aec102b5cf1db29158b2568e74cfd88a
    public final static String WX_APP_ID = "wxe805b02db6562962";
    //爱好
    public final static int[] icon = {R.mipmap.xiangqi, R.mipmap.youxi, R.mipmap.majiang, R.mipmap.taijiquan,
            R.mipmap.guojixiangqi, R.mipmap.pingpanqiu, R.mipmap.lanqiu, R.mipmap.zuqiu, R.mipmap.wangqiu,
            R.mipmap.paiqiu, R.mipmap.gaoerfu, R.mipmap.liubing, R.mipmap.baolingqiu, R.mipmap.youyong,
            R.mipmap.huaxue, R.mipmap.puke, R.mipmap.zhuoqiu, R.mipmap.sheyin, R.mipmap.zixingche,
            R.mipmap.yumaoqiu, R.mipmap.diaoyu, R.mipmap.huihua, R.mipmap.shufa, R.mipmap.wudao, R.mipmap.wushu, R.mipmap.sanbu,
            R.mipmap.paobu};
    public final static String[] iconName = {"象棋", "游戏", "麻将", "太极拳", "国际象棋", "乒乓球", "篮球", "足球", "网球", "排球",
            "高尔夫", "溜冰", "保龄球", "游泳", "滑雪", "扑克", "桌球", "摄影", "自行车", "羽毛球", "钓鱼", "绘画", "书法", "舞蹈", "武术",
            "散步", "跑步"};
    public final static int[] circle = {R.mipmap.tuoyuan1, R.mipmap.tuoyuan2, R.mipmap.tuoyuan3, R.mipmap.tuoyuan4};
    public static int HOBBY = 5;
    // 性别
    public static int SEX_MALE = 1;
    public static int SEX_FEMALE = 0;
    public static String[] items = new String[]{"选择本地图片", "拍照"};

    /*头像名称*/
    public static final String IMAGE_FILE_NAME = "faceImage.jpg";

    /* 请求码*/
    public static final int IMAGE_REQUEST_CODE = 0;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int RESULT_REQUEST_CODE = 2;
    // 血压计
    public static int XY_SEARCH_REQUEST_CODE = 100;
    public static int XY_SEARCH_RESULT_CODE = 101;
    public static int XY_SEARCH_CANCEL_CODE = 102;
    public static int XY_DEVICE_REQUEST_CODE = 120;
    public static int XY_DEVICE_RESULT_CODE = 121;
    public static int XY_DEVICE_CANCEL_CODE = 122;

    // BlueTooth
    public static int BT_FOUND_REQUEST_CODE = 200;
    public static int BT_FOUND_RESULT_CODE = 201;

    // 体重
    public static int TZ_SEARCH_REQUEST_CODE = 300;
    public static int TZ_SEARCH_RESULT_CODE = 301;
    public static int TZ_SEARCH_CANCEL_CODE = 302;
    public static int TZ_DEVICE_REQUEST_CODE = 320;
    public static int TZ_DEVICE_RESULT_CODE = 321;
    public static int TZ_DEVICE_CANCEL_CODE = 322;

    // 运动
    public static int YD_DEVICE_REQUEST_CODE = 400;
    public static int YD_DEVICE_RESULT_CODE = 401;
    public static int YD_SEARCH_REQUEST_CODE = 420;
    public static int YD_SEARCH_RESULT_CODE = 421;
    public static int YD_SEARCH_CANCEL_CODE = 422;

    // 体温
    public static int TW_SEARCH_REQUEST_CODE = 500;
    public static int TW_SEARCH_RESULT_CODE = 501;
    public static int TW_SEARCH_CANCEL_CODE = 502;
    public static int TW_DEVICE_REQUEST_CODE = 520;
    public static int TW_DEVICE_RESULT_CODE = 521;
    public static int TW_DEVICE_CANCEL_CODE = 522;

    // 好友
    public static int MEMBER_ADD_REQUEST_CODE = 800;
    public static int MEMBER_ADD_RESULT_CODE = 801;
    public static int FRIEND_ADD_REQUEST_CODE = 900;
    public static int FRIEND_ADD_RESULT_CODE = 901;
    //医生
    public static int DOCTOR_NEW_RESULT_CODE = 600;
    //成员
    public static int CY_NEW_RESULT_CODE = 601;
}
