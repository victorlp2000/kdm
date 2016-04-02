package com.wel.kangmeida;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.wel.kangmeida.utils.AppPreferences;


/**
 * Created by ycdyus on 2015/10/9.
 */
public class BaseApp extends Application {
    private static BaseApp app;


    @Override
    public void onCreate() {
        AppPreferences.getInstance().init(getSharedPreferences("app", Context.MODE_PRIVATE));
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
        super.onCreate();
        app = this;


    }

    public static BaseApp getInstance() {
        return app;
    }

    /**
     * 弹出Toast
     */
    public static void showToast(String msg) {
        Toast.makeText(app, msg, Toast.LENGTH_SHORT).show();
    }


}
