package cc.springwind.tianziyihao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cc.springwind.tianziyihao.db.DBHelp;
import cc.springwind.tianziyihao.db.bean.Order;

/**
 * Created by HeFan on 2016/8/2.
 */
public class OrderDao {
    private static OrderDao dao;
    private final DBHelp dbHelp;
    private Context context;
    private static String table = "user_order";

    private OrderDao(Context context) {
        dbHelp = new DBHelp(context);
        this.context = context;
    }

    public static OrderDao getInstance(Context context) {
        if (dao == null) {
            dao = new OrderDao(context);
        }
        return dao;
    }


    public void insert(Order order) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", order.username);
        values.put("order_flag", order.order_flag);
        values.put("receive_address", order.receive_address);
        values.put("receive_district_code", order.receive_district_code);
        values.put("receive_name", order.receive_name);
        values.put("sum_price", order.sum_price);
        values.put("receive_tel", order.receive_tel);
        db.insert(table, null, values);
        db.close();
    }
}
