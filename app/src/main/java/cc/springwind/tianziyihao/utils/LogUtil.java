package cc.springwind.tianziyihao.utils;

import android.util.Log;

/**
 * Created by HeFan on 2016/7/7.
 */
public class LogUtil {
    private static final boolean DEBUG = true;

    public static void debug(String TAG,String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void info(String TAG,String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void error(String TAG,String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void warn(String TAG,String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }
}
