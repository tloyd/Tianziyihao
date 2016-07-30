package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.bean.AddressBean;
import cc.springwind.tianziyihao.db.dao.AddressDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;

/**
 * Created by HeFan on 2016/7/29.
 *
 * 地址选择fragment
 */
public class AddressSelectFragment extends BaseFragment implements View.OnClickListener {
    @InjectView(R.id.lv_address)
    ListView lvAddress;
    private ArrayList<AddressBean> list;
    private AddressDao dao;
    private AddressAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View view = View.inflate(getContext(), R.layout.fragment_address_select, null);
        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        dao = AddressDao.getInstance(getContext());
        list = dao.findAll();
        adapter = new AddressAdapter();
        adapter.setSelectListener(this);
        lvAddress.setAdapter(adapter);
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

    @Override
    @OnClick({R.id.ib_image_back, R.id.btn_manage_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_manage_address:
                AddressFragment fragment = new AddressFragment();
                getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment, "AddressFragment")
                        .addToBackStack("AddressFragment").commit();
                break;
            case R.id.tv_address:
                // 当点击地址项中的选择按钮时,将该选项的数据设置给接口中的passAddress方法
                int position = (int) view.getTag();
                this.callback.passAddress(list.get(position));
                getFragmentManager().popBackStack();
                break;
        }
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * 接口Callback用于将当前选中的地址回传给调用该Fragment的对象
     */
    public interface Callback {
        void passAddress(AddressBean bean);
    }

    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public AddressBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_address, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            AddressBean item = getItem(position);

            viewHolder.tvAddressDetail.setText(item.district + item.specifiec_address);
            viewHolder.tvPhoneNumber.setText(item.receive_tel);
            viewHolder.tvReceiveUser.setText(item.receive_name);
            viewHolder.tvAddress.setText("选择");
            viewHolder.tvAddress.setTag(position);
            viewHolder.tvAddress.setOnClickListener(selectListener);
            return convertView;
        }

        View.OnClickListener selectListener;

        public void setSelectListener(View.OnClickListener selectListener) {
            this.selectListener = selectListener;
        }

        class ViewHolder {
            @InjectView(R.id.tv_receive_user)
            TextView tvReceiveUser;
            @InjectView(R.id.tv_phone_number)
            TextView tvPhoneNumber;
            @InjectView(R.id.tv_address_detail)
            TextView tvAddressDetail;
            @InjectView(R.id.tv_address)
            TextView tvAddress;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
