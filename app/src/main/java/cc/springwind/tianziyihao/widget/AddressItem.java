package cc.springwind.tianziyihao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.bean.AddressBean;

/**
 * Created by HeFan on 2016/7/30.
 */
public class AddressItem extends LinearLayout {
    private TextView tvReceiveUser;
    private TextView tvPhoneNumber;
    private TextView tvAddressDetail;

    public AddressItem(Context context) {
        this(context, null);
    }

    public AddressItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddressItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_address, this);

        initUI();
    }

    private void initUI() {
        tvReceiveUser = (TextView) findViewById(R.id.tv_receive_user);
        tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        tvAddressDetail = (TextView) findViewById(R.id.tv_address_detail);
    }

    public void setTvAddressDetail(String s) {
        tvAddressDetail.setText(s);
    }

    public void setTvReceiveUser(String s) {
        tvReceiveUser.setText(s);
    }

    public void setTvPhoneNumber(String s) {
        tvPhoneNumber.setText(s);
    }

    public void setItem(AddressBean bean) {
        setTvAddressDetail(bean.district+" "+bean.specifiec_address);
        setTvPhoneNumber(bean.receive_tel);
        setTvReceiveUser(bean.receive_name);
    }
}
