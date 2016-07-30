package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.utils.SpUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;

/**
 * Created by HeFan on 2016/7/7.
 * <p/>
 * 用户界面
 */
public class UserFragment extends BaseFragment {
    @InjectView(R.id.tv_user_phone)
    TextView tvUserPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        activity.return_flag = false;
        View view = View.inflate(activity, R.layout.fragment_user, null);
        ButterKnife.inject(this, view);

        initUI();

        return view;
    }

    private void initUI() {
        tvUserPhone.setText(SpUtil.getString(getContext(), Constants.CURRENT_USER, "110"));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 清除指定文件夹下的文件
     *
     * @param file
     */
    private void deleteFilesByDirectory(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
            ToastUtil.showToast(getContext(), "清除完毕!");
        }
    }

    @OnClick({R.id.tbi_clear, R.id.btn_log_out, R.id.tbi_about_us,R.id.tbi_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tbi_clear:
                deleteFilesByDirectory(getContext().getCacheDir());
                break;
            case R.id.btn_log_out:
                SpUtil.putBoolean(getContext(), Constants.IS_LOGIN, false);
                activity.getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new LoginFragment(),
                        "LoginFragment").addToBackStack("LoginFragment").commit();
                break;
            case R.id.tbi_about_us:
                ImageFragment fragment = new ImageFragment();
                Bundle args = new Bundle();
                args.putString("title", "信息内容");
                args.putString("content_url", "http://ww1.sinaimg.cn/mw690/94dfe97bgw1f563jhy60mj20hs1jt79q.jpg");
                fragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment,
                        "ImageFragment").addToBackStack("ImageFragment").commit();
                break;

            case R.id.tbi_address:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new AddressFragment(),
                        "ImageFragment").addToBackStack("ImageFragment").commit();
                break;
        }
    }
}
