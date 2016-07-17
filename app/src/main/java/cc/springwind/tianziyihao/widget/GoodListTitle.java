package cc.springwind.tianziyihao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.springwind.tianziyihao.R;

/**
 * Created by HeFan on 2016/7/9.
 */
public class GoodListTitle extends LinearLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tv_elv_group_title;
    private TextView tv_elv_group_more;

    public GoodListTitle(Context context) {
        this(context, null);
    }

    public GoodListTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodListTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.item_elv_group_title, this);
        tv_elv_group_title = (TextView) findViewById(R.id.tv_elv_group_title);
        tv_elv_group_more = (TextView) findViewById(R.id.tv_elv_group_more);
    }

    public void setTv_elv_group_title(String title) {
        tv_elv_group_title.setText(title);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
