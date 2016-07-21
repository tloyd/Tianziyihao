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
import cc.springwind.tianziyihao.dao.FakeDao;
import cc.springwind.tianziyihao.global.BaseFragment;

/**
 * Created by HeFan on 2016/7/7.
 *
 * 分类页面fragment
 */
public class ClassifyFragment extends BaseFragment {
    @InjectView(R.id.lv_classify_first_level)
    ListView lvClassifyFirstLevel;
    @InjectView(R.id.fl_classify_content)
    FrameLayout flClassifyContent;
    private List<FakeDao.ClassifyGroup> classifyGroupList;
    private ClassifyListAdapter adapter;
    private int mPosition;
    private FragmentTransaction ft;
    private FragmentManager manager;
//    private ClassifyContentFragment fragment;
//    private Bundle args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(activity, R.layout.fragment_class, null);
        ButterKnife.inject(this, view);
        activity.return_flag=false;

        initUI();
        return view;
    }

    private void getData() {
        FakeDao fakeDao = new FakeDao();
        classifyGroupList = fakeDao.getClassifyGroupList();
//        adapter.notifyDataSetChanged();
    }

    private void initUI() {
        adapter = new ClassifyListAdapter();
        lvClassifyFirstLevel.setAdapter(adapter);
        manager = getActivity().getSupportFragmentManager();
//        fragment = new ClassifyContentFragment();
//        args = new Bundle();
//        args.putSerializable("tag", classifyGroupList.get(0));
//        ft = manager.beginTransaction();
//        ft.add(R.id.fl_classify_content, fragment).commit();
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
        public FakeDao.ClassifyGroup getItem(int position) {
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
            textView.setPadding(5,10,5,10);
            textView.setTextColor(getResources().getColor(R.color.bg_Black));
            textView.setTextSize(20);
            return textView;
        }
    }
}
