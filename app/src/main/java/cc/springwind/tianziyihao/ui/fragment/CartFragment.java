package cc.springwind.tianziyihao.ui.fragment;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.bean.CartBean;
import cc.springwind.tianziyihao.db.dao.CartDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;

/**
 * Created by HeFan on 2016/7/7.
 * <p/>
 * 购物车界面
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
    private List<CartBean> cartBeanList;
    private ArrayList<CartBean> cartCheckList;
    private CartListAdapter cartListAdapter;
    private DecimalFormat decimalFormat;
    private float sum = 0.0f;
    private InnerContentObserver observer;
    private String phoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_cart, null);
        ButterKnife.inject(this, view);
        activity.return_flag = false;
        decimalFormat = new DecimalFormat("0.0");
        phoneNumber = SpUtil.getString(getContext(), Constants.CURRENT_USER, "");
        // 注册内容观察者,监听cart表变化,一旦cart表有变化,立刻更新cartInfoList
        // TODO: 2016/7/21 这个URI是啥?
        observer = new InnerContentObserver(new Handler());
        activity.getContentResolver().registerContentObserver(Uri.parse("content://cart/change"), true, observer);

        initData();
        initUI();
        return view;
    }

    /**
     * 初始化界面控件
     */
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
        tvPay.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        cartDao = CartDao.getInstance(getContext());
        cartCheckList = new ArrayList<>();
        cartBeanList = cartDao.findAllByPhoneNumber(phoneNumber);
        cartListAdapter = new CartListAdapter(this);
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
                                sum += (float) cartBeanList.get(position).count * Float.parseFloat(cartBeanList.get
                                        (position).good_price);
                                tvSum.setText(decimalFormat.format(sum) + "");
                                cartCheckList.add(cartBeanList.get(position));
                            } else {
                                sum -= (float) cartBeanList.get(position).count * Float.parseFloat(cartBeanList.get
                                        (position).good_price);
                                tvSum.setText(decimalFormat.format(sum) + "");
                                cartCheckList.remove(cartBeanList.get(position));
                            }
                        }
                    }
                }
        );
        lvCart.setAdapter(cartListAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.log(activity.TAG, this, "onHiddenChanged:" + hidden);
        if (hidden) {
            updateCartDB(cartBeanList);
        }
    }

    private void updateCartDB(List<CartBean> list) {
        for (CartBean info : list)
            cartDao.update(info.good_id, info.count);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        LogUtil.debug(activity.TAG, "CartFragment-->>onDestroyView");
        activity.getContentResolver().unregisterContentObserver(observer);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.tv_pay:
                LogUtil.log(activity.TAG, this, "tv_pay");
                getPay();
                break;
            case R.id.pro_add:
                LogUtil.log(activity.TAG, this, "pro_add");

                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    int position = (Integer) tag;
                    //更改集合的数据
                    int num = cartBeanList.get(position).count;
                    num++;
                    cartBeanList.get(position).count = num; //修改集合中商品数量
                    cartListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.pro_reduce:
                LogUtil.log(activity.TAG, this, "pro_reduce");

                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    int num = cartBeanList.get(position).count;
                    num--;
                    if (num == 0) {
                        cartDao.delete(cartBeanList.get(position).good_id, phoneNumber);
                        break;
                    }
                    cartBeanList.get(position).count = num; //修改集合中商品数量
                    cartListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_delete:
                LogUtil.log(activity.TAG, this, "tv_delete");

                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    cartDao.delete(cartBeanList.get(position).good_id, phoneNumber);
                }
                break;
        }
    }

    /**
     * 结算操作
     */
    private void getPay() {
        if (cartBeanList.size() <= 0) {
            ToastUtil.showToast(getContext(), "您还没有购买商品!");
            return;
        }
        if (cartCheckList.size() <= 0) {
            ToastUtil.showToast(getContext(), "请选择确认要购买的产品再结算!");
            return;
        }

        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();

        args.putSerializable("cartCheckList", cartCheckList);

        fragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment, "PayFragment")
                .addToBackStack("PayFragment").commit();
    }


    /**
     * 这个是内容观察者类,用于接收数据库改变的通知,在数据发生改变的时候,重新加载购物车的数据
     */
    class InnerContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public InnerContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {

            cartBeanList = cartDao.findAllByPhoneNumber(phoneNumber);
            cartListAdapter.notifyDataSetChanged();
            // // TODO: 2016/7/29 外部类无法用notifyDataSetChanged()方法通知数据更新
            LogUtil.log(activity.TAG, this, "onChange");
        }
    }


    /**
     * 购物车列表适配器
     */
    class CartListAdapter extends BaseAdapter {
        private CartFragment cartFragment;
        private DecimalFormat decimalFormat;
        private CartBean item;
        private boolean isSelected = false;

        public CartListAdapter(CartFragment cartFragment) {
            this.cartFragment = cartFragment;
            decimalFormat = new DecimalFormat("0.0");
        }

        @Override
        public int getCount() {
            return cartBeanList.size();
        }

        @Override
        public CartBean getItem(int position) {
            return cartBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(cartFragment.getContext(), R.layout.item_cart, null);
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

            viewHolder.tvItemSum.setText(decimalFormat.format(Float.parseFloat(item.good_price) *
                    (float) item.count) + "");

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
}
