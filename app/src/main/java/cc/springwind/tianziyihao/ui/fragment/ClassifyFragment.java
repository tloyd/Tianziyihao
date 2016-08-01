package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;

/**
 * Created by HeFan on 2016/7/7.
 * <p/>
 * 分类页面fragment
 */
public class ClassifyFragment extends BaseFragment {
    @InjectView(R.id.lv_classify_first_level)
    ListView lvClassifyFirstLevel;
    @InjectView(R.id.fl_classify_content)
    FrameLayout flClassifyContent;
    private List<GoodsDao.ClassifyGroup> classifyGroupList;
    private ClassifyListAdapter adapter;
    private int mPosition;
    private FragmentTransaction ft;
    private FragmentManager manager;
    private GoodsDao dao;
    //    private ClassifyContentFragment fragment;
//    private Bundle args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(activity, R.layout.fragment_class, null);
        ButterKnife.inject(this, view);
        activity.return_flag = false;
        LogUtil.log(activity.TAG, this, "onCreateView");
        getData();
        initUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log(activity.TAG, this, "onResume count:" + lvClassifyFirstLevel.getChildCount() + "");
    }

    private void getData() {
        dao = GoodsDao.getInstance(getContext());
        classifyGroupList = dao.getClassifyGroupList();
    }

    private void initUI() {
        adapter = new ClassifyListAdapter();
        lvClassifyFirstLevel.setAdapter(adapter);
        manager = getActivity().getSupportFragmentManager();
        ClassifyContentFragment fragment = new ClassifyContentFragment();
        Bundle args = new Bundle();
        args.putSerializable("tag", classifyGroupList.get(0));
        fragment.setArguments(args);
        ft = manager.beginTransaction();
        ft.replace(R.id.fl_classify_content, fragment).commit();

        lvClassifyFirstLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                adapter.notifyDataSetChanged();
                ClassifyContentFragment fragment = new ClassifyContentFragment();
                Bundle args = new Bundle();
                args.putSerializable("tag", classifyGroupList.get(position));
                fragment.setArguments(args);
                ft = manager.beginTransaction();
                ft.replace(R.id.fl_classify_content, fragment).commit();
            }
        });
        LogUtil.log(activity.TAG, this, "create count:" + lvClassifyFirstLevel.getChildCount() + "");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
//        LogUtil.log(activity.TAG, this, "onHiddenChanged:" + hidden);

        if (hidden) {
            return;
        }

        int position = SpUtil.getInt(getContext(), Constants.ITEM_CLICKED, -1);
        if (position != -1) {
            adapter.notifyDataSetChanged();
            LogUtil.log(activity.TAG, this, "count:" + lvClassifyFirstLevel.getChildCount() + "");
            mPosition = position;
            // TODO: 2016/8/1 得到子项并且点击该项
            lvClassifyFirstLevel.setItemChecked(mPosition, true);
            SpUtil.putInt(getContext(), Constants.ITEM_CLICKED, -1);
        }/**/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private class ClassifyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return classifyGroupList.size();
        }

        @Override
        public GoodsDao.ClassifyGroup getItem(int position) {
            return classifyGroupList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(getContext());
            } else {
                textView = (TextView) convertView;
            }

            if (mPosition == position) {
                textView.setBackgroundColor(getResources().getColor(R.color.bg_White));
            } else {
                textView.setBackgroundColor(getResources().getColor(R.color.bg_Gray_light));
            }
            textView.setText(getItem(position).name);
            textView.setPadding(5, 10, 5, 10);
            textView.setTextColor(getResources().getColor(R.color.bg_Black));
            textView.setTextSize(20);
            return textView;
        }
    }
}
