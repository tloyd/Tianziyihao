package cc.springwind.tianziyihao.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cc.springwind.tianziyihao.dao.FakeDao;
import cc.springwind.tianziyihao.ui.fragment.HomeFragment;
import cc.springwind.tianziyihao.widget.GoodListItem;

/**
 * Created by HeFan on 2016/7/21 0021.
 * <p/>
 * 首页分类扩展列表里的GridView的适配器
 */
public class ExpandableListGridViewAdaptar extends BaseAdapter {
    private HomeFragment homeFragment;
    List<FakeDao.HomeGoodChild> homeGoodChildren;

    public ExpandableListGridViewAdaptar(HomeFragment homeFragment, List<FakeDao.HomeGoodChild> homeGoodChildList) {
        this.homeFragment = homeFragment;
        homeGoodChildren = homeGoodChildList;
    }

    @Override
    public int getCount() {
        return homeGoodChildren.size();
    }

    @Override
    public FakeDao.HomeGoodChild getItem(int position) {
        return homeGoodChildren.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodListItem goodListItem;
        if (convertView != null) {
            goodListItem = (GoodListItem) convertView;
        } else {
            goodListItem = new GoodListItem(homeFragment.getContext());
        }
        goodListItem.setTvGoodPrice(getItem(position).price);
        goodListItem.setTvGoodName(getItem(position).name);
        goodListItem.setIvGoodThumb(getItem(position).url);
        return goodListItem;
       /* TextView textView = null;
        if (convertView == null) {
            textView = new TextView(getContext());
            textView.setText("测试");
            textView.setTextSize(30);
        } else {
            textView = (TextView) convertView;
        }
        return textView;*/
    }
}
