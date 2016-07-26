package cc.springwind.tianziyihao.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.ui.fragment.HomeFragment;
import cc.springwind.tianziyihao.widget.GoodListTitle;
import cc.springwind.tianziyihao.widget.WrapHeightGridView;

/**
 * Created by HeFan on 2016/7/21 0021.
 * <p/>
 * 首页分类扩展列表的适配器
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private HomeFragment homeFragment;
    List<GoodsDao.HomeGoodGroup> listOfHomeGoodGroup;

    public ExpandableAdapter(HomeFragment homeFragment, List<GoodsDao.HomeGoodGroup> listOfHomeGoodGroup) {
        this.homeFragment = homeFragment;
        this.listOfHomeGoodGroup = listOfHomeGoodGroup;
    }

    @Override
    public int getGroupCount() {
        return listOfHomeGoodGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public GoodsDao.HomeGoodGroup getGroup(int groupPosition) {
        return listOfHomeGoodGroup.get(groupPosition);
    }

    @Override
    public GoodsDao.HomeGoodChild getChild(int groupPosition, int childPosition) {
        return listOfHomeGoodGroup.get(groupPosition).homeGoodChildList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GoodListTitle goodListTitle;
        if (convertView == null) {
            goodListTitle = new GoodListTitle(homeFragment.getContext());
        } else {
            goodListTitle = (GoodListTitle) convertView;
        }
        goodListTitle.setTv_elv_group_title(getGroup(groupPosition).name);
        return goodListTitle;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        WrapHeightGridView gridView;
        /*ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height= LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width=LinearLayout.LayoutParams.MATCH_PARENT;
        gridView.setLayoutParams(params);*/

        if (convertView == null) {
            gridView = new WrapHeightGridView(homeFragment.getContext());
        } else {
            gridView = (WrapHeightGridView) convertView;
        }

        gridView.setPadding(5, 5, 5, 5);
        gridView.setNumColumns(3);
        gridView.setAdapter(new ExpandableListGridViewAdaptar(homeFragment, getGroup(groupPosition).homeGoodChildList));
        return gridView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
