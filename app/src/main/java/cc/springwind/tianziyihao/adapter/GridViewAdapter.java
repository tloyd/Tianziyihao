package cc.springwind.tianziyihao.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.ui.acitivity.GoodDetailActivity;
import cc.springwind.tianziyihao.ui.fragment.HomeFragment;
import cc.springwind.tianziyihao.widget.GoodListItem;

/**
 * 显示限时购买的三个item的GridView的适配器
 */
public class GridViewAdapter extends BaseAdapter {
    private HomeFragment homeFragment;
    List<GoodsDao.HomeLimitPurchaseGood> listOfHomeLimitPurchaseGood;

    public GridViewAdapter(HomeFragment homeFragment, List<GoodsDao.HomeLimitPurchaseGood>
            listOfHomeLimitPurchaseGood) {
        this.homeFragment = homeFragment;
        if (listOfHomeLimitPurchaseGood.size() <= 3) {
            this.listOfHomeLimitPurchaseGood = listOfHomeLimitPurchaseGood;
        } else {
            this.listOfHomeLimitPurchaseGood = listOfHomeLimitPurchaseGood.subList(0, 3);
        }
    }

    @Override
    public int getCount() {
        return listOfHomeLimitPurchaseGood.size();
    }

    @Override
    public GoodsDao.HomeLimitPurchaseGood getItem(int position) {
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
            goodListItem = new GoodListItem(homeFragment.getContext());
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
                Intent intent = new Intent(homeFragment.getContext(), GoodDetailActivity.class);
                intent.putExtra("id", getItem(position).id);
                homeFragment.getActivity().startActivity(intent);
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
