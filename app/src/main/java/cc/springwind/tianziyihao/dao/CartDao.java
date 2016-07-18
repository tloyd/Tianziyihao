package cc.springwind.tianziyihao.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cc.springwind.tianziyihao.bean.CartInfo;
import cc.springwind.tianziyihao.db.CartDBHelp;

/**
 * Created by HeFan on 2016/7/3.
 */
public class CartDao {
    private CartDBHelp mCartDBHelp;
    private static CartDao mCartDao= null;

    private CartDao(Context context) {
        mCartDBHelp = new CartDBHelp(context);
    }

    public static CartDao getInstance(Context context) {
        if (mCartDao == null) {
            mCartDao = new CartDao(context);
        }
        return mCartDao;
    }

    public void insert(String phone, String mode) {
        //1,开启数据库,准备做写入操作
        SQLiteDatabase db = mCartDBHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("mode", mode);
        db.insert("blacknumber", null, values);

        db.close();
    }

    public void delete(String phone) {
        SQLiteDatabase db = mCartDBHelp.getWritableDatabase();

        db.delete("blacknumber", "phone = ?", new String[]{phone});

        db.close();
    }

    public void update(String phone, String mode) {
        SQLiteDatabase db = mCartDBHelp.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("mode", mode);

        db.update("blacknumber", contentValues, "phone = ?", new String[]{phone});

        db.close();
    }

    public List<CartInfo> findAll() {
        SQLiteDatabase db = mCartDBHelp.getWritableDatabase();

        Cursor cursor = db.query("blacknumber", new String[]{"phone", "mode"}, null, null, null, null, "_id desc");
        List<CartInfo> mList = new ArrayList<CartInfo>();
        while (cursor.moveToNext()) {
        }
        cursor.close();
        db.close();
        return mList;
    }

}
