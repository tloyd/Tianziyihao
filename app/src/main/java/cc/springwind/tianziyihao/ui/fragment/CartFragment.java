package cc.springwind.tianziyihao.ui.fragment;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.adapter.CartListAdapter;
import cc.springwind.tianziyihao.db.bean.CartBean;
import cc.springwind.tianziyihao.db.dao.CartDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.utils.LogUtil;

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
    private CartListAdapter cartListAdapter;
    private DecimalFormat decimalFormat;
    private float sum = 0.0f;
    private InnerContentObserver observer;

    //    private BigDecimal sum=new BigDecimal(0.0f);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_cart, null);
        ButterKnife.inject(this, view);
        activity.return_flag = false;
        decimalFormat = new DecimalFormat("0.0");

        // 注册内容观察者,监听cart表变化,一旦cart表有变化,立刻更新cartInfoList
        // TODO: 2016/7/21 这个URI是啥?
        observer = new InnerContentObserver(new Handler());
        activity.getContentResolver().registerContentObserver(Uri.parse("content://cart/change"), true, observer);

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
        cartBeanList = cartDao.findAll();
        cartListAdapter = new CartListAdapter(this, cartBeanList);
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
                                        (position)
                                        .good_price);
                                tvSum.setText(decimalFormat.format(sum) + "");
                                /*sum.add(new BigDecimal(cartInfoList.get(position).count).multiply(new BigDecimal(Float
                                        .parseFloat(cartInfoList.get(position).good_price))));
                                float v = sum.floatValue();
                                tvSum.setText(v+"");*/
                            } else {
                                sum -= (float) cartBeanList.get(position).count * Float.parseFloat(cartBeanList.get
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.log(activity.TAG, this, "onHiddenChanged");
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
                    int num = cartBeanList.get(position).count;
                    num++;
                    cartBeanList.get(position).count = num; //修改集合中商品数量
                    cartListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.pro_reduce:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    int num = cartBeanList.get(position).count;
                    num--;
                    cartBeanList.get(position).count = num; //修改集合中商品数量
                    cartListAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.tv_delete:
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    cartBeanList.remove(position);
                    cartListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

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
            cartBeanList = cartDao.findAll();
            // TODO: 2016/7/21 日志没有打印,但是数据确实更新了
            LogUtil.log(activity.TAG,this,"onChange");
        }
    }
}
