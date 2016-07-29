package cc.springwind.tianziyihao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cc.springwind.tianziyihao.db.DBHelp;

/**
 * Created by HeFan on 2016/7/3.
 */
public class UserInfoDao {
    private DBHelp mDBHelp;
    private static UserInfoDao mUserInfoDao = null;

    private UserInfoDao(Context context) {
        mDBHelp = new DBHelp(context);
    }

    public static UserInfoDao getInstance(Context context) {
        if (mUserInfoDao == null) {
            mUserInfoDao = new UserInfoDao(context);
        }
        return mUserInfoDao;
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

    public int queryId(String phone) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        Cursor cursor = db.query("userinfo", new String[]{"_id"}, "username=?", new String[]{phone}, null, null, "_id" +
                " desc");
        int _id = 0;
        while (cursor.moveToNext()) {
            try {
                _id = cursor.getInt(cursor.getColumnIndex("_id"));
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        cursor.close();
        db.close();
        return _id;
    }

    /**
     * 查询积分
     *
     * @param phone
     * @return
     */
    public int queryScoreByPhoneNumber(String phone) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        Cursor cursor = db.query("userinfo", new String[]{"score"}, "username=?", new String[]{phone}, null, null,
                null);
        int score = 0;
        while (cursor.moveToNext()) {
            score = cursor.getInt(cursor.getColumnIndex("score"));
        }
        cursor.close();
        db.close();
        return score;
    }

    /**
     * 根据电话号码更新积分
     *
     * @param phone
     * @param score
     */
    public void updateScoreByPhoneNumber(String phone, int score) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", score);
        db.update("userinfo", values, "username = ?", new String[]{phone});

        db.close();
    }
}
