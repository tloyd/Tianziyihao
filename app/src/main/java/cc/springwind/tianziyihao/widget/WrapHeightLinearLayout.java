package cc.springwind.tianziyihao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by HeFan on 2016/7/15.
 */
public class WrapHeightLinearLayout extends LinearLayout {
    public WrapHeightLinearLayout(Context context) {
        super(context);
    }

    public WrapHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // The great Android "hackatlon", the love, the magic.
        // The two leftmost bits in the height measure spec have
        // a special meaning, hence we can't use them to describe height.
        int heightSpec;
        heightSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
