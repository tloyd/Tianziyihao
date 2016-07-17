package cc.springwind.tianziyihao.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.dao.FakeDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.GoodDetailActivity;
import cc.springwind.tianziyihao.widget.WrapHeightExpandableListView;
import cc.springwind.tianziyihao.widget.GoodListItem;
import cc.springwind.tianziyihao.widget.GoodListTitle;
import cc.springwind.tianziyihao.widget.WrapHeightGridView;

/**
 * Created by HeFan on 2016/7/7.
 */
public class HomeFragment extends BaseFragment {

    private ViewPager viewPager;
    private View view;
    private ArrayList<ImageView> listOfImageView;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<String> listOfImageUrl;
    private String[] imageUrls = new String[]{
            "http://ww4.sinaimg.cn/mw690/651ebbd5gw1ew4dcw7rwjj20j60cs0vc.jpg", "http://ww2.sinaimg" +
            ".cn/mw690/651ebbd5gw1ew4ddecr4ij21hc0u0tha.jpg", "http://ww1.sinaimg" +
            ".cn/mw690/651ebbd5gw1ew4ddi3tjxj21hc0u0q95.jpg", "http://ww3.sinaimg" +
            ".cn/mw690/651ebbd5gw1ew4ddjfy82j21hc0u07d6.jpg"
    };
    private LinearLayout llHottestIndicator;
    private int currentItem = 0;
    private WrapHeightExpandableListView eListView;
    private List<FakeDao.HomeGoodGroup> listOfHomeGoodGroup;
    private FakeDao fakeDao;
    private List<FakeDao.HomeLimitPurchaseGood> listOfHomeLimitPurchaseGood;
    private boolean isplay = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        initImageUrls();
        initAdGView();
        initImageViewList();
        initDots();
        initViewPager();
        initEListView();
        return view;
    }

    private void initAdGView() {
        fakeDao = new FakeDao();
        listOfHomeLimitPurchaseGood = fakeDao.getHomeLimitPurchaseList();
        WrapHeightGridView gv_limit_home = (WrapHeightGridView) view.findViewById(R.id.gv_limit_home);
//        GridView gv_limit_home = (GridView) view.findViewById(R.id.gv_limit_home);
        GViewAdapter adapter = new GViewAdapter();
        gv_limit_home.setPadding(5, 5, 5, 5);
        gv_limit_home.setAdapter(adapter);
    }

    class GViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listOfHomeLimitPurchaseGood.size();
        }

        @Override
        public FakeDao.HomeLimitPurchaseGood getItem(int position) {
            return listOfHomeLimitPurchaseGood.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            GoodListItem goodListItem = null;
            if (convertView == null) {
                goodListItem = new GoodListItem(getContext());
            } else {
                goodListItem = (GoodListItem) convertView;
            }
            goodListItem.setIvGoodThumb(getItem(position).url);
            goodListItem.setTvGoodPrice(getItem(position).price);
            goodListItem.setTvGoodName(getItem(position).name);
            goodListItem.setTvLimitPurchase(getItem(position).limitPurchase);
            goodListItem.setTvGoodPriceOrigin(getItem(position).priceOrigin);
            goodListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getContext(), GoodDetailActivity.class);
                    intent.putExtra("id",getItem(position).id);
                    startActivity(intent);
                }
            });
            return goodListItem;
            /*ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_limit_good, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            FakeDao.Limit item = getItem(position);

            ImageLoader instance = ImageLoader.getInstance();
            instance.displayImage(item.url, viewHolder.ivGoodThumbLimit);
//            viewHolder.ivGoodThumbLimit.setImageResource(R.drawable.logo);
            viewHolder.tvGoodNameLimit.setText(item.name);
            viewHolder.tvGoodPriceLimit.setText(item.price);
            viewHolder.tvGoodPriceOriginLimit.setText(item.priceOrigin);
            viewHolder.tvLimitPurchaseLimit.setText(item.limitPurchase);
            return convertView;*/
            /*TextView textView=null;
            if (convertView==null){
                textView = new TextView(getContext());
                textView.setText("测试");
                textView.setTextSize(30);
            } else {
                textView= (TextView) convertView;
            }
            return textView;*/
        }

        /*class ViewHolder {
            @InjectView(R.id.iv_good_thumb_limit)
            ImageView ivGoodThumbLimit;
            @InjectView(R.id.tv_limit_purchase_limit)
            TextView tvLimitPurchaseLimit;
            @InjectView(R.id.tv_good_name_limit)
            TextView tvGoodNameLimit;
            @InjectView(R.id.tv_good_price_limit)
            TextView tvGoodPriceLimit;
            @InjectView(R.id.tv_good_price_origin_limit)
            TextView tvGoodPriceOriginLimit;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }*/
    }

    private void initEListView() {
        eListView = (WrapHeightExpandableListView) view.findViewById(R.id.elv_home);

        listOfHomeGoodGroup = fakeDao.getHomeGoodLists();
        ExpandableAdapter adapter = new ExpandableAdapter();
        eListView.setAdapter(adapter);
        eListView.setGroupIndicator(null);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            eListView.expandGroup(i);
        }
        eListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        setListViewHeightBasedOnChildren(eListView);
        adapter.notifyDataSetChanged();
    }

    public static void setListViewHeightBasedOnChildren(ExpandableListView expandableListView) {
        // 获取ListView对应的Adapter
        ExpandableListAdapter listAdapter = expandableListView.getExpandableListAdapter();
        if (listAdapter == null) {
            // pre -condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listgroupItem = listAdapter.getGroupView(i, true, null, expandableListView);
            listgroupItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listgroupItem.getMeasuredHeight(); // 统计所有子项的总高度
            for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                View listchildItem = listAdapter.getChildView(i, j, false, null, expandableListView);
                listchildItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listchildItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }

        ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
        params.height = totalHeight + (expandableListView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        expandableListView.setLayoutParams(params);
    }

    class ExpandableAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return listOfHomeGoodGroup.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public FakeDao.HomeGoodGroup getGroup(int groupPosition) {
            return listOfHomeGoodGroup.get(groupPosition);
        }

        @Override
        public FakeDao.HomeGoodChild getChild(int groupPosition, int childPosition) {
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
                goodListTitle = new GoodListTitle(getContext());
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
                gridView = new WrapHeightGridView(getContext());
            } else {
                gridView = (WrapHeightGridView) convertView;
            }

            gridView.setPadding(5, 5, 5, 5);
            gridView.setNumColumns(3);
            gridView.setAdapter(new ListGViewAdaptar(getGroup(groupPosition).homeGoodChildList));
            return gridView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }


    }

    private class ListGViewAdaptar extends BaseAdapter {
        List<FakeDao.HomeGoodChild> homeGoodChildren;

        public ListGViewAdaptar(List<FakeDao.HomeGoodChild> homeGoodChildList) {
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
                goodListItem = new GoodListItem(getContext());
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


    /*private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.debug(activity.TAG, "receive msg:" + msg.what);
            if (mHandler.hasMessages(MSG_UPDATE_IMAGE)) {
                LogUtil.debug(activity.TAG, "hasMessages msg:" + msg.what);

                mHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    LogUtil.debug(activity.TAG, "MSG_UPDATE_IMAGE");
                    currentItem++;
                    viewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    LogUtil.debug(activity.TAG, "MSG_KEEP_SILENT");
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    LogUtil.debug(activity.TAG, "MSG_BREAK_SILENT");
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    LogUtil.debug(activity.TAG, "MSG_PAGE_CHANGED");
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
            }
        }
    };*/

    private ImageView[] mBottomImages;//底部只是当前页面的小圆点

    private void initDots() {
        //创建底部指示位置的导航栏
        mBottomImages = new ImageView[listOfImageView.size()];
        llHottestIndicator = (LinearLayout) view.findViewById(R.id.ll_hottest_indicator);

        for (int i = 0; i < mBottomImages.length; i++) {
            ImageView imageView = new ImageView(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.icon_point_pre);
            } else {
                imageView.setBackgroundResource(R.drawable.icon_point);
            }

            mBottomImages[i] = imageView;
            //把指示作用的原点图片加入底部的视图中
            llHottestIndicator.addView(mBottomImages[i]);
        }
    }

    private void initImageUrls() {
        listOfImageUrl = new ArrayList<>();
        for (String url : imageUrls) {
            listOfImageUrl.add(url);
        }
    }

    private void initImageViewList() {
        listOfImageView = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view = new ImageView(activity);
            view.setTag(imageUrls[i]);
            if (i == 0)//给一个默认图
                view.setBackgroundResource(R.drawable.logo);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            listOfImageView.add(view);
        }
    }

    private void initViewPager() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
        //开始轮播效果
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (isplay) {
                    try {
                        Thread.sleep(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();
    }


    private class MyPagerAdapter extends PagerAdapter {

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

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int total = mBottomImages.length;
            for (int j = 0; j < total; j++) {
                if (j == position) {
                    mBottomImages[j].setBackgroundResource(R.drawable.icon_point_pre);
                } else {
                    mBottomImages[j].setBackgroundResource(R.drawable.icon_point);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
