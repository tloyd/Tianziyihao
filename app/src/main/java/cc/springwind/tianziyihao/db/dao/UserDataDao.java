package cc.springwind.tianziyihao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cc.springwind.tianziyihao.db.DBHelp;
import cc.springwind.tianziyihao.db.bean.UserDataBean;

/**
 * Created by HeFan on 2016/7/23.
 */
public class UserDataDao {
    private DBHelp mDBHelp;
    private static UserDataDao mUserDataDao = null;

    private UserDataDao(Context context) {
        mDBHelp = new DBHelp(context);
    }

    public static UserDataDao getInstance(Context context) {
        if (mUserDataDao == null) {
            mUserDataDao = new UserDataDao(context);
        }
        return mUserDataDao;
    }

    public void insert(UserDataBean bean) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", bean.user_id);
        db.insert("userdata", null, values);

        db.close();
    }

    public int queryScoleById(int id) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();
        int scole = 0;
        Cursor cursor = db.query("userdata", new String[]{"scole"}, "user_id=?", new String[]{id + ""}, null, null, null);
        while (cursor.moveToNext()) {
            scole = cursor.getInt(cursor.getColumnIndex("scole"));
        }
        db.close();
        return scole;
    }

    public void updateScoleById(int id, int scole) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("scole",scole);

        db.update("userdata",values,"user_id=?",new String[]{id+""});

        db.close();
    }

}
