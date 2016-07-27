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
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.dao.FakeDao;
import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.GoodDetailActivity;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;

/**
 * Created by HeFan on 2016/7/16.
 * <p/>
 * 点击分类后显示的分类列表页面
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
    private List<GoodsDao.GoodSimpleInfo> goodSimpleInfoList;
    private ClassifyListAdapter adapter;
    private GoodsDao dao;
    private String second_class_id;
    private String first_class_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fakeDao = new FakeDao();
        activity.return_flag = false;

        second_class_id = getArguments().getString("second_class_id");
        first_class_id = getArguments().getString("first_class_id");

        dao = GoodsDao.getInstance(getContext());

        goodSimpleInfoList = dao.getClassifyGoodList(first_class_id, second_class_id, "_id");

//        goodSimpleInfoList = fakeDao.getClassifyGoodListBySortType("default");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_classify_list, null);
        ButterKnife.inject(this, view);
        ((MainActivity) getActivity()).setControllBarVisible(false);
        initUI();

        return view;
    }

    private void initUI() {
        adapter = new ClassifyListAdapter();
        lvClassifyList.setAdapter(adapter);
        lvClassifyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GoodDetailActivity.class);
                intent.putExtra("id", goodSimpleInfoList.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setControllBarVisible(true);
        LogUtil.log(activity.TAG, this, "onDestroy");
    }

    @OnClick({R.id.tv_default, R.id.tv_new, R.id.tv_sale, R.id.tv_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_default:
                goodSimpleInfoList = dao.getClassifyGoodList(first_class_id, second_class_id, "_id");
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_new:
                goodSimpleInfoList = dao.getClassifyGoodList(first_class_id, second_class_id, "date_on_sale");
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_sale:
                goodSimpleInfoList = dao.getClassifyGoodList(first_class_id, second_class_id, "sale_count");
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_price:
                goodSimpleInfoList = dao.getClassifyGoodList(first_class_id, second_class_id, "good_price");
                adapter.notifyDataSetChanged();
                break;
        }
    }

    public class ClassifyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return goodSimpleInfoList.size();
        }

        @Override
        public GoodsDao.GoodSimpleInfo getItem(int position) {
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
            GoodsDao.GoodSimpleInfo item = getItem(position);
            ImageLoader.getInstance().displayImage(item.url, viewHolder.ivGoodSimple);
            viewHolder.tvNameGoodSimple.setText(item.name);
            viewHolder.tvPiceGoodSimple.setText(String.valueOf(item.price));
            viewHolder.tvSaleCountSimple.setText(item.saleCount + "");
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
