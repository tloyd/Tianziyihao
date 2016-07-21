package cc.springwind.tianziyihao.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.adapter.ExpandableAdapter;
import cc.springwind.tianziyihao.adapter.GridViewAdapter;
import cc.springwind.tianziyihao.adapter.ViewPagerAdapter;
import cc.springwind.tianziyihao.bean.Constants;
import cc.springwind.tianziyihao.dao.FakeDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.widget.WrapHeightExpandableListView;
import cc.springwind.tianziyihao.widget.WrapHeightGridView;

/**
 * Created by HeFan on 2016/7/7.
 *
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private ViewPager viewPager;
    private View view;
    private ArrayList<ImageView> listOfImageView;
    private ArrayList<String> listOfImageUrl;
    // 轮播图片的URL地址数组
    private String[] imageUrls = new String[]{
            "http://ww4.sinaimg.cn/mw690/651ebbd5gw1ew4dcw7rwjj20j60cs0vc.jpg", "http://ww2.sinaimg" +
            ".cn/mw690/651ebbd5gw1ew4ddecr4ij21hc0u0tha.jpg", "http://ww1.sinaimg" +
            ".cn/mw690/651ebbd5gw1ew4ddi3tjxj21hc0u0q95.jpg", "http://ww3.sinaimg" +
            ".cn/mw690/651ebbd5gw1ew4ddjfy82j21hc0u07d6.jpg"
    };
    private LinearLayout llHottestIndicator;
    private WrapHeightExpandableListView eListView;
    private List<FakeDao.HomeGoodGroup> listOfHomeGoodGroup;
    private FakeDao fakeDao;
    private List<FakeDao.HomeLimitPurchaseGood> listOfHomeLimitPurchaseGood;
    // 设置控制播放标志位
    private boolean isplay = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        isplay = true;
        initImageUrls();
        initLimitGridView();
        initImageViewList();
        initDots();
        initViewPager();
        initExpandableListView();
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.debug("-->>HomeFragment", "onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.debug("-->>HomeFragment", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity.return_flag = false;

        LogUtil.debug("-->>HomeFragment", "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity) getActivity()).setControllBarVisible(true);
        LogUtil.debug("-->>HomeFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.debug("-->>HomeFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.debug("-->>HomeFragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.debug("-->>HomeFragment", "onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.debug("-->>HomeFragment", "onHiddenChanged:" + hidden);
    }

    /**
     * 初始化显示购买的三个item
     */
    private void initLimitGridView() {
        fakeDao = new FakeDao();
        listOfHomeLimitPurchaseGood = fakeDao.getHomeLimitPurchaseList();
//        WrapHeightGridView gv_limit_home = (WrapHeightGridView) view.findViewById(R.id.gv_limit_home);
        WrapHeightGridView gv_limit_home = new WrapHeightGridView(getContext());
//        GridView gv_limit_home = (GridView) view.findViewById(R.id.gv_limit_home);

        LinearLayout ll_gv_limit = (LinearLayout) view.findViewById(R.id.ll_gv_limit);

        GridViewAdapter adapter = new GridViewAdapter(this, listOfHomeLimitPurchaseGood);
        gv_limit_home.setPadding(5, 5, 5, 5);
        gv_limit_home.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gv_limit_home.setNumColumns(3);
        gv_limit_home.setAdapter(adapter);

        setGridViewHeightBasedOnChildren(gv_limit_home);

        ll_gv_limit.addView(gv_limit_home);
    }

    /**
     * 设置GridView的高度基于子项自适应
     *
     * @param gv_limit_home
     */
    private void setGridViewHeightBasedOnChildren(WrapHeightGridView gv_limit_home) {
        ListAdapter listAdapter = gv_limit_home.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 3;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, gv_limit_home);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        // 设置高度
        params.height = totalHeight;
        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        gv_limit_home.setLayoutParams(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.index_top_logo, R.id.index_search_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_top_logo:
                if (!SpUtil.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new
                            LoginFragment(), "LoginFragment").addToBackStack("LoginFragment").commit();
                } else {
                    FragmentController controller = FragmentController.getInstance(activity, R.id.fl_content);
                    controller.showFragment(4);
                }
                break;
            case R.id.index_search_button:
                break;
        }
    }

    /**
     * 初始化ExpandableListView
     */
    private void initExpandableListView() {
        eListView = (WrapHeightExpandableListView) view.findViewById(R.id.elv_home);

        listOfHomeGoodGroup = fakeDao.getHomeGoodLists();
        ExpandableAdapter adapter = new ExpandableAdapter(this, listOfHomeGoodGroup);
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

    /**
     * 设置ExpandableListView的高度基于子项高度自适应,
     *
     * @param expandableListView
     */
    public void setListViewHeightBasedOnChildren(ExpandableListView expandableListView) {
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

    /**
     * 初始化轮播图片底下显示位置的小圆点
     */
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

    /**
     * 初始化轮播广告图片URL地址列表
     */
    private void initImageUrls() {
        listOfImageUrl = new ArrayList<>();
        for (String url : imageUrls) {
            listOfImageUrl.add(url);
        }
    }

    /**
     * 初始化轮播图片列表
     */
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

    /**
     * 初始化轮播图片ViewPager控件
     */
    private void initViewPager() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(listOfImageView, listOfImageUrl));
        viewPager.addOnPageChangeListener(new PageChangeListener());
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
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * 图片轮播状态该表的监听器
     */
    private class PageChangeListener implements ViewPager.OnPageChangeListener {
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
