package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;

/**
 * Created by HeFan on 2016/7/7.
 */
public class AcitivityFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        activity.return_flag = false;
        return View.inflate(activity, R.layout.fragment_activity, null);
    }
}
