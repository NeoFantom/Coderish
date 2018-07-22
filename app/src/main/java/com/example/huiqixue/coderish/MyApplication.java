package com.example.huiqixue.coderish;

import android.app.Application;
import android.content.Context;

/**
 * Created by Huiqi Xue on 2018/4/21.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    static Context getCurrentContext() {
        return context;
    }
}
