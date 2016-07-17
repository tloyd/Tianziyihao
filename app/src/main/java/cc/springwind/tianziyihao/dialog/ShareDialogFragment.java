package cc.springwind.tianziyihao.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;

/**
 * Created by HeFan on 2016/7/15.
 */
public class ShareDialogFragment extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = dm.widthPixels;
//        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dialog_four_items);
        layoutParams.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(getActivity(), R.layout.dialog_share, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.btn_weibo, R.id.btn_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_weibo:
                break;
            case R.id.btn_cancle:
                dismiss();
                break;
        }
    }
}
