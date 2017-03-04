package com.jyq.android.common.crash;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
           佛祖保佑       永无BUG
 */

import android.content.Context;

import com.google.common.util.concurrent.UncaughtExceptionHandlers;


/**
 * Created by Administrator on 2016/9/27.
 */

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;


    private static AppCrashHandler instance;

    private AppCrashHandler(Context context) {
        this.context = context;
    }

    public static AppCrashHandler getInstance(Context mContext) {
        if (instance == null) {
            instance = new AppCrashHandler(mContext);
        }

        return instance;
    }

    public final void saveException(Throwable ex, boolean uncaught) {
        CrashSaver.save(context, ex, uncaught);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
//        CrashReport.postCatchedException(e,t);
        // save log
        saveException(e, true);
        UncaughtExceptionHandlers.systemExit().uncaughtException(t, e);
        // uncaught
//        uncaughtExceptionHandler.uncaughtException(t, e);
    }
}
