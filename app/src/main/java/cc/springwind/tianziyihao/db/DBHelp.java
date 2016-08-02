package cc.springwind.tianziyihao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by HeFan on 2016/7/18.
 */
public class DBHelp extends SQLiteOpenHelper implements Serializable {

    public DBHelp(Context context) {
        super(context, "tzyh.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*创建购物车表*/
        db.execSQL("CREATE TABLE cart " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "good_id TEXT," +
                "good_name TEXT, " +
                "good_img_url TEXT," +
                "good_price TEXT," +
                "username TEXT, " +
                "count INTEGER);");
        /*创建收藏表,有两个字段,表示哪个用户收藏了哪个商品*/
        db.execSQL("create table favourate " +
                "(_id integer primary key autoincrement , " +
                "good_id TEXT NOT NULL," +
                "username TEXT NOT NULL);");
        /*创建用户信息表,用三个字段,_id,用户名(手机号),密码,积分,账户余额*/
        db.execSQL("CREATE TABLE userinfo " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "username VARCHAR(20) UNIQUE," +
                "score INTEGER DEFAULT 0, " +
                "account REAL DEFAULT 10000, " +
                "pay_password TEXT, " +
                "password VARCHAR(20));");
        /*创建用户资料表,此表用来储存用户的基本资料,如积分,昵称,性别,年纪等,当前版本只实现了积分呵呵*/
        /*db.execSQL("CREATE TABLE userdata " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "scole INTEGER DEFAULT 0, " +
                "user_id INTEGER NOT NULL)");*/
        // TODO: 2016/7/24 0024 外键到底能不能用
        //FOREIGN KEY user_id REFERENCES userinfo(_id)
        /*创建触发器,在注册新用户时候,自动初始化生成新用户的userdata表,*/
        /*db.execSQL("CREATE TRIGGER init_user_data AFTER INSERT ON userinfo " +
                "BEGIN" +
                "INSERT INTO userdata (user_id) VALUES (SELECT _id FROM userinfo)" +
                "END");*/
        /*db.execSQL("create table goods(\n" +
                "_id integer primary key autoincrement,\n" +
                "first_class_id integer not null,\n" +
                "second_class_id integer not null,\n" +
                "good_id integer not null,\n" +
                "good_name text default \"商品\",\n" +
                "good_price real default 10.0,\n" +
                "good_price_origin real default 20.0,\n" +
                "thumbnail_img_url text default \"http://tva4.sinaimg.cn/crop.22.9.214.214" +
                ".180/5d60116cgw1ewot81f3evj207e07emx8.jpg\",\n" +
                "is_limit boolean default 1,\n" +
                "is_home boolean default 1,\n" +
                "description text default \"商品简介\",\n" +
                "note text default \"注意事项\",\n" +
                "rate_score real default 0,\n" +
                "date_on_sale date,\n" +
                "sale_count integer default 0)");
        db.execSQL("create table good_data(\n" +
                "good_id integer,\n" +
                "data text,\n" +
                "type integer\n" +
                ")");*/
        /*创建订单详情表*/
        db.execSQL("create table user_order(" +
                "order_id integer primary key autoincrement," +
                "username text," +
                "sum_price real," +
                "receive_district_code text," +
                "receive_name text," +
                "receive_tel text," +
                "receive_address text," +
                // 0代表未未付款 1代表已付款
                "order_flag integer" +
                ")");
        /*创建地址表*/
        db.execSQL("create table address(" +
                "address_id integer primary key autoincrement," +
                "district text," +
                "specifiec_address text," +
                "receive_tel text," +
                "receive_name text," +
                "district_code text," +
                "username text," +
                "isDefault boolean default 0" +
                ")");
        /*创建优惠券表*/
        db.execSQL("create table coupon (_id integer primary key autoincrement," +
                "coupon_name text," +
                "coupon_type integer," +
                "coupon_value real," +
                "coupon_qualify real default 0," +
                "username text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
