package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.widget.WrapHeightGridView;

/**
 * Created by HeFan on 2016/7/16.
 * <p/>
 * 二级分类fragment， 作用于点击分类一级条目后显示在右边的fragment
 */
public class ClassifyContentFragment extends BaseFragment {

    @InjectView(R.id.gv_classify_content)
    WrapHeightGridView gvClassifyContent;
    private GoodsDao.ClassifyGroup classifyGroup;
    private List<GoodsDao.SecondLevelGroup> groupList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity.return_flag = false;

        classifyGroup = (GoodsDao.ClassifyGroup) getArguments().getSerializable("tag");
        groupList = classifyGroup.groupList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_classify_content, null);
        ButterKnife.inject(this, view);

        initUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initUI() {
        gvClassifyContent.setAdapter(new ClasssifyContentAdapter());
        gvClassifyContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("second_class_id", groupList.get(position).second_class_id);
                bundle.putString("first_class_id", String.valueOf(groupList.get(position).first_class_id));
                ClassifyListFragment fragment = new ClassifyListFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.fl_content, fragment).addToBackStack("ClassifyListFragment").commit();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class ClasssifyContentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return groupList.size();
        }

        @Override
        public GoodsDao.SecondLevelGroup getItem(int position) {
            return groupList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_classify, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(getItem(position).url, viewHolder.ivClassify);
            viewHolder.tvClassify.setText(getItem(position).name);
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.iv_classify)
            ImageView ivClassify;
            @InjectView(R.id.tv_classify)
            TextView tvClassify;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
