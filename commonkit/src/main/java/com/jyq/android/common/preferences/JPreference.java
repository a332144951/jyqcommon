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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jyq.android.common.CommonKit;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/4.
 */

public  class JPreference  {
    protected static String name="app";
    protected static Set<String> getStringSet(String key){
        return getSharedPreference().getStringSet(key, Collections.<String>emptySet());
    }
    protected static boolean getBoolean(String key){
        return getSharedPreference().getBoolean(key,false);
    }
    protected static long getLong(String key){
        return getSharedPreference().getLong(key,0);
    }
    protected static float getFloat(String key){
        return getSharedPreference().getFloat(key,0);
    }
    protected static int getInt(String key){
        return getSharedPreference().getInt(key,0);
    }
    protected static String getString(String key){
        return getSharedPreference().getString(key,null);
    }

    protected static void saveStringSet(String key,Set<String> value){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putStringSet(key, value);
        editor.commit();
    }
    protected static void saveBoolean(String key,boolean value){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    protected static void saveLong(String key,long value){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putLong(key, value);
        editor.commit();
    }
    protected static void saveFloat(String key,float value){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    protected static void saveInt(String key,int value){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putInt(key, value);
        editor.commit();
    }
    protected static void saveString(String key,String value){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.putString(key, value);
        editor.commit();
    }
    protected static void remove(String key){
        SharedPreferences.Editor editor=getSharedPreference().edit();
        editor.remove(key);
        editor.commit();
    }
    protected static SharedPreferences getSharedPreference(){
        return PreferenceManager.getDefaultSharedPreferences(CommonKit.getInstance().getContext());
    }
}
