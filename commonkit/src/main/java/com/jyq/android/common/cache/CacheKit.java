package com.jyq.android.common.cache;

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

import com.jyq.android.common.CommonKit;
import com.jyq.android.common.storage.StorageType;
import com.jyq.android.common.storage.StorageUtil;

import java.io.File;


/**
 * Created by Administrator on 2017/3/20.
 */

public class CacheKit {
    private static final CacheKit ourInstance = new CacheKit();

    public static CacheKit getInstance() {
        return ourInstance;
    }

    private ACache InternalCache;
    private ACache ExternalCache;

    private CacheKit() {
        InternalCache = ACache.get(CommonKit.getInstance().getContext());
        ExternalCache = ACache.get(new File(StorageUtil.getDirectoryByDirType(StorageType.TYPE_CACHE)));
    }

    /**
     * 内部存储缓存
     * @return
     */
    public ACache getInternalCache() {
        return InternalCache;
    }

    /**
     * 外部存储缓存
     * @return
     */
    public ACache getExternalCache() {
        return ExternalCache;
    }

    public void clearCache() {
        ACache.get(CommonKit.getInstance().getContext()).clear();
    }


}
