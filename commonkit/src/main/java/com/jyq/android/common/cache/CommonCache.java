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

/**
 * Created by Administrator on 2017/3/21.
 */

public class CommonCache {
    private static final CommonCache ourInstance = new CommonCache();

    public static CommonCache getInstance() {
        return ourInstance;
    }

    private CommonCache() {

    }
    private final String KEY_CACHE_IMAGE_HOST="cache_image_host";
    public String getImageHost(){
        return CacheKit.getInstance().getInternalCache().getAsString(KEY_CACHE_IMAGE_HOST);
    }
    public void updateImageHost(String host){
        CacheKit.getInstance().getInternalCache().put(KEY_CACHE_IMAGE_HOST,host);
    }

}
