package cc.springwind.tianziyihao.db.dao;

import android.content.Context;

import cc.springwind.tianziyihao.db.DBHelp;

/**
 * Created by HeFan on 2016/7/26.
 */
public class GoodDataDao {

    private DBHelp mDBHelp;
    private static GoodDataDao dao = null;

    private GoodDataDao(Context context) {
        mDBHelp = new DBHelp(context);
    }

    public static GoodDataDao getInstance(Context context) {
        if (dao == null) {
            dao = new GoodDataDao(context);
        }
        return dao;
    }


}
