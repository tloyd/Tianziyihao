package cc.springwind.tianziyihao.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cc.springwind.tianziyihao.db.DBHelp;
import cc.springwind.tianziyihao.db.bean.AddressBean;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;

/**
 * Created by HeFan on 2016/7/30.
 */
public class AddressDao {
    private static AddressDao dao;
    private final DBHelp dbHelp;
    private Context context;

    private AddressDao(Context context) {
        dbHelp = new DBHelp(context);
        this.context = context;
    }

    public static AddressDao getInstance(Context context) {
        if (dao == null) {
            dao = new AddressDao(context);
        }
        return dao;
    }

    public ArrayList<AddressBean> findAll() {
        ArrayList<AddressBean> list = new ArrayList<>();
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Cursor cursor = db.query("address", null, "username=?", new String[]{SpUtil.getString(context, Constants
                .CURRENT_USER, "")}, null, null, null);
        while (cursor.moveToNext()) {
            AddressBean bean = new AddressBean();
            bean.address_id = cursor.getInt(cursor.getColumnIndex("address_id"));
            bean.district = cursor.getString(cursor.getColumnIndex("district"));
            bean.specifiec_address = cursor.getString(cursor.getColumnIndex("specifiec_address"));
            bean.receive_tel = cursor.getString(cursor.getColumnIndex("receive_tel"));
            bean.receive_name = cursor.getString(cursor.getColumnIndex("receive_name"));
            bean.district_code = cursor.getString(cursor.getColumnIndex("district_code"));
            list.add(bean);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void insert(AddressBean bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("district", bean.district);
        values.put("specifiec_address", bean.specifiec_address);
        values.put("receive_tel", bean.receive_tel);
        values.put("receive_name", bean.receive_name);
        values.put("district_code", bean.district_code);
        values.put("username", SpUtil.getString(context, Constants.CURRENT_USER, ""));
        long l = db.insert("address", null, values);

        if (l != -1) {
            ToastUtil.showToast(context, "添加成功!");
        } else {
            ToastUtil.showToast(context, "添加失败!");
        }

        db.close();
    }

    public void setDefault(int address_id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isDefault", 1);
        int i = db.update("address", values, "address_id = ?", new String[]{address_id + ""});
        values.clear();
        values.put("isDefault", 0);
        db.update("address", values, "address_id <> ?", new String[]{address_id + ""});

        if (i == 0) {
            ToastUtil.showToast(context, "设置失败!");
        } else {
            ToastUtil.showToast(context, "设置成功!");
        }

        db.close();
    }

    public void delete(int address_id) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        int i = db.delete("address", "address_id=?", new String[]{address_id + ""});
        if (i == 0) {
            ToastUtil.showToast(context, "删除失败!");
        } else {
            ToastUtil.showToast(context, "删除成功!");
        }
        db.close();
    }

    public void update(AddressBean bean) {
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("district", bean.district);
        values.put("specifiec_address", bean.specifiec_address);
        values.put("district_code", bean.district_code);
        values.put("receive_name", bean.receive_name);
        values.put("receive_tel", bean.receive_tel);

        int i = db.update("address", values, "address_id=?", new String[]{bean.address_id + ""});

        if (i != 0) {
            ToastUtil.showToast(context, "更新成功!");
        } else {
            ToastUtil.showToast(context, "更新失败!");
        }

        db.close();
    }

    /**
     * @return 得到默认的地址信息对象
     */
    public AddressBean findDefault() {
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        AddressBean bean = null;
        Cursor cursor = db.query("address", null, "isDefault=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            bean = new AddressBean();
            bean.address_id = cursor.getInt(cursor.getColumnIndex("address_id"));
            bean.district = cursor.getString(cursor.getColumnIndex("district"));
            bean.specifiec_address = cursor.getString(cursor.getColumnIndex("specifiec_address"));
            bean.receive_tel = cursor.getString(cursor.getColumnIndex("receive_tel"));
            bean.receive_name = cursor.getString(cursor.getColumnIndex("receive_name"));
            bean.district_code = cursor.getString(cursor.getColumnIndex("district_code"));
        }
        db.close();
        return bean;
    }
}
