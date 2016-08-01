package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    private String username;
    private static final int ALREADY_REGISTER = 1001;
    private static final int SEND_SUCCESS = 1002;
    private static final int CHANGE_PASSWORD = 1003;
    private static final int CHECK_FAILED = 1004;
    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    LogUtil.debug(activity.TAG, "EVENT_SUBMIT_VERIFICATION_CODE");
                    mHandler.sendEmptyMessage(CHANGE_PASSWORD);
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
                mHandler.sendEmptyMessage(CHECK_FAILED);
            }
        }
    };
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
                case CHANGE_PASSWORD:
                    changePassword();
                    break;
                case CHECK_FAILED:
                    ToastUtil.showToast(getContext(), "短信验证失败!");
                    break;
            }
        }
    };
    private UserInfoDao dao;

    private void changePassword() {
        String newPayPwd = etNewPasswordFppc.getText().toString();
        String newPayPwdAgain = etNewPasswordAgainFppc.getText().toString();
        if (newPayPwd.isEmpty()) {
            ToastUtil.showToast(getContext(), "新密码为空!");
            return;
        }
        if (newPayPwdAgain.isEmpty()) {
            ToastUtil.showToast(getContext(), "重复密码为空!");
            return;
        }
        if (!newPayPwd.equals(newPayPwdAgain)) {
            ToastUtil.showToast(getContext(), "密码不一致!");
            return;
        }
        dao.updatePayPassword(tvPhoneNumberFppc.getText().toString().trim(), MD5.hexdigest(newPayPwd));
        ToastUtil.showToast(getContext(), "修改成功!");
        this.manager.popBackStack();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.registerEventHandler(eh);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_pay_password_change, null);
        ButterKnife.inject(this, view);
        ((MainActivity) getActivity()).setControllBarVisible(false);
        username = SpUtil.getString(getContext(), Constants.CURRENT_USER, "");
        manager = getFragmentManager();
        dao = UserInfoDao.getInstance(getContext());
        tvPhoneNumberFppc.setText(username);
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
        SMSSDK.unregisterAllEventHandler();
    }

    @OnClick({R.id.ib_image_back, R.id.btn_get_sms_number_fppc, R.id.btn_confirm_change_fppc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_image_back:
                this.manager.popBackStack();
                break;
            case R.id.btn_get_sms_number_fppc:
                SMSSDK.getVerificationCode("86", username);
                break;
            case R.id.btn_confirm_change_fppc:
                String newpwd = etNewPasswordFppc.getText().toString().trim();
                String newpwdAgain = etNewPasswordAgainFppc.getText().toString().trim();
                if (!newpwd.equals(newpwdAgain)) {
                    ToastUtil.showToast(getContext(), "密码不一致!");
                    return;
                }
                SMSSDK.submitVerificationCode("86", tvPhoneNumberFppc.getText().toString().trim(),
                        etSmsNumberFppc.getText().toString().trim());
                break;
        }
    }
}
