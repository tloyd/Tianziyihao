package cc.springwind.tianziyihao.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cc.springwind.tianziyihao.db.DBHelp;

/**
 * Created by HeFan on 2016/8/2.
 */
public class CouponDao {
    private static CouponDao dao;
    private final DBHelp dbHelp;
    private Context context;

    private CouponDao(Context context) {
        dbHelp = new DBHelp(context);
        this.context = context;
    }

    public static CouponDao getInstance(Context context) {
        if (dao == null) {
            dao = new CouponDao(context);
        }
        return dao;
    }

    public List<Coupon> findAll(String username) {
        List<Coupon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.query("coupon", null, "username=?", new String[]{username}, null, null, null);
        while (cursor.moveToNext()) {
            Coupon coupon = new Coupon();
            coupon._id = cursor.getInt(cursor.getColumnIndex("_id"));
            coupon.couponType = cursor.getInt(cursor.getColumnIndex("coupon_type"));
            coupon.username = cursor.getString(cursor.getColumnIndex("username"));
            coupon.couponName = cursor.getString(cursor.getColumnIndex("coupon_name"));
            coupon.couponValue = cursor.getFloat(cursor.getColumnIndex("coupon_value"));
            coupon.couponQuelify = cursor.getFloat(cursor.getColumnIndex("coupon_qualify"));
            list.add(coupon);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void delete(int _id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        db.delete("coupon", "_id=?", new String[]{_id + ""});
        db.close();
    }

    /**
     * "coupon_name text," +
     * "coupon_type integer," +
     * "coupon_value real," +
     * "coupon_qualify real default 0," +
     * "username text,"
     */
    public class Coupon {
        public int _id;
        public String couponName;
        public int couponType;
        public float couponValue;
        public float couponQuelify;
        public String username;
    }
}
