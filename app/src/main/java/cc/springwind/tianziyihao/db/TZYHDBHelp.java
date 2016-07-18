package cc.springwind.tianziyihao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HeFan on 2016/7/18.
 */
public class TZYHDBHelp extends SQLiteOpenHelper {

    public TZYHDBHelp(Context context) {
        super(context, "tzyh.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cart " +
                "(_id integer primary key autoincrement , " +
                "good_id varchar(20)," +
                "good_name varchar(20), " +
                "good_img_url text," +
                "good_price text," +
                "count integer);");
        /*db.execSQL("create table favourate " +
                "(_id integer primary key autoincrement , " +
                "good_id varchar(20)," +
                "good_name varchar(20), " +
                "good_img_url text," +
                "good_price text);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
