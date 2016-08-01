package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;

/**
 * Created by HeFan on 2016/8/1.
 */
public class PasswordFragment extends BaseFragment {

    private FragmentManager manager;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        View view = View.inflate(getContext(), R.layout.fragment_password, null);
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

    @OnClick({R.id.ib_image_back, R.id.tbi_alter_login_pwd_fp, R.id.tbi_alter_pay_pwd_fp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                manager.popBackStack();
                break;
            case R.id.tbi_alter_login_pwd_fp:
                // 跳转到修改登录密码界面
                ft.replace(R.id.fl_content, new PasswordChangeFragment(), "PasswordChangeFragment")
                        .addToBackStack("PasswordChangeFragment").commit();
                break;
            case R.id.tbi_alter_pay_pwd_fp:
                // 跳转到修改支付密码界面
                ft.replace(R.id.fl_content, new PasswordPayChangeFragment(), "PasswordPayChangeFragment")
                        .addToBackStack("PasswordPayChangeFragment").commit();
                break;
        }
    }
}
