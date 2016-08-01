package cc.springwind.tianziyihao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cc.springwind.tianziyihao.db.DBHelp;

/**
 * Created by HeFan on 2016/7/31.
 */
public class FavourateDao {
    private static FavourateDao dao;
    private Context context;
    private DBHelp dbHelp;

    private FavourateDao(Context context) {
        this.context = context;
        this.dbHelp = new DBHelp(context);
    }

    public static FavourateDao getInstance(Context context) {
        if (dao == null) {
            dao = new FavourateDao(context);
        }
        return dao;
    }

    public List<GoodsDao.GoodSimpleInfo> findAll(String username) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        GoodsDao goodsDao = GoodsDao.getInstance(context);
        List<GoodsDao.GoodSimpleInfo> mList = new ArrayList<>();
        Cursor cursor = db.query("favourate", null, "username=?", new String[]{username}, null, null, null);
        while (cursor.moveToNext()) {
            String good_id = cursor.getString(cursor.getColumnIndex("good_id"));
            GoodsDao.GoodSimpleInfo child = goodsDao.queryGoodInfoById(good_id);
            mList.add(child);
        }
        cursor.close();
        db.close();
        return mList;
    }

    public boolean insert(String username, String good_id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("good_id", good_id);
        long l = db.insert("favourate", null, values);
        if (l != -1)
            return true;
        db.close();
        context.getContentResolver().notifyChange(Uri.parse("content://favourate/change"), null);
        return false;
    }

    public boolean isExist(String username, String good_id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Cursor cursor = db.query("favourate", null, "username=? and good_id=?", new String[]{username, good_id}, null,
                null, null);
        while (cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean delete(String username, String good_id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int i = db.delete("favourate", "username=? and good_id=?", new String[]{username, good_id});
        if (i == 0) {
            return false;
        }
        db.close();
        context.getContentResolver().notifyChange(Uri.parse("content://favourate/change"), null);
        return true;
    }
}
