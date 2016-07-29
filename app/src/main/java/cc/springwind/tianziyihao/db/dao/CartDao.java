package cc.springwind.tianziyihao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cc.springwind.tianziyihao.db.bean.CartBean;
import cc.springwind.tianziyihao.db.DBHelp;

/**
 * Created by HeFan on 2016/7/3.
 */
public class CartDao {
    private DBHelp mDBHelp;
    private static CartDao mCartDao = null;
    private Context context;

    private CartDao(Context context) {
        this.context = context;
        mDBHelp = new DBHelp(context);
    }

    public static CartDao getInstance(Context context) {
        if (mCartDao == null) {
            mCartDao = new CartDao(context);
        }
        return mCartDao;
    }

    public void insert(CartBean info) {
        //1,开启数据库,准备做写入操作
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("good_id", info.good_id);
        values.put("good_name", info.good_name);
        values.put("good_price", info.good_price);
        values.put("good_img_url", info.good_img_url);
        values.put("count", info.count);
        values.put("username", info.username);
        db.insert("cart", null, values);

        db.close();

        context.getContentResolver().notifyChange(Uri.parse("content://cart/change"), null);
    }

    public void delete(String good_id, String phoneNumber) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        db.delete("cart", "good_id = ? and username = ?", new String[]{good_id, phoneNumber});

        db.close();

        context.getContentResolver().notifyChange(Uri.parse("content://cart/change"), null);
    }

    public void update(String good_id, int count) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("count", count);

        db.update("cart", contentValues, "good_id = ?", new String[]{good_id});

        db.close();

        context.getContentResolver().notifyChange(Uri.parse("content://cart/change"), null);
    }

    public List<CartBean> findAllByPhoneNumber(String phoneNumber) {
        SQLiteDatabase db = mDBHelp.getWritableDatabase();

        Cursor cursor = db.query("cart", null, "username = ?", new String[]{phoneNumber}, null, null, "_id desc");
        List<CartBean> mList = new ArrayList<>();
        while (cursor.moveToNext()) {
            CartBean cartBean = new CartBean();
            cartBean.count = cursor.getInt(cursor.getColumnIndex("count"));
            cartBean.good_id = cursor.getString(cursor.getColumnIndex("good_id"));
            cartBean.good_name = cursor.getString(cursor.getColumnIndex("good_name"));
            cartBean.good_price = cursor.getString(cursor.getColumnIndex("good_price"));
            cartBean.good_img_url = cursor.getString(cursor.getColumnIndex("good_img_url"));
            mList.add(cartBean);
        }
        cursor.close();
        db.close();
        return mList;
    }

}
