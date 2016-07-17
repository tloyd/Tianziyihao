package cc.springwind.tianziyihao.global;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by HeFan on 2016/6/7.
 */
public class BaseFragment extends Fragment {

    protected BaseActivity activity;
    protected BaseApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity= (BaseActivity) getActivity();
        application= (BaseApplication) getActivity().getApplication();
    }

}
