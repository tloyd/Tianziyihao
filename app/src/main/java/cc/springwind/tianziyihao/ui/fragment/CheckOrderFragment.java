package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.bean.CartBean;
import cc.springwind.tianziyihao.db.bean.Order;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.widget.AddressItem;

/**
 * Created by HeFan on 2016/8/2.
 */
public class CheckOrderFragment extends BaseFragment {

    @InjectView(R.id.ai_order_fco)
    AddressItem aiOrderFco;
    @InjectView(R.id.lv_order_list_fco)
    ListView lvOrderListFco;
    @InjectView(R.id.tv_order_fco)
    TextView tvOrderFco;
    private Order order;
    private ArrayList<CartBean> orderList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View view = View.inflate(getContext(), R.layout.fragment_check_order, null);
        initData();
        ButterKnife.inject(this, view);
        return view;
    }

    private void initData() {
        order = (Order) getArguments().getSerializable("order");
        orderList = (ArrayList<CartBean>) getArguments().getSerializable("cartCheckList");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setControllBarVisible(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ib_image_back, R.id.btn_submit_order_fco})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                break;
            case R.id.btn_submit_order_fco:
                break;
        }
    }
}
