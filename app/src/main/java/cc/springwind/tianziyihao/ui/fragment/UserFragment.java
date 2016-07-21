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
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.bean.Constants;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.utils.SpUtil;

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
        }
    }
}
