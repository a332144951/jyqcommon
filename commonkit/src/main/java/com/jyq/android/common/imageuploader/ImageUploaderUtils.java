package com.jyq.android.common.imageuploader;

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

import android.text.TextUtils;
import android.webkit.URLUtil;

import com.qiniu.android.utils.UrlSafeBase64;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ImageUploaderUtils {
    public static String contactFullImageUrl(String host, String key) {
        if (URLUtil.isNetworkUrl(key)) {
            return key;
        }
        if (TextUtils.isEmpty(key))
            return "";
        return TextUtils.concat(host, "/", key).toString();
    }
    public static String contactWaterMarker(String url,String markerStr){
        StringBuffer buffer=new StringBuffer(url);
        if (url.endsWith("/")){
            buffer.delete(buffer.length()-1,buffer.length());
        }
        buffer.append("?watermark/2/text/")
                .append(UrlSafeBase64.encodeToString(markerStr))
                .append("/font/")
                .append(UrlSafeBase64.encodeToString("宋体"))
                .append("/fontsize/")
                .append(1000)
                .append("/fill/")
                .append(UrlSafeBase64.encodeToString("#ffffff"));
        return buffer.toString();
    }

}
