package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import butterknife.ButterKnife;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.utils.LogUtil;

/**
 * Created by HeFan on 2016/7/7.
 *
 * 用户界面
 */
public class UserFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        activity.return_flag = false;

        LogUtil.debug(activity.TAG, "UserFragment");
        View view = View.inflate(activity, R.layout.fragment_user, null);
        ButterKnife.inject(this, view);
        return view;
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
