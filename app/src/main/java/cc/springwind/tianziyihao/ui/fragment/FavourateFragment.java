package cc.springwind.tianziyihao.ui.fragment;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.dao.FavourateDao;
import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.ui.acitivity.GoodDetailActivity;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;

/**
 * Created by HeFan on 2016/7/31.
 */
public class FavourateFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.lv_favourate_ff)
    ListView lvFavourateFf;
    private FavourateDao dao;
    private String username;
    private List<GoodsDao.GoodSimpleInfo> mList;
    private FavourateAdapter adapter;
    private InnerContentObserver observer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View view = View.inflate(getContext(), R.layout.fragment_favourate, null);
        ButterKnife.inject(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        dao = FavourateDao.getInstance(getContext());
        username = SpUtil.getString(getContext(), Constants.CURRENT_USER, "");
        mList = dao.findAll(username);
        adapter = new FavourateAdapter();
        adapter.setDeleteOnClickListener(this);
        observer = new InnerContentObserver(new Handler());
        activity.getContentResolver().registerContentObserver(Uri.parse("content://favourate/change"), true, observer);
        lvFavourateFf.setAdapter(adapter);
        lvFavourateFf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, GoodDetailActivity.class);
                intent.putExtra("id", mList.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setControllBarVisible(true);
        LogUtil.log(activity.TAG, this, "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        activity.getContentResolver().unregisterContentObserver(observer);
    }

    @Override
    @OnClick(R.id.ib_image_back)
    public void onClick(View view) {
        Object tag = view.getTag();
        switch (view.getId()) {
            case R.id.ib_image_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_delect_if:
                if (tag != null && tag instanceof Integer) {
                    delete((Integer) tag);
                }
                break;
        }
    }

    private void delete(Integer position) {
        GoodsDao.GoodSimpleInfo info = mList.get(position);
        dao.delete(username, info.id);
    }

    class FavourateAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public GoodsDao.GoodSimpleInfo getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_favourate, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            GoodsDao.GoodSimpleInfo item = getItem(position);
            ImageLoader.getInstance().displayImage(item.url, viewHolder.ivFavourateIf);
            viewHolder.tvNameIf.setText(item.name);
            viewHolder.tvPriceIf.setText(item.price + "");
            viewHolder.tvSaleCountIf.setText(item.saleCount + "");
            viewHolder.btnDelectIf.setTag(position);
            viewHolder.btnDelectIf.setOnClickListener(deleteOnClickListener);
            return convertView;
        }

        View.OnClickListener deleteOnClickListener;

        public void setDeleteOnClickListener(View.OnClickListener onClickListener) {
            this.deleteOnClickListener = onClickListener;
        }

        class ViewHolder {
            @InjectView(R.id.iv_favourate_if)
            ImageView ivFavourateIf;
            @InjectView(R.id.tv_is_limit_if)
            TextView tvIsLimitIf;
            @InjectView(R.id.tv_name_if)
            TextView tvNameIf;
            @InjectView(R.id.tv_price_if)
            TextView tvPriceIf;
            @InjectView(R.id.tv_sale_count_if)
            TextView tvSaleCountIf;
            @InjectView(R.id.btn_delect_if)
            TextView btnDelectIf;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    class InnerContentObserver extends ContentObserver {
        public InnerContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            mList = dao.findAll(username);
            adapter.notifyDataSetChanged();
        }
    }
}
