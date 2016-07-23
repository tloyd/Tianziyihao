package cc.springwind.tianziyihao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HeFan on 2016/7/18.
 */
public class DBHelp extends SQLiteOpenHelper {

    public DBHelp(Context context) {
        super(context, "tzyh.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*创建购物车表*/
        db.execSQL("CREATE TABLE cart " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "good_id VARCHAR(20)," +
                "good_name VARCHAR(20), " +
                "good_img_url TEXT," +
                "good_price TEXT," +
                "count INTEGER);");
        /*db.execSQL("create table favourate " +
                "(_id integer primary key autoincrement , " +
                "good_id varchar(20)," +
                "good_name varchar(20), " +
                "good_img_url text," +
                "good_price text);");*/
        /*创建用户信息表,用三个字段,_id,用户名(手机号),密码*/
        db.execSQL("CREATE TABLE userinfo " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "username VARCHAR(20)," +
                "password VARCHAR(20));");
        /*创建用户资料表,此表用来储存用户的基本资料,如积分,昵称,性别,年纪等,当前版本只实现了积分呵呵*/
        db.execSQL("CREATE TABLE userdata " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "scole INTEGER default 0, " +
                "user_id INTEGER NOT NULL)");//FOREIGN KEY user_id REFERENCES userinfo(_id)
        /*创建触发器,在注册新用户时候,自动初始化生成新用户的userdata表,*/
        /*db.execSQL("CREATE TRIGGER init_user_data AFTER INSERT ON userinfo " +
                "BEGIN" +
                "INSERT INTO userdata (user_id) VALUES (SELECT _id FROM userinfo)" +
                "END");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
