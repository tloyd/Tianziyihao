package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.bean.CartInfo;
import cc.springwind.tianziyihao.dao.CartDao;
import cc.springwind.tianziyihao.global.BaseFragment;

/**
 * Created by HeFan on 2016/7/7.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.lv_cart)
    ListView lvCart;
    @InjectView(R.id.rb_cart_select_all)
    CheckBox rbCartSelectAll;
    @InjectView(R.id.tv_sum)
    TextView tvSum;
    @InjectView(R.id.tv_pay)
    TextView tvPay;
    private View view;
    private CartDao cartDao;
    private List<CartInfo> cartInfoList;
    private CartListAdapter cartListAdapter;
    private float sum = 0.0f;
    protected DecimalFormat decimalFormat;

    //    private BigDecimal sum=new BigDecimal(0.0f);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_cart, null);
        ButterKnife.inject(this, view);
        decimalFormat = new DecimalFormat("0.0");
        activity.return_flag=false;
        initData();
        initUI();
        return view;
    }

    private void initUI() {
        rbCartSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cartListAdapter.setIsSelected(true);
                    cartListAdapter.notifyDataSetChanged();
                } else {
                    cartListAdapter.setIsSelected(false);
                    cartListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initData() {
        cartDao = CartDao.getInstance(getContext());
        // TODO: 2016/7/19 0019 当点击添加到购物车后，直接去看购物车是看不到数据的，一定要退出程序，再进购物车界面才能看到刚刚添加的数据
        cartInfoList = cartDao.findAll();
        cartListAdapter = new CartListAdapter();
        cartListAdapter.setAddListener(this);
        cartListAdapter.setReduceListener(this);
        cartListAdapter.setDeleteListener(this);
        cartListAdapter.setCheckListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Object tag = buttonView.getTag();
                        if (tag != null && tag instanceof Integer) {
                            int position = (Integer) tag;
                            if (isChecked) {
                                // TODO: 2016/7/19 0019 计算有误
                                sum += (float) cartInfoList.get(position).count * Float.parseFloat(cartInfoList.get
                                        (position)
                                        .good_price);
                                tvSum.setText(decimalFormat.format(sum) + "");
                                /*sum.add(new BigDecimal(cartInfoList.get(position).count).multiply(new BigDecimal(Float
                                        .parseFloat(cartInfoList.get(position).good_price))));
                                float v = sum.floatValue();
                                tvSum.setText(v+"");*/
                            } else {
                                sum -= (float) cartInfoList.get(position).count * Float.parseFloat(cartInfoList.get
                                        (position)
                                        .good_price);
                                tvSum.setText(decimalFormat.format(sum) + "");
                                /*sum.subtract(new BigDecimal(cartInfoList.get(position).count).multiply(new
                                BigDecimal(Float
                                        .parseFloat(cartInfoList.get(position).good_price))));
                                float v = sum.floatValue();
                                tvSum.setText(v+"");*/
                            }
                        }
                    }
                }

        );
        lvCart.setAdapter(cartListAdapter);
    }

    /**
     * 购物车列表项的适配器
     */
    class CartListAdapter extends BaseAdapter {

        private CartInfo item;
        private boolean isSelected = false;

        @Override
        public int getCount() {
            return cartInfoList.size();
        }

        @Override
        public CartInfo getItem(int position) {
            return cartInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_cart, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            item = getItem(position);

            ImageLoader.getInstance().displayImage(item.good_img_url, viewHolder.ivCartItem);
            viewHolder.tvCartItem.setText(item.good_name);
            viewHolder.proCount.setText(item.count + "");
            viewHolder.tvCartItemPrice.setText(item.good_price);
            viewHolder.tvCartItemCount.setText(item.count + "");
//            viewHolder.tvItemSum.setText(new BigDecimal(Float.parseFloat(item.good_price)).multiply(new BigDecimal
// (item.count)) + "");

            viewHolder.tvItemSum.setText(decimalFormat.format(Float.parseFloat("16.1") * (float) item.count) + "");

            viewHolder.proAdd.setTag(position);
            viewHolder.proReduce.setTag(position);
            viewHolder.tvDelete.setTag(position);
            viewHolder.rbCartItem.setTag(position);
            viewHolder.rbCartItem.setChecked(isSelected);

            viewHolder.tvDelete.setOnClickListener(deleteListener);
            viewHolder.proAdd.setOnClickListener(addListener);
            viewHolder.proReduce.setOnClickListener(reduceListener);
            viewHolder.rbCartItem.setOnCheckedChangeListener(checkListener);

            return convertView;
        }

        public void setDeleteListener(View.OnClickListener deleteListener) {
            this.deleteListener = deleteListener;
        }

        public void setAddListener(View.OnClickListener addListener) {
            this.addListener = addListener;
        }

        public void setReduceListener(View.OnClickListener reduceListener) {
            this.reduceListener = reduceListener;
        }

        public void setCheckListener(CompoundButton.OnCheckedChangeListener checkListener) {
            this.checkListener = checkListener;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        View.OnClickListener deleteListener;
        View.OnClickListener addListener;
        View.OnClickListener reduceListener;
        CompoundButton.OnCheckedChangeListener checkListener;


        class ViewHolder {
            @InjectView(R.id.rb_cart_item)
            CheckBox rbCartItem;
            @InjectView(R.id.iv_cart_item)
            ImageView ivCartItem;
            @InjectView(R.id.tv_cart_item)
            TextView tvCartItem;
            @InjectView(R.id.pro_add)
            Button proAdd;
            @InjectView(R.id.pro_count_good)
            TextView proCount;
            @InjectView(R.id.pro_reduce)
            Button proReduce;
            @InjectView(R.id.tv_cart_item_price)
            TextView tvCartItemPrice;
            @InjectView(R.id.tv_cart_item_count)
            TextView tvCartItemCount;
            @InjectView(R.id.tv_delete)
            TextView tvDelete;
            @InjectView(R.id.tv_item_sum)
            TextView tvItemSum;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_sum, R.id.tv_pay})
    public void onClick(View view) {
        Object tag = view.getTag();
        switch (view.getId()) {
            case R.id.tv_pay:

                break;

            case R.id.pro_add:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    //更改集合的数据
                    int num = cartInfoList.get(position).count;
                    num++;
                    cartInfoList.get(position).count = num; //修改集合中商品数量
                    //解决问题：点击某个按钮的时候，如果列表项所需的数据改变了，如何更新UI
                    cartListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.pro_reduce:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    //更改集合的数据
                    int num = cartInfoList.get(position).count;
                    num--;
                    cartInfoList.get(position).count = num; //修改集合中商品数量
                    //解决问题：点击某个按钮的时候，如果列表项所需的数据改变了，如何更新UI
                    cartListAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.tv_delete:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    //更改集合的数据
                    cartInfoList.remove(position);
                    //解决问题：点击某个按钮的时候，如果列表项所需的数据改变了，如何更新UI
                    cartListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
