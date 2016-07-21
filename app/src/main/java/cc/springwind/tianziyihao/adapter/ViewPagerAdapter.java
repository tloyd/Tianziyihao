package cc.springwind.tianziyihao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by HeFan on 2016/7/21 0021.
 *
 * 首页轮播广告ViewPager适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> listOfImageView;
    private ArrayList<String> listOfImageUrl;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ViewPagerAdapter(ArrayList<ImageView> listOfImageView, ArrayList<String> listOfImageUrl) {
        this.listOfImageView = listOfImageView;
        this.listOfImageUrl = listOfImageUrl;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= listOfImageView.size();
        if (position < 0) {
            position = listOfImageView.size() + position;
        }
        ImageView imageView = listOfImageView.get(position);
        imageLoader.displayImage(listOfImageUrl.get(position), imageView);

        ViewParent vp = imageView.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(imageView);
        }

        (container).addView(listOfImageView.get(position));
        return listOfImageView.get(position);
    }
}
