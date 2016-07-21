package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
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
import cc.springwind.tianziyihao.bean.CartInfo;
import cc.springwind.tianziyihao.dao.CartDao;
import cc.springwind.tianziyihao.global.BaseFragment;

/**
 * Created by HeFan on 2016/7/7.
 *
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
    private List<CartInfo> cartInfoList;
    private CartListAdapter cartListAdapter;
    private DecimalFormat decimalFormat;
    private float sum = 0.0f;

    //    private BigDecimal sum=new BigDecimal(0.0f);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_cart, null);
        ButterKnife.inject(this, view);
        activity.return_flag = false;
        decimalFormat = new DecimalFormat("0.0");
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
        cartListAdapter = new CartListAdapter(this, cartInfoList);
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
