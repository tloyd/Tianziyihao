package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.dao.UserInfoDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.MD5;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;

/**
 * Created by HeFan on 2016/8/1.
 */
public class PasswordChangeFragment extends BaseFragment {

    @InjectView(R.id.et_old_password_fpc)
    EditText etOldPasswordFpc;
    @InjectView(R.id.et_new_password_fpc)
    EditText etNewPasswordFpc;
    @InjectView(R.id.et_new_password_again_fpc)
    EditText etNewPasswordAgainFpc;
    private FragmentManager manager;
    private FragmentTransaction ft;
    private UserInfoDao dao;
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        username = SpUtil.getString(getContext(), Constants.CURRENT_USER, "");
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        dao = UserInfoDao.getInstance(getContext());
        View view = View.inflate(getContext(), R.layout.fragment_password_change, null);
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

    @OnClick({R.id.ib_image_back, R.id.btn_confirm_change_fpc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                this.manager.popBackStack();
                break;
            case R.id.btn_confirm_change_fpc:
                changePassword();

                break;
        }
    }

    private void changePassword() {
        String oldpwd = etOldPasswordFpc.getText().toString();
        String newpwd = etNewPasswordFpc.getText().toString();
        String newpwdAgain = etNewPasswordAgainFpc.getText().toString();
        if (!dao.check(username, MD5.hexdigest(oldpwd))) {
            ToastUtil.showToast(getContext(), "旧密码错误!");
            return;
        }

        if (!newpwd.equals(newpwdAgain)) {
            ToastUtil.showToast(getContext(), "密码不一致!");
            return;
        }

        dao.update(username, MD5.hexdigest(newpwd));
        ToastUtil.showToast(getContext(), "修改密码成功");

        this.manager.popBackStack();
    }
}
