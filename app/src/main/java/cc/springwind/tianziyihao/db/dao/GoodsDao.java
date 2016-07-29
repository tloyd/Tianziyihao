package cc.springwind.tianziyihao.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cc.springwind.tianziyihao.entity.GoodDetailInfo;

/**
 * Created by HeFan on 2016/7/26.
 */
public class GoodsDao implements Serializable {
    private static GoodsDao dao = null;
    public static String path = "data/data/cc.springwind.tianziyihao/files/tzyh.db";

    private GoodsDao(Context context) {
    }

    public static GoodsDao getInstance(Context context) {
        if (dao == null) {
            dao = new GoodsDao(context);
        }
        return dao;
    }

    public List<HomeLimitPurchaseGood> getHomeLimitPurchaseList() {
//        SQLiteDatabase db = mDBHelp.getWritableDatabase();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        List<HomeLimitPurchaseGood> list = new ArrayList<>();

        Cursor cursor = db.query("goods", null, "is_limit = ?", new String[]{"1"}, null, null, "_id desc");
        while (cursor.moveToNext()) {
            HomeLimitPurchaseGood good = new HomeLimitPurchaseGood();
            good.id = cursor.getString(cursor.getColumnIndex("good_id"));
            good.url = cursor.getString(cursor.getColumnIndex("thumbnail_img_url"));
            good.price = cursor.getString(cursor.getColumnIndex("good_price"));
            good.limitPurchase = "限时购买";
            good.name = cursor.getString(cursor.getColumnIndex("good_name"));
            good.priceOrigin = cursor.getString(cursor.getColumnIndex("good_price_origin"));
            list.add(good);
        }
        cursor.close();
        db.close();
        return list;
    }

    public GoodDetailInfo queryGoodDetailById(String id) {
        GoodDetailInfo goodDetailInfo = new GoodDetailInfo();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.query("goods", null, "good_id = ?", new String[]{id}, null, null, null);

        while (cursor.moveToNext()) {
            goodDetailInfo.good_name = cursor.getString(cursor.getColumnIndex("good_name"));
            goodDetailInfo.price = cursor.getString(cursor.getColumnIndex("good_price"));
            goodDetailInfo.price_original = cursor.getString(cursor.getColumnIndex("good_price_origin"));
            goodDetailInfo.good_description = cursor.getString(cursor.getColumnIndex("description"));
            goodDetailInfo.good_id = cursor.getString(cursor.getColumnIndex("good_id"));
            goodDetailInfo.notes = cursor.getString(cursor.getColumnIndex("note"));
            goodDetailInfo.thumbnail_img_url = cursor.getString(cursor.getColumnIndex("thumbnail_img_url"));

            goodDetailInfo.reveal_img_urls = getGoodData(id, 0);
            goodDetailInfo.good_params = getGoodData(id, 1);
            goodDetailInfo.detail_img_urls = getGoodData(id, 2);
        }

        cursor.close();
        db.close();
        return goodDetailInfo;
    }

