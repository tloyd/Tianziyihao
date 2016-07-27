package cc.springwind.tianziyihao.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.dao.UserInfoDao;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.MD5;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by HeFan on 2016/7/20.
 * <p/>
 * 登录界面
 */
public class LoginFragment extends BaseFragment {
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.btn_log_in)
    Button btnLogIn;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View inflate = View.inflate(getContext(), R.layout.fragment_login, null);
        ButterKnife.inject(this, inflate);
        activity.return_flag = true;
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.debug("-->>LoginFragment", "onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.debug("-->>LoginFragment", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.debug("-->>LoginFragment", "onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.debug("-->>LoginFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.debug("-->>LoginFragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setControllBarVisible(true);
        LogUtil.debug("-->>LoginFragment", "onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.debug("-->>LoginFragment", "onHiddenChanged:" + hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.debug("-->>LoginFragment", "onResume:");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        LogUtil.debug("-->>onDestroyView", "onResume:");
    }


    @OnClick({R.id.btn_log_in, R.id.tv_register, R.id.tv_forget_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_log_in:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                UserInfoDao userInfoDao = UserInfoDao.getInstance(activity);
                boolean check = userInfoDao.check(username, MD5.hexdigest(password));
                if (check) {
                    SpUtil.putBoolean(getContext(), Constants.IS_LOGIN, true);
                    SpUtil.getString(getContext(), Constants.CURRENT_USER, username);
                    getFragmentManager().popBackStack();
                    ((MainActivity) getActivity()).setRgTabClick(activity.array[1]);
                } else {
                    ToastUtil.showToast(getContext(), "密码或帐号不正确,请重新输入");
                }
                break;
            case R.id.tv_register:
                //register();
                FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
                supportFragmentManager.beginTransaction().replace(R.id.fl_content, new RegisterFragment(),
                        "RegisterFragment").addToBackStack("RegisterFragment").commit();
                break;
            case R.id.tv_forget_pwd:
                break;
        }
    }

    private void register() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息
                    registerUser(country, phone);
                }
            }
        });
        registerPage.show(getContext());
    }

    private void registerUser(String country, String phone) {

    }


}
