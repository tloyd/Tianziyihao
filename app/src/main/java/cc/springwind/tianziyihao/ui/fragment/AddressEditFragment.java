package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
 * Created by HeFan on 2016/7/30.
 */
public class AddressEditFragment extends BaseFragment {

    @InjectView(R.id.et_district)
    EditText etDistrict;
    @InjectView(R.id.et_specified)
    EditText etSpecified;
    @InjectView(R.id.et_contact_people)
    EditText etContactPeople;
    @InjectView(R.id.et_contact_tel)
    EditText etContactTel;
    @InjectView(R.id.et_district_code)
    EditText etDistrictCode;
    private AddressBean bean;
    private AddressDao dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View view = View.inflate(getContext(), R.layout.fragment_address_edit, null);
        ButterKnife.inject(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        dao = AddressDao.getInstance(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (AddressBean) bundle.getSerializable("AddressBean");
            etContactPeople.setText(bean.receive_name);
            etContactTel.setText(bean.receive_tel);
            etSpecified.setText(bean.specifiec_address);
            etDistrict.setText(bean.district);
            etDistrictCode.setText(bean.district_code);
        }
        etDistrict.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                @Override
                                                public void onFocusChange(View v, boolean hasFocus) {

                                                }
                                            }

        );
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

    @OnClick({R.id.ib_image_back, R.id.btn_set_default_address, R.id.btn_delect_address, R.id.btn_save_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_set_default_address:
                dao.setDefault(bean.address_id);
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_delect_address:
                dao.delete(bean.address_id);
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_save_address:
                saveAddress();
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void saveAddress() {
        if (bean != null) {
            bean.district = etDistrict.getText().toString();
            bean.specifiec_address = etSpecified.getText().toString();
            bean.district_code = etDistrictCode.getText().toString();
            bean.receive_name = etContactPeople.getText().toString();
            bean.receive_tel = etContactTel.getText().toString();

            dao.update(bean);

            return;
        }
        AddressBean bean1 = new AddressBean();
        bean1.district = etDistrict.getText().toString();
        bean1.specifiec_address = etSpecified.getText().toString();
        bean1.district_code = etDistrictCode.getText().toString();
        bean1.receive_name = etContactPeople.getText().toString();
        bean1.receive_tel = etContactTel.getText().toString();
        dao.insert(bean1);
    }
}
