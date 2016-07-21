package cc.springwind.tianziyihao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cc.springwind.tianziyihao.db.DBHelp;

/**
 * Created by HeFan on 2016/7/3.
 */
public class UserDao {
    private DBHelp mDBHelp;
    private static UserDao mUserDao = null;

    private UserDao(Context context) {
        mDBHelp = new DBHelp(context);
    }

    public static UserDao getInstance(Context context) {
        if (mUserDao == null) {
            mUserDao = new UserDao(context);
        }
        return mUserDao;
    }

    /*"username varchar(20)" +
      "password varchar(20))*/

    public long insert(String phone, String password) {
        //1,开启数据库,准备做写入操作
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", phone);
        values.put("password", password);
        long l = db.insert("userinfo", null, values);

        db.close();

        return l;
    }

    public void delete(String phone) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        db.delete("userinfo", "username = ?", new String[]{phone});

        db.close();
    }

    public void update(String phone, String password) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", phone);
        contentValues.put("password", password);
        db.update("cart", contentValues, "username = ?", new String[]{phone});

        db.close();
    }

    public boolean check(String phone, String password) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        Cursor cursor = db.query("userinfo", null, null, null, null, null, "_id desc");
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("username")).equals(phone) && cursor.getString(cursor
                    .getColumnIndex("password")).equals(password)) {
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }

}
