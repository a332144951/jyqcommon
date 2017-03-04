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
import android.content.res.Resources;
import android.support.annotation.DrawableRes;

/**
 * Created by Administrator on 2017/2/28.
 */

public final class ImageLoaderConfig {
    final Resources resources;
    final @DrawableRes int placeholderDrawable;
    final @DrawableRes int failDrawable;

    private ImageLoaderConfig(Builder builder) {
        resources = builder.mContext.getResources();
        placeholderDrawable = builder.placeholderDrawable;
        failDrawable = builder.failDrawable;
    }

    public static final class Builder {
        private  int placeholderDrawable;
        private  int failDrawable;
        private Context mContext;


        public Builder(Context context) {
            this.mContext=context.getApplicationContext();
        }


        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }


        public Builder placeholderDrawable(@DrawableRes  int val) {
            placeholderDrawable = val;
            return this;
        }

        public Builder failDrawable(@DrawableRes int val) {
            failDrawable = val;
            return this;
        }
    }
}
