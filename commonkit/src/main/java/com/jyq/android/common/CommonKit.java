package com.jyq.android.common;

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
import android.util.Log;

import com.jyq.android.common.cache.CacheKit;
import com.jyq.android.common.log.LogUtil;
import com.jyq.android.common.preferences.AppPreference;
import com.jyq.android.common.preferences.JPreference;
import com.jyq.android.common.storage.StorageType;
import com.jyq.android.common.storage.StorageUtil;
import com.jyq.android.common.sys.ScreenUtil;

/**
 * Created by Administrator on 2017/2/28.
 */

public class CommonKit {
    private  volatile static CommonKit instance;
    private static Context mContext;
    public static CommonKit getInstance() {
        if (instance==null){
            synchronized (CommonKit.class){
                instance=new CommonKit();
            }
        }
        return instance;
    }
    protected CommonKit(){
        StorageUtil.init(mContext,null);
        ScreenUtil.init(mContext);
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG);
        CacheKit.init(mContext);
    }


    public static void init(Context context){
        CommonKit.mContext=context.getApplicationContext();
    }

    public Context getContext() {
        return mContext;
    }
}
