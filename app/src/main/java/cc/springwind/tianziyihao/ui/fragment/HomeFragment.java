package cc.springwind.tianziyihao.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.adapter.ExpandableAdapter;
import cc.springwind.tianziyihao.adapter.GridViewAdapter;
import cc.springwind.tianziyihao.adapter.ViewPagerAdapter;
import cc.springwind.tianziyihao.db.dao.FakeDao;
import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.db.dao.UserInfoDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.DateUtil;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;
import cc.springwind.tianziyihao.widget.WrapHeightExpandableListView;
import cc.springwind.tianziyihao.widget.WrapHeightGridView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by HeFan on 2016/7/7.
 * <p/>
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private ViewPager viewPager;
    private View view;
    private LinearLayout llHottestIndicator;
    private WrapHeightExpandableListView eListView;
    private List<GoodsDao.HomeGoodGroup> listOfHomeGoodGroup;
    // 设置控制播放标志位
    private GoodsDao goodsDao;
    private List<HashMap<String, String>> list;
    private FragmentTransaction ft;
    private ViewPagerThread thread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        initLimitGridView();
        initDots();
        thread = new ViewPagerThread();
        initViewPager();
        initExpandableListView();
        initView();
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
        LogUtil.log(activity.TAG, this, "onHiddenChanged:" + hidden);
        if (hidden) {
            thread.isplay = false;
        } else {
            thread.isplay = true;
        }
        LogUtil.log(activity.TAG, this, "thread.isplay:" + thread.isplay);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*LogUtil.log(activity.TAG, this, "setUserVisibleHint:" + isplay);
        if (isVisibleToUser) {
            //相于FragmentonResume
            isplay = true;
            LogUtil.log(activity.TAG, this, "setUserVisibleHint:" + isplay);
        } else {
            //相于FragmentonPause
            isplay = false;
            LogUtil.log(activity.TAG, this, "setUserVisibleHint:" + isplay);
        }*/
    }

    /**
     * 初始化轮播图片ViewPager控件
     */
    private void initViewPager() {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(list, this));
        viewPager.addOnPageChangeListener(new PageChangeListener());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
        thread.start();
    }

    private void initView() {
        final EditText editText = (EditText) view.findViewById(R.id.index_search_edit);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    LogUtil.log(activity.TAG, HomeFragment.this, "onKey");
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    String key = editText.getText().toString();
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
                                0);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("good_name", key);
                    ClassifyListFragment fragment = new ClassifyListFragment();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.fl_content, fragment).addToBackStack("ClassifyListFragment").commit();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 初始化显示购买的三个item
     */
    private void initLimitGridView() {
        goodsDao = GoodsDao.getInstance(getContext());
        List<GoodsDao.HomeLimitPurchaseGood> homeLimitPurchaseList = goodsDao.getHomeLimitPurchaseList();
        WrapHeightGridView gv_limit_home = new WrapHeightGridView(getContext());
        LinearLayout ll_gv_limit = (LinearLayout) view.findViewById(R.id.ll_gv_limit);
        GridViewAdapter adapter = new GridViewAdapter(this, homeLimitPurchaseList);
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

    @OnClick({R.id.index_top_logo, R.id.index_search_button, R.id.btn_get_score, R.id.btn_get_share, R.id
            .btn_favourate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_top_logo:
                if (!SpUtil.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                    ft.add(R.id.fl_content, new
                            LoginFragment(), "LoginFragment").addToBackStack("LoginFragment").commit();
                } else {
                    FragmentController controller = FragmentController.getInstance(activity, R.id.fl_content);
                    controller.showFragment(4);
                }
                break;
            case R.id.index_search_button://信息按钮
                ImageFragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putString("title", "信息内容");
                args.putString("content_url", "http://ww1.sinaimg.cn/mw690/94dfe97bgw1f563jhy60mj20hs1jt79q.jpg");
                fragment.setArguments(args);
                ft.add(R.id.fl_content, fragment,
                        "ImageFragment").addToBackStack("ImageFragment").commit();
                break;

            case R.id.btn_get_score://签到赚积分
                if (SpUtil.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                    getScore();
                } else {
                    ToastUtil.showToast(getContext(), "请先登录！");
                    ft.add(R.id.fl_content, new
                            LoginFragment(), "LoginFragment").addToBackStack("LoginFragment").commit();
                }
                break;

            case R.id.btn_get_share://分享有礼
                showShare();
                break;

            case R.id.btn_favourate:
                if (SpUtil.getBoolean(getContext(), Constants.IS_LOGIN, false)) {
                    ft.add(R.id.fl_content, new FavourateFragment()).addToBackStack(null).commit();
                } else {
                    ToastUtil.showToast(getContext(), "请先登录！");
                }
                break;
        }
    }

    /**
     * 调用此方法，即可打开一键分享功能进行分享
     */
    private void showShare() {
        ShareSDK.initSDK(getContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("title标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(getContext());
    }

    /**
     * 签到操作
     */
    private void getScore() {
        String date = SpUtil.getString(getContext(), Constants.SIGN_DATE, "");
        String today = DateUtil.getSimpleDate();
        if (date.equals(today)) {
            ToastUtil.showToast(getContext(), "您今天已经打过卡啦!");
        } else {
            SpUtil.putString(getContext(), Constants.SIGN_DATE, today);
            ToastUtil.showToast(getContext(), "今日签到:积分+6");
            String phone = SpUtil.getString(getContext(), Constants.CURRENT_USER, "");
            UserInfoDao dao = UserInfoDao.getInstance(getContext());
            int score = dao.queryScoreByPhoneNumber(phone);
            score += 6;
            UserInfoDao.getInstance(getContext()).updateScoreByPhoneNumber(phone, score);
        }
    }

    /**
     * 初始化ExpandableListView
     */
    private void initExpandableListView() {
        eListView = (WrapHeightExpandableListView) view.findViewById(R.id.elv_home);
        listOfHomeGoodGroup = goodsDao.getHomeGoodLists();
        ExpandableAdapter adapter = new ExpandableAdapter(this, listOfHomeGoodGroup);
        eListView.setAdapter(adapter);
        eListView.setGroupIndicator(null);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            eListView.expandGroup(i);
        }
        eListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ((MainActivity) getActivity()).setRgTabClick(2);
                SpUtil.putInt(getContext(), Constants.ITEM_CLICKED, groupPosition);
                return true;
            }
        });
        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long
                    id) {
                LogUtil.log(activity.TAG, this, "setOnChildClickListener");
                return false;
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

    private ImageView[] mBottomImages;//底部只是当前页面的小圆点

    /**
     * 初始化轮播图片底下显示位置的小圆点
     */
    private void initDots() {
        list = new FakeDao().getScrollImageUrls();

        //创建底部指示位置的导航栏
        mBottomImages = new ImageView[list.size()];
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

    class ViewPagerThread extends Thread {
        public boolean isplay = true;

        // TODO: 2016/8/1 还是不能暂停
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
            position %= mBottomImages.length;
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
