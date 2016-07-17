package cc.springwind.tianziyihao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HeFan on 2016/7/7.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, String msg) {
        if (mToast == null)
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
