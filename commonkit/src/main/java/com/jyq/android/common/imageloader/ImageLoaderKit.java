package com.jyq.android.common.imageloader;

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
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ImageLoaderKit {
 private volatile static ImageLoaderKit instance;

    public static ImageLoaderKit getInstance() {
        if (instance ==null){
            synchronized (ImageLoaderKit.class){
                if (instance ==null){
                    instance =new ImageLoaderKit();
                }
            }
        }
        return instance;
    }
    protected ImageLoaderKit(){

    }

    private Context mContext;
    private ImageLoaderConfig mConfig;
    public synchronized void init(Context context,ImageLoaderConfig config){
        this.mContext=context.getApplicationContext();
        this.mConfig=(config==null?getDefaultConfig():config);
    }
    private ImageLoaderConfig getDefaultConfig(){
        return new ImageLoaderConfig.Builder(mContext)
                .build();
    }
    public void displayImage(String url,ImageView target){
        displayImage(mContext,url,0,0,target);
    }
    public void displayImage(Context context,@DrawableRes int resId,ImageView target){
        Glide.with(context).load(resId).into(target);
    }
    public void displayImage(Context context, String url, @DrawableRes int loadingDrawable, @DrawableRes int failDrawable, ImageView target){
        Glide.with(context).load(url).placeholder(loadingDrawable).fallback(failDrawable).into(target);
    }



}
