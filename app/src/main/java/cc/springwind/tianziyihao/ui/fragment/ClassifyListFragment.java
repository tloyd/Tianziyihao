package cc.springwind.tianziyihao.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.dao.FakeDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.GoodDetailActivity;

/**
 * Created by HeFan on 2016/7/16.
 */
public class ClassifyListFragment extends BaseFragment {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.et_search)
    EditText etSearch;
    @InjectView(R.id.iv_home)
    ImageView ivHome;
    @InjectView(R.id.tv_default)
    TextView tvDefault;
    @InjectView(R.id.tv_new)
    TextView tvNew;
    @InjectView(R.id.tv_sale)
    TextView tvSale;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.lv_classify_list)
    ListView lvClassifyList;
//    @InjectView(R.id.wrl_classify_list)
//    SwipeRefreshLayout wrlClassifyList;

    private FakeDao fakeDao;
    private List<FakeDao.GoodSimpleInfo> goodSimpleInfoList;
    private ClassifyListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fakeDao = new FakeDao();
        activity.return_flag=false;

        goodSimpleInfoList = fakeDao.getClassifyGoodListBySortType("default");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_classify_list, null);
        ButterKnife.inject(this, view);

        initUI();

        return view;
    }

    private void initUI() {
        adapter = new ClassifyListAdapter();
        lvClassifyList.setAdapter(adapter);
        lvClassifyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getContext(), GoodDetailActivity.class);
                intent.putExtra("id",goodSimpleInfoList.get(position).id);
                startActivity(intent);
            }
        });
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class ClassifyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return goodSimpleInfoList.size();
        }

        @Override
        public FakeDao.GoodSimpleInfo getItem(int position) {
            return goodSimpleInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_good_simple, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            FakeDao.GoodSimpleInfo item = getItem(position);
            ImageLoader.getInstance().displayImage(item.url, viewHolder.ivGoodSimple);
            viewHolder.tvNameGoodSimple.setText(item.name);
            viewHolder.tvPiceGoodSimple.setText(item.price);
            viewHolder.tvSaleCountSimple.setText(item.saleCount);
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.iv_good_simple)
            ImageView ivGoodSimple;
            @InjectView(R.id.tv_name_good_simple)
            TextView tvNameGoodSimple;
            @InjectView(R.id.tv_pice_good_simple)
            TextView tvPiceGoodSimple;
            @InjectView(R.id.tv_sale_count_simple)
            TextView tvSaleCountSimple;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
