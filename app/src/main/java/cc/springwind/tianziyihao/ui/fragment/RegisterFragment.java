package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import cc.springwind.tianziyihao.utils.TextCheckUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by HeFan on 2016/7/20.
 * <p/>
 * 注册界面
 */
public class RegisterFragment extends BaseFragment {
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.et_sms_number)
    EditText etSmsNumber;
    @InjectView(R.id.btn_get_sms_number)
    Button btnGetSmsNumber;
    @InjectView(R.id.btn_register)
    Button btnRegister;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ALREADY_REGISTER:
                    ToastUtil.showToast(getContext(), "已注册,无需重新注册");
                    break;
                case SEND_SUCCESS:
                    ToastUtil.showToast(getContext(), "发送成功,60秒后重新发送");
                    break;
                case DO_REGISTER:
                    register(phone);
                    break;
            }
        }
    };

    private static final int ALREADY_REGISTER = 1001;
    private static final int SEND_SUCCESS = 1002;
    private static final int DO_REGISTER = 1003;
    private String phone;

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    LogUtil.debug(activity.TAG, "EVENT_SUBMIT_VERIFICATION_CODE");
                    HashMap<String, Object> map = (HashMap<String, Object>) data;
                    phone = (String) map.get("phone");
                    mHandler.sendEmptyMessage(DO_REGISTER);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Boolean flag = (Boolean) data;
                    if (flag) {
                        LogUtil.debug(activity.TAG, "EVENT_GET_VERIFICATION_CODE");
                        mHandler.sendEmptyMessage(ALREADY_REGISTER);
                    } else {
                        mHandler.sendEmptyMessage(SEND_SUCCESS);
                    }

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    private void register(String phone) {
        String passwordMD5 = MD5.hexdigest(etPassword.getText().toString().trim());
        UserInfoDao userInfoDao = UserInfoDao.getInstance(getContext());
        long l = userInfoDao.insert(phone, passwordMD5);
        if (l != -1) {
            ToastUtil.showToast(getContext(), "注册成功");
            SpUtil.putBoolean(getContext(), Constants.IS_LOGIN, true);
            SpUtil.putString(getContext(), Constants.CURRENT_USER, phone);
            MainActivity mainActivity = (MainActivity) getActivity();
            FragmentManager manager = mainActivity.getSupportFragmentManager();
            // POP_BACK_STACK_INCLUSIVE 该标志位表示在后退栈里的,所有在这个找到的Fragment后面入栈的Fragment,包括这个Fragment,都会被弹出
            // TODO: 2016/7/21 注册成功直接返回最后一次被点击的页面
            manager.popBackStack("LoginFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mainActivity.setRgTabClick(0);
            mainActivity.setRgTabClick(mainActivity.array[1]);
            LogUtil.debug(activity.TAG, "test");
        }
    }

    private String phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.registerEventHandler(eh);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_register, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        SMSSDK.unregisterAllEventHandler();
    }

    @OnClick({R.id.btn_get_sms_number, R.id.btn_register, R.id.tv_get_support_contries})
    public void onClick(View view) {
        phoneNumber = etUsername.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get_sms_number:
                if (UserInfoDao.getInstance(getContext()).queryId(phoneNumber) != 0) {
                    ToastUtil.showToast(getContext(), "手机号已注册!");
                    break;
                }
                if (TextCheckUtil.isPhoneNumber(phoneNumber)) {
                    SMSSDK.getVerificationCode("86", phoneNumber);
                    LogUtil.log(activity.TAG, this, "btn_get_sms_number");
                } else {
                    ToastUtil.showToast(getContext(), "手机号码不正确");
                }
                break;
            case R.id.btn_register:
                String password = etPassword.getText().toString().trim();
                if (!TextCheckUtil.isPassword(password)) {
                    ToastUtil.showToast(getContext(), "密码格式不正确");
                    break;
                }
                if (TextUtils.isEmpty(etSmsNumber.getText().toString().trim()) || !TextUtils.isDigitsOnly(etSmsNumber
                        .getText().toString().trim()) || etSmsNumber.getText().toString().trim().length() != 4) {
                    ToastUtil.showToast(getContext(), "验证码格式错误");
                    break;
                }
                register(phoneNumber);
                break;
            case R.id.tv_get_support_contries:
                SMSSDK.getSupportedCountries();
                break;
        }
    }

}
