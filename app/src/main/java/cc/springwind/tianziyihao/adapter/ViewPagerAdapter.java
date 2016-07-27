package cc.springwind.tianziyihao.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.ui.fragment.ImageFragment;

/**
 * Created by HeFan on 2016/7/21 0021.
 * <p/>
 * 首页轮播广告ViewPager适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<HashMap<String, String>> mapList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Fragment fragment;

    public ViewPagerAdapter(List<HashMap<String, String>> mapList, Fragment homeFragment) {
        this.mapList = mapList;
        this.fragment = homeFragment;
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
        position %= mapList.size();
        final HashMap<String, String> tag = mapList.get(position);
        if (position < 0) {
            position = mapList.size() + position;
        }
        final ImageView imageView = new ImageView(fragment.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(tag.get("small"), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putString("title", "图片详情页");
                args.putString("content_url", tag.get("big"));
                fragment.setArguments(args);
                ViewPagerAdapter.this.fragment.getFragmentManager().beginTransaction().add(R.id.fl_content, fragment,
                        "ImageFragment").addToBackStack("ImageFragment").commit();
            }
        });

        ViewParent vp = imageView.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(imageView);
        }

        (container).addView(imageView);
        return imageView;
    }
}
