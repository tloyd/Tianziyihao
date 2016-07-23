package cc.springwind.tianziyihao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HeFan on 2016/7/23.
 */
public class DateUtil {

    public static String getSimpleDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
