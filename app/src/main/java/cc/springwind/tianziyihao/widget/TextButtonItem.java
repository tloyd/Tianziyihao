package cc.springwind.tianziyihao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.springwind.tianziyihao.R;

/**
 * Created by HeFan on 2016/7/19.
 */
public class TextButtonItem extends LinearLayout {

    private ImageView iv_left_indicator;
    private TextView tv_left_title;
    private TextView tv_right_detail;
    private ImageView iv_right_indicator;

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    private String tbi_tv_left;
    private int tbi_iv_left;
    private String tbi_tv_right;
    private int tbi_iv_right;

    public TextButtonItem(Context context) {
        this(context, null);
    }

    public TextButtonItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextButtonItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_text_button, this);

        initView();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable
                .cc_springwind_tianziyihao_widget_TextButtonItem);

        initAttrs(array);

    }

    private void initAttrs(TypedArray array) {
        if (array != null) {
            tbi_tv_left = array.getString(R.styleable.cc_springwind_tianziyihao_widget_TextButtonItem_tbi_tv_left);
            tbi_tv_right = array.getString(R.styleable.cc_springwind_tianziyihao_widget_TextButtonItem_tbi_tv_right);
            tbi_iv_left = array.getResourceId(R.styleable
                    .cc_springwind_tianziyihao_widget_TextButtonItem_tbi_iv_left, -1);
            tbi_iv_right = array.getResourceId(R.styleable
                    .cc_springwind_tianziyihao_widget_TextButtonItem_tbi_iv_right, -1);
        }
        if (tbi_tv_left != null) {
            tv_left_title.setText(tbi_tv_left);
        }
        if (tbi_tv_right != null) {
            tv_right_detail.setText(tbi_tv_right);
        }
        if (tbi_iv_left != -1) {
            iv_left_indicator.setImageResource(tbi_iv_left);
        }
        if (tbi_iv_right != -1) {
            iv_right_indicator.setImageResource(tbi_iv_right);
        }
    }

    private void initView() {
        iv_left_indicator = (ImageView) findViewById(R.id.iv_left_indicator);
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        tv_right_detail = (TextView) findViewById(R.id.tv_right_detail);
        iv_right_indicator = (ImageView) findViewById(R.id.iv_right_indicator);
    }


}
