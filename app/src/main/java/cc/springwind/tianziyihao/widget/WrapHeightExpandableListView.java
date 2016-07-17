package cc.springwind.tianziyihao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by HeFan on 2016/7/14.
 */
public class WrapHeightExpandableListView extends ExpandableListView {
    public WrapHeightExpandableListView(Context context) {
        super(context);
    }

    public WrapHeightExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapHeightExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        // The great Android "hackatlon", the love, the magic.
        // The two leftmost bits in the height measure spec have
        // a special meaning, hence we can't use them to describe height.
        heightSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
