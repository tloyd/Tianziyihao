package cc.springwind.tianziyihao.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by HeFan on 2016/7/20.
 */
public class TextCheckUtil {


    private static String regular="^[0-9a-zA-Z]{4,12}";

    public static boolean isPhoneNumber(String number) {
        if (TextUtils.isDigitsOnly(number) && number.length() == 11) {
            return true;
        }
        return false;
    }

    public static boolean isPassword(String password) {
        if (Pattern.matches(regular,password)) {
            return true;
        }
        return false;
    }
}
