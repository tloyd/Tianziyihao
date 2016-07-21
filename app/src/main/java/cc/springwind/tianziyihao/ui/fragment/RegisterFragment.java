package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.TextCheckUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by HeFan on 2016/7/20.
 *
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

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    LogUtil.debug(activity.TAG, "EVENT_SUBMIT_VERIFICATION_CODE");
//                    HashMap<String, Object> map = (HashMap<String, Object>) data;

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Boolean flag = (Boolean) data;
                    if (flag) {
                        LogUtil.debug(activity.TAG, "EVENT_GET_VERIFICATION_CODE");
                        ToastUtil.showToast(getContext(), "已注册,无需重新注册");
                    } else {
                        ToastUtil.showToast(getContext(), "发送成功,60秒后重新发送");
                    }

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };
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
        switch (view.getId()) {
            case R.id.btn_get_sms_number:
                phoneNumber = etUsername.getText().toString().trim();
                if (TextCheckUtil.isPhoneNumber(phoneNumber)) {
                    SMSSDK.getVerificationCode("86", phoneNumber);
                } else {
                    ToastUtil.showToast(getContext(), "手机号码不正确");
                }
                break;
            case R.id.btn_register:
                String password = etPassword.getText().toString().trim();
                if (TextCheckUtil.isPassword(password)) {
                    SMSSDK.submitVerificationCode("86", phoneNumber, etSmsNumber.getText().toString().trim());
                } else {
                    ToastUtil.showToast(getContext(), "密码格式不正确");
                }
                break;
            case R.id.tv_get_support_contries:
                SMSSDK.getSupportedCountries();
                break;
        }
    }

}
