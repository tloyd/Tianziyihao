package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;

/**
 * Created by HeFan on 2016/8/1.
 */
public class PasswordPayChangeFragment extends BaseFragment {

    @InjectView(R.id.tv_phone_number_fppc)
    TextView tvPhoneNumberFppc;
    @InjectView(R.id.et_new_password_fppc)
    EditText etNewPasswordFppc;
    @InjectView(R.id.et_new_password_again_fppc)
    EditText etNewPasswordAgainFppc;
    @InjectView(R.id.et_sms_number_fppc)
    EditText etSmsNumberFppc;
    private FragmentManager manager;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        View view = View.inflate(getContext(), R.layout.fragment_pay_password_change, null);
        ButterKnife.inject(this, view);
        return view;
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

    @OnClick({R.id.ib_image_back, R.id.btn_get_sms_number_fppc, R.id.btn_confirm_change_fppc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                this.manager.popBackStack();
                break;
            case R.id.btn_get_sms_number_fppc:

                break;
            case R.id.btn_confirm_change_fppc:
                this.manager.popBackStack();
                break;
        }
    }
}
