package com.jyq.android.common.preferences;

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
 * Created by Administrator on 2017/3/4.
 */

public class AppPreference extends JPreference{

    private static final String KEY_API_TOKEN="token";
    private static final String KEY_API_ACCOUNT="account";
    private static final String KEY_USER_INIT="user_init";
    public static String getApiToken(){
        return getString(KEY_API_TOKEN);
    }
    public static void saveApiToken(String token){
        saveString(KEY_API_TOKEN,token);
    }
    public static void saveInit(boolean init){
        saveBoolean(KEY_USER_INIT,init);
    }
    public static boolean getInit(){
        return getBoolean(KEY_USER_INIT);
    }

    public static void clear(){
        getSharedPreference().edit().clear().commit();
    }

}
