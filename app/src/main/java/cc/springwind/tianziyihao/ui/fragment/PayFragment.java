package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.bean.AddressBean;
import cc.springwind.tianziyihao.db.bean.CartBean;
import cc.springwind.tianziyihao.db.bean.Order;
import cc.springwind.tianziyihao.db.dao.AddressDao;
import cc.springwind.tianziyihao.db.dao.CouponDao;
import cc.springwind.tianziyihao.db.dao.OrderDao;
import cc.springwind.tianziyihao.db.dao.UserInfoDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;
import cc.springwind.tianziyihao.widget.AddressItem;

/**
 * Created by HeFan on 2016/7/29.
 */
public class PayFragment extends BaseFragment implements View.OnClickListener {
    private static final int SELF = 0;
    private static final int ALIPAY = 1;
    private static final int WECHAT = 2;
    @InjectView(R.id.spinner_coupon_fp)
    Spinner spinnerCouponFp;
    private AddressItem aiPay;
    @InjectView(R.id.lv_pay)
    ListView lvPay;
    @InjectView(R.id.spinner_hope_receive_time)
    Spinner spinnerHopeReceiveTime;
    @InjectView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @InjectView(R.id.tv_discount)
    TextView tvDiscount;
    @InjectView(R.id.tv_dilivery_money)
    TextView tvDiliveryMoney;
    @InjectView(R.id.tv_note)
    TextView tvNote;
    @InjectView(R.id.rg_pay_way)
    RadioGroup rgPayWay;
    @InjectView(R.id.et_buyer_request)
    EditText etBuyerRequest;
    @InjectView(R.id.tv_pay_count)
    TextView tvPayCount;
    @InjectView(R.id.tv_pay_money)
    TextView tvPayMoney;
    private ArrayList<CartBean> cartCheckList;
    private AddressBean addressBean;
    private PayAdapter adapter;
    private DecimalFormat decimalFormat;
    private float sum = 0;
    private int count = 0;
    private int payway = -1;
    private float account;
    private View view;
    private String username;
    private List<CouponDao.Coupon> couponList;
    private CouponDao dao;
    private CouponAdapter couponAdapter;
    private CouponDao.Coupon coupon;
    private FragmentTransaction ft;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        LogUtil.log(activity.TAG, this, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        view = View.inflate(getContext(), R.layout.fragment_pay, null);
        ButterKnife.inject(this, view);
        ft = getFragmentManager().beginTransaction();
        initUI();
        return view;
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        aiPay = (AddressItem) view.findViewById(R.id.ai_pay);
        if (addressBean != null) {
            aiPay.setItem(addressBean);
        }
        adapter = new PayAdapter();
        adapter.setAddListener(this);
        adapter.setReduceListener(this);
        lvPay.setAdapter(adapter);
        for (CartBean b :
                cartCheckList) {
            sum += b.count * Float.parseFloat(b.good_price);
            count += b.count;
        }

        setCaculateText(count, sum);
        setListViewHeightBasedOnChildren(lvPay);
        rgPayWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rb_self:
                        payway = SELF;
                        break;
                    case R.id.rb_alipay:
                        payway = ALIPAY;
                        break;
                    case R.id.rb_wechat:
                        payway = WECHAT;
                        break;
                }
            }
        });
        final ArrayList<String> list = new ArrayList<>();
        list.add("8:00~11:00(尽快送达)");
        list.add("11:00~14:00");
        list.add("14:00~18:00");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout
                .simple_list_item_1, list);
        spinnerHopeReceiveTime.setAdapter(adapter);
        spinnerHopeReceiveTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.log(activity.TAG, this, "list.get(position):" + list.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        username = SpUtil.getString(getContext(), Constants.CURRENT_USER, "");
        dao = CouponDao.getInstance(getContext());
        couponList = dao.findAll(username);
        couponAdapter = new CouponAdapter();
        spinnerCouponFp.setAdapter(couponAdapter);
        spinnerCouponFp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coupon = couponList.get(position);
                if (Float.parseFloat(tvPayMoney.getText().toString()) >= coupon.couponQuelify) {
                    tvDiscount.setText(coupon.couponValue + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 设置需要计算的控件数据
     *
     * @param count
     * @param sum
     */
    private void setCaculateText(int count, float sum) {
        if (sum >= 38)
            tvDiliveryMoney.setText("0.0");
        else
            tvDiliveryMoney.setText("3.0");
        tvGoodsPrice.setText(decimalFormat.format(sum) + "");
        float discount = Float.parseFloat(tvDiscount.getText().toString());
        float dilivery = Float.parseFloat(tvDiliveryMoney.getText().toString());
        tvPayMoney.setText(decimalFormat.format(sum + dilivery - discount) + "");
        tvPayCount.setText(count + "");
    }

    /**
     * 初始化数据
     */
    private void initData() {
        decimalFormat = new DecimalFormat();
        cartCheckList = (ArrayList<CartBean>) getArguments().getSerializable("cartCheckList");
        addressBean = AddressDao.getInstance(getContext()).findDefault();
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
    }

    /**
     * 设置listView的高度基于子项高度自适应,
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
//            int desiredWidth= View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    @OnClick({R.id.tv_submit_order, R.id.ib_image_back, R.id.ai_pay})
    public void onClick(View view) {
        Object tag = view.getTag();
        switch (view.getId()) {
            case R.id.ib_image_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.ai_pay:
                // 点击地址条目时候的操作
                getAddress();
                break;
            case R.id.tv_submit_order:
                // 点击提交订单的时候操作
                submitOrder();
                break;
            case R.id.btn_add:
                // 点击商品项中的增加按钮
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    int num = cartCheckList.get(position).count;
                    cartCheckList.get(position).count = ++num; //修改集合中商品数量
                    sum += Float.parseFloat(cartCheckList.get(position).good_price);
                    setCaculateText(++count, sum);
                    adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(lvPay);
                }
                break;
            case R.id.btn_reduce:
                // 点击商品项中的减少按钮
                if (tag != null && tag instanceof Integer) {
                    int position = (Integer) tag;
                    int num = cartCheckList.get(position).count;
                    cartCheckList.get(position).count = --num; //修改集合中商品数量
                    sum -= Float.parseFloat(cartCheckList.get(position).good_price);
                    setCaculateText(--count, sum);
                    adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(lvPay);
                }
                break;
        }
    }

    /**
     * 获取地址信息
     */
    private void getAddress() {
        LogUtil.log(activity.TAG, this, "getAddress");
        AddressSelectFragment fragment = new AddressSelectFragment();
        fragment.setCallback(new AddressSelectFragment.Callback() {
            @Override
            public void passAddress(AddressBean bean) {
                addressBean = bean;

            }
        });
        getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).addToBackStack(null).commit();
    }

    /**
     * 提交订单操作
     */
    private void submitOrder() {
        if (addressBean == null) {
            ToastUtil.showToast(getContext(), "请填写地址哦!");
            return;
        }

        if (payway == -1) {
            ToastUtil.showToast(getContext(), "请选择支付方式!");
            return;
        }
        /**
         * 假如所有条件都符合订单提交的情况, 那么要组合订单所需要的数据,提交给服务器/数据库
         *
         * 地址信息
         * 会员注册唯一判断标识,本应用为电话号码(username)
         * 自动生成订单id
         * 订单状态(待付款,已付款代发货,已发货待签收,已签收待评价)
         * 总价格
         *
         * */

        Order order = new Order();
        order.receive_address = addressBean.district + " " + addressBean.specifiec_address;
        order.receive_district_code = addressBean.district_code;
        order.receive_name = addressBean.receive_name;
        order.receive_tel = addressBean.receive_tel;
        order.sum_price = Float.parseFloat(tvPayMoney.getText().toString());
        order.username = username;
        order.order_flag=0;

        /**
         "order_id integer primary key autoincrement," +
         "username text," +
         "sum_price real," +
         "receive_district_code text," +
         "receive_name text," +
         "receive_tel text," +
         "receive_address text," +
         "order_flag integer"
         * */

        if (payway == SELF) {
            account = UserInfoDao.getInstance(getContext()).queryAccountByUsername(username);
            if (account <= sum) {
                ToastUtil.showToast(getContext(), "账户余额不足,请先充值!");
                return;
            }
            OrderDao orderDao = OrderDao.getInstance(getContext());
            orderDao.insert(order);
            CheckOrderFragment fragment = new CheckOrderFragment();
            Bundle args = new Bundle();
            args.putSerializable("order", order);
            args.putSerializable("orderList", cartCheckList);
            fragment.setArguments(args);
            ft.replace(R.id.fl_content, fragment);
        }
        if (payway == ALIPAY) {
            return;
        }
        if (payway == WECHAT) {
            return;
        }
    }


    /**
     * 商品项的适配器
     */
    class PayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cartCheckList.size();
        }

        @Override
        public CartBean getItem(int position) {
            return cartCheckList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_pay, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CartBean item = getItem(position);
            viewHolder.tvNameItemPay.setText(item.good_name);
            viewHolder.tvPriceItemPay.setText(item.good_price);
            viewHolder.tvCountItemPay.setText(item.count + "");
            viewHolder.tvCountGood.setText(item.count + "");

            ImageLoader.getInstance().displayImage(item.good_img_url, viewHolder.ivItemPay);

            viewHolder.btnAdd.setOnClickListener(addListener);
            viewHolder.btnReduce.setOnClickListener(reduceListener);
            viewHolder.btnAdd.setTag(position);
            viewHolder.btnReduce.setTag(position);

            return convertView;
        }

        View.OnClickListener addListener;
        View.OnClickListener reduceListener;

        public void setAddListener(View.OnClickListener addListener) {
            this.addListener = addListener;
        }

        public void setReduceListener(View.OnClickListener reduceListener) {
            this.reduceListener = reduceListener;
        }

        class ViewHolder {
            @InjectView(R.id.iv_item_pay)
            ImageView ivItemPay;
            @InjectView(R.id.tv_name_item_pay)
            TextView tvNameItemPay;
            @InjectView(R.id.tv_price_item_pay)
            TextView tvPriceItemPay;
            @InjectView(R.id.tv_count_item_pay)
            TextView tvCountItemPay;
            @InjectView(R.id.btn_add)
            Button btnAdd;
            @InjectView(R.id.tv_count_good)
            TextView tvCountGood;
            @InjectView(R.id.btn_reduce)
            Button btnReduce;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    class CouponAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return couponList.size();
        }

        @Override
        public CouponDao.Coupon getItem(int position) {
            return couponList.get(position);
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
            textView.setText(getItem(position).couponName);
            return textView;
        }
    }
}
