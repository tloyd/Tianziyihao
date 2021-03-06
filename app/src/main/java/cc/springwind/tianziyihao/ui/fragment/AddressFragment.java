package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

/**
 * Created by HeFan on 2016/7/29.
 */
public class AddressFragment extends BaseFragment {
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
        View view = View.inflate(getContext(), R.layout.fragment_address, null);
        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    private void initData() {
        dao = AddressDao.getInstance(getContext());
        list = dao.findAll();
        adapter = new AddressAdapter();
        lvAddress.setAdapter(adapter);
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressEditFragment fragment = new AddressEditFragment();
                Bundle args = new Bundle();
                args.putSerializable("AddressBean", list.get(position));
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment, "AddressEditFragment")
                        .addToBackStack("AddressEditFragment").commit();
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
    }

    @OnClick({R.id.ib_image_back, R.id.btn_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_add_address:
                AddressEditFragment fragment = new AddressEditFragment();
                getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment, "AddressEditFragment")
                        .addToBackStack("AddressEditFragment").commit();
                break;
        }
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

            return convertView;
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
