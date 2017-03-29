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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/20.
 */

 public class ACache {
    public static final int TIME_HOUR = 60 * 60;
    public static final int TIME_DAY = TIME_HOUR * 24;
    private static final int MAX_SIZE = 1000 * 1000 * 50; // 50 mb
    private static final int MAX_COUNT = Integer.MAX_VALUE; // 不限制存放数据的数量
    private static Map<String, ACache> mInstanceMap = new HashMap<String, ACache>();
    private CacheManager mCache;

    public static ACache get(Context ctx) {
        return get(ctx, "ACache");
    }

    public static ACache get(Context ctx, String cacheName) {  //更改为非缓存
        File f = new File(ctx.getFilesDir(), cacheName);
        return get(f, MAX_SIZE, MAX_COUNT);
    }

    public static ACache get(File cacheDir) {
        return get(cacheDir, MAX_SIZE, MAX_COUNT);
    }

    public static ACache get(Context ctx, long max_zise, int max_count) {
        File f = new File(ctx.getFilesDir(), "ACache");
        return get(f, max_zise, max_count);
    }

    public static ACache get(File cacheDir, long max_zise, int max_count) {
        ACache manager = mInstanceMap.get(cacheDir.getAbsoluteFile() + myPid());
        if (manager == null) {
            manager = new ACache(cacheDir, max_zise, max_count);
            mInstanceMap.put(cacheDir.getAbsolutePath() + myPid(), manager);
        }
        return manager;
    }

    private static String myPid() {
        return "_" + android.os.Process.myPid();
    }

    private ACache(File cacheDir, long max_size, int max_count) {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new RuntimeException("can't make dirs in "
                    + cacheDir.getAbsolutePath());
        }
        mCache = new CacheManager(cacheDir, max_size, max_count);
    }

    // =======================================
    // ============ String数据 读写 ==============
    // =======================================
    /**
     * 保存 String数据 缓存
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的String数据
     */
    public void put(String key, String value) {
        File file = mCache.newFile(key);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file), 1024);
            out.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mCache.put(file);
        }
    }

    /**
     * 保存 String数据 缓存
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的String数据
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, String value, int saveTime) {
        put(key, CacheUtils.newStringWithDateInfo(saveTime, value));
    }

    /**
     * 读取 String数据
     *
     * @param key
     * @return String 数据
     */
    public String getAsString(String key) {
        File file = mCache.get(key);
        if (!file.exists())
            return null;
        boolean removeFile = false;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String readString = "";
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                readString += currentLine;
            }
            if (!CacheUtils.isDue(readString)) {
                return CacheUtils.clearDateInfo(readString);
            } else {
                removeFile = true;
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (removeFile)
                remove(key);
        }
    }

    // =======================================
    // ============= JSONObject 数据 读写 ==============
    // =======================================
    /**
     * 保存 JSONObject数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的JSON数据
     */
    public void put(String key, JsonObject value) {
        put(key, value.toString());
    }

    /**
     * 保存 JSONObject数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的JSONObject数据
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, JsonObject value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    /**
     * 读取JSONObject数据
     *
     * @param key
     * @return JSONObject数据
     */
    public JsonObject getAsJSONObject(String key) {
        String JSONString = getAsString(key);
        return new JsonParser().parse(JSONString==null?"":JSONString).getAsJsonObject();
    }

    // =======================================
    // ============ JSONArray 数据 读写 =============
    // =======================================
    /**
     * 保存 JSONArray数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的JSONArray数据
     */
    public void put(String key, JsonArray value) {
        put(key, value.toString());
    }

    /**
     * 保存 JSONArray数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的JSONArray数据
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, JsonArray value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    /**
     * 读取JSONArray数据
     *
     * @param key
     * @return JSONArray数据
     */
    public JsonArray getAsJSONArray(String key) {
        String JSONString = getAsString(key);
        return new JsonParser().parse(JSONString==null?"":JSONString).getAsJsonArray();
    }

    // =======================================
    // ============== byte 数据 读写 =============
    // =======================================
    /**
     * 保存 byte数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的数�?
     */
    public void put(String key, byte[] value) {
        File file = mCache.newFile(key);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mCache.put(file);
        }
    }

    /**
     * 保存 byte数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的数�?
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, byte[] value, int saveTime) {
        put(key, CacheUtils.newByteArrayWithDateInfo(saveTime, value));
    }

    /**
     * 获取 byte 数据
     *
     * @param key
     * @return byte 数据
     */
    public byte[] getAsBinary(String key) {
        RandomAccessFile RAFile = null;
        boolean removeFile = false;
        try {
            File file = mCache.get(key);
            if (!file.exists())
                return null;
            RAFile = new RandomAccessFile(file, "r");
            byte[] byteArray = new byte[(int) RAFile.length()];
            RAFile.read(byteArray);
            if (!CacheUtils.isDue(byteArray)) {
                return CacheUtils.clearDateInfo(byteArray);
            } else {
                removeFile = true;
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (RAFile != null) {
                try {
                    RAFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (removeFile)
                remove(key);
        }
    }

    // =======================================
    // ============= 序列�?数据 读写 ===============
    // =======================================
    /**
     * 保存 Serializable数据 �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     */
    public void put(String key, Serializable value) {
        put(key, value, -1);
    }



    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, Serializable value, int saveTime) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] data = baos.toByteArray();
            if (saveTime != -1) {
                put(key, data, saveTime);
            } else {
                put(key, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 读取 Serializable数据
     *
     * @param key
     * @return Serializable 数据
     */
    public Object getAsObject(String key) {
        byte[] data = getAsBinary(key);
        if (data != null) {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            try {
                bais = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bais);
                Object reObject = ois.readObject();
                return reObject;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (bais != null)
                        bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (ois != null)
                        ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    public Boolean getAsBoolean(String key,Boolean defaultBoolean) {
        byte[] data = getAsBinary(key);
        if (data != null) {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            try {
                bais = new ByteArrayInputStream(data);
                ois = new ObjectInputStream(bais);
                Object reObject = ois.readObject();
                return (Boolean) reObject;
            } catch (Exception e) {
                e.printStackTrace();
                return defaultBoolean;
            } finally {
                try {
                    if (bais != null)
                        bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (ois != null)
                        ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return defaultBoolean;

    }

    // =======================================
    // ============== bitmap 数据 读写 =============
    // =======================================
    /**
     * 保存 bitmap �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的bitmap数据
     */
    public void put(String key, Bitmap value) {
        put(key, CacheUtils.Bitmap2Bytes(value));
    }

    /**
     * 保存 bitmap �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存�?bitmap 数据
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, Bitmap value, int saveTime) {
        put(key, CacheUtils.Bitmap2Bytes(value), saveTime);
    }

    /**
     * 读取 bitmap 数据
     *
     * @param key
     * @return bitmap 数据
     */
    public Bitmap getAsBitmap(String key) {
        if (getAsBinary(key) == null) {
            return null;
        }
        return CacheUtils.Bytes2Bimap(getAsBinary(key));
    }

    // =======================================
    // ============= drawable 数据 读写 =============
    // =======================================
    /**
     * 保存 drawable �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的drawable数据
     */
    public void put(String key, Drawable value) {
        put(key, CacheUtils.drawable2Bitmap(value));
    }

    /**
     * 保存 drawable �?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存�?drawable 数据
     * @param saveTime
     *            保存的时间，单位：秒
     */
    public void put(String key, Drawable value, int saveTime) {
        put(key, CacheUtils.drawable2Bitmap(value), saveTime);
    }

    /**
     * 读取 Drawable 数据
     *
     * @param key
     * @return Drawable 数据
     */
    public Drawable getAsDrawable(String key) {
        if (getAsBinary(key) == null) {
            return null;
        }
        return CacheUtils.bitmap2Drawable(CacheUtils.Bytes2Bimap(getAsBinary(key)));
    }

    /**
     * 获取缓存文件
     *
     * @param key
     * @return value 缓存的文�?
     */
    public File file(String key) {
        File f = mCache.newFile(key);
        if (f.exists())
            return f;
        return null;
    }

    /**
     * 移除某个key
     *
     * @param key
     * @return 是否移除成功
     */
    public boolean remove(String key) {
        return mCache.remove(key);
    }

    /**
     * 清除�?��数据
     */
    public void clear() {
        mCache.clear();
    }




}
