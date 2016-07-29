package cc.springwind.tianziyihao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;

/**
 * Created by HeFan on 2016/7/28.
 */
public class HelpFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View view = View.inflate(getContext(), R.layout.fragment_help, null);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setControllBarVisible(true);
        LogUtil.log(activity.TAG, this, "onDestroy");
    }

    @OnClick(R.id.ib_image_back)
    public void onClick() {
        getFragmentManager().popBackStack();
    }
}
