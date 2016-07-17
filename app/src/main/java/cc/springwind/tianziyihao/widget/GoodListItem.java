package cc.springwind.tianziyihao.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cc.springwind.tianziyihao.R;

/**
 * Created by HeFan on 2016/7/9.
 */
public class GoodListItem extends LinearLayout {
    private ImageView ivGoodThumb;
    private TextView tvLimitPurchase;
    private TextView tvGoodName;
    private TextView tvGoodPrice;
    private TextView tvGoodPriceOrigin;

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private String attrs_ivGoodThumb;
    private String attrs_tvLimitPurchase;
    private String attrs_tvGoodName;
    private String attrs_tvGoodPrice;
    private String attrs_tvGoodPriceOrigin;

    public GoodListItem(Context context) {
        this(context, null);
    }

    public GoodListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_good, this);

        initUI();
        initAttrs(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initUI() {
        ivGoodThumb = (ImageView) findViewById(R.id.iv_good_thumb);
        tvLimitPurchase = (TextView) findViewById(R.id.tv_limit_purchase);
        tvGoodName = (TextView) findViewById(R.id.tv_good_name);
        tvGoodPrice = (TextView) findViewById(R.id.tv_good_price);
        tvGoodPriceOrigin = (TextView) findViewById(R.id.tv_good_price_origin);
    }

    private void initAttrs(AttributeSet attrs) {

        if (attrs != null) {
            attrs_ivGoodThumb = attrs.getAttributeValue(NAMESPACE, "attrs_ivGoodThumb");
            attrs_tvLimitPurchase = attrs.getAttributeValue(NAMESPACE, "attrs_tvLimitPurchase");
            attrs_tvGoodName = attrs.getAttributeValue(NAMESPACE, "attrs_tvGoodName");
            attrs_tvGoodPrice = attrs.getAttributeValue(NAMESPACE, "attrs_tvGoodPrice");
            attrs_tvGoodPriceOrigin = attrs.getAttributeValue(NAMESPACE, "attrs_tvGoodPriceOrigin");
        }

        if (attrs_ivGoodThumb == null) {
            ivGoodThumb.setImageResource(R.drawable.icon_error);
        } else {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(attrs_ivGoodThumb, ivGoodThumb);
        }

        if (attrs_tvLimitPurchase != null) {
            tvLimitPurchase.setVisibility(VISIBLE);
            tvLimitPurchase.setText(attrs_tvLimitPurchase);
            tvLimitPurchase.setTextColor(getResources().getColor(R.color.tv_bg_Oranger));
        } else {
            tvLimitPurchase.setBackgroundColor(Color.argb(0, 0, 0, 0));
            tvLimitPurchase.setText("");
        }

        if (attrs_tvGoodName != null) {
            tvGoodName.setText(attrs_tvGoodName);
        } else {
            tvGoodName.setText("");
        }

        if (attrs_tvGoodPrice != null) {
            tvGoodPrice.setText(attrs_tvGoodPrice);
        } else {
            tvGoodPrice.setText("");
        }

        if (attrs_tvGoodPriceOrigin != null) {
            tvGoodPriceOrigin.setText(attrs_tvGoodPriceOrigin);
            tvGoodPriceOrigin.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            tvGoodPriceOrigin.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            tvGoodPriceOrigin.setText("");
        }
    }


    public void setTvGoodName(String name) {
        tvGoodName.setText(name);
    }

    public void setTvGoodPrice(String price) {
        tvGoodPrice.setText(price);
    }

    public void setIvGoodThumb(String url) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, ivGoodThumb);
    }

    public void setTvLimitPurchase(String str) {
        tvLimitPurchase.setVisibility(VISIBLE);
        tvLimitPurchase.setText(str);
        tvLimitPurchase.setBackgroundColor(getResources().getColor(R.color.tv_bg_Oranger));
    }

    public void setTvGoodPriceOrigin(String str) {
        tvGoodPriceOrigin.setText(str);
        tvGoodPriceOrigin.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