    private List<String> getGoodData(String id, int i) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.query("good_data", null, "good_id = ? and type=?", new String[]{id, i + ""}, null, null,
                null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndex("data"));
            list.add(data);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<HomeGoodGroup> getHomeGoodLists() {
        List<HomeGoodGroup> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        Cursor cursor = db.query(true, "goods", new String[]{"first_class_id"}, null, null, null, null, null,
                null);

        while (cursor.moveToNext()) {
            HomeGoodGroup group = new HomeGoodGroup();
            group.first_class_id = cursor.getInt(cursor.getColumnIndex("first_class_id"));
            switch (group.first_class_id) {
                case 1:
                    group.name = "生鲜肉类";
                    break;
                case 2:
                    group.name = "禽蛋粮油";
                    break;
            }
            Cursor goods = db.query("goods", null, "first_class_id = ?", new String[]{String.valueOf(group
                    .first_class_id)}, null, null, null);
            List<HomeGoodChild> childList = new ArrayList<>();
            while (goods.moveToNext()) {
                HomeGoodChild child = new HomeGoodChild();

                child.good_id = goods.getInt(goods.getColumnIndex("good_id"));
                child.name = goods.getString(goods.getColumnIndex("good_name"));
                child.price = String.valueOf(goods.getFloat(goods.getColumnIndex("good_price")));
                child.url = goods.getString(goods.getColumnIndex("thumbnail_img_url"));
                child.priceOrigin=goods.getString(goods.getColumnIndex("good_price_origin"));

                childList.add(child);
            }
            group.homeGoodChildList = childList;
            list.add(group);
            goods.close();
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<ClassifyGroup> getClassifyGroupList() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        List<ClassifyGroup> groupList = new ArrayList<>();

        Cursor cursor = db.query(true, "goods", new String[]{"first_class_id"}, null, null, null, null, null,
                null);

        while (cursor.moveToNext()) {
            ClassifyGroup group = new ClassifyGroup();
            group.first_class_id = cursor.getInt(cursor.getColumnIndex("first_class_id"));
            switch (group.first_class_id) {
                case 1:
                    group.name = "生鲜肉类";
                    break;
                case 2:
                    group.name = "禽蛋粮油";
                    break;
            }

            Cursor second = db.query(true, "goods", new String[]{"second_class_id"}, "first_class_id=?", new
                    String[]{String.valueOf(group.first_class_id)}, null, null, null, null);

            List<SecondLevelGroup> childList = new ArrayList<>();
            while (second.moveToNext()) {
                SecondLevelGroup child = new SecondLevelGroup();
                child.first_class_id = group.first_class_id;
                child.name = "二级分类";
                child.second_class_id = String.valueOf(second.getInt(second.getColumnIndex("second_class_id")));
                child.url = "http://tva4.sinaimg.cn/crop.22.9.214.214.180/5d60116cgw1ewot81f3evj207e07emx8.jpg";
                childList.add(child);
            }
            group.groupList = childList;
            second.close();
            groupList.add(group);
        }
        cursor.close();
        db.close();
        return groupList;
    }

    public List<GoodSimpleInfo> getClassifyGoodListByClassId(String first_class_id, String second_class_id, String
            aDefault) {
        List<GoodSimpleInfo> mList = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        Cursor cursor = db.query("goods", null, "first_class_id=? and second_class_id=?", new
                        String[]{first_class_id, second_class_id}, null, null, aDefault + " desc",
                null);

        while (cursor.moveToNext()) {
            GoodSimpleInfo info = new GoodSimpleInfo();
            info.id = cursor.getString(cursor.getColumnIndex("good_id"));
            info.name = cursor.getString(cursor.getColumnIndex("good_name"));
            info.price = cursor.getFloat(cursor.getColumnIndex("good_price"));
            info.url = cursor.getString(cursor.getColumnIndex("thumbnail_img_url"));
            info.saleCount = cursor.getInt(cursor.getColumnIndex("sale_count"));
            mList.add(info);
        }

        cursor.close();
        db.close();
        return mList;
    }

    public List<GoodSimpleInfo> getClassifyGoodListByGoodName(String good_name) {
        List<GoodSimpleInfo> mList = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        Cursor cursor = db.rawQuery("select * from goods where good_name like '%" + good_name + "%'", null);

        while (cursor.moveToNext()) {
            GoodSimpleInfo info = new GoodSimpleInfo();
            info.id = cursor.getString(cursor.getColumnIndex("good_id"));
            info.name = cursor.getString(cursor.getColumnIndex("good_name"));
            info.price = cursor.getFloat(cursor.getColumnIndex("good_price"));
            info.url = cursor.getString(cursor.getColumnIndex("thumbnail_img_url"));
            info.saleCount = cursor.getInt(cursor.getColumnIndex("sale_count"));
            mList.add(info);
        }

        cursor.close();
        db.close();
        return mList;
    }

    public class GoodSimpleInfo {
        public String id;
        public String name;
        public float price;
        public String url;
        public int saleCount;
    }

    public class ClassifyGroup implements Serializable {
        public int first_class_id;
        public String name;
        public List<SecondLevelGroup> groupList;
    }

    public class SecondLevelGroup implements Serializable {
        public int first_class_id;
        public String second_class_id;
        public String name;
        public String url;
    }

    public class HomeGoodGroup {
        public int first_class_id;
        public String name;
        public List<HomeGoodChild> homeGoodChildList;
    }

    public class HomeGoodChild {
        public int good_id;
        public String name;
        public String price;
        public String url;
        public String priceOrigin;
    }

    public class HomeLimitPurchaseGood {
        public String id;
        public String url;
        public String price;
        public String limitPurchase;
        public String name;
        public String priceOrigin;
    }
}
