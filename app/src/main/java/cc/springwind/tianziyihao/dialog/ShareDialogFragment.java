package cc.springwind.tianziyihao.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.token.AccessTokenKeeper;
import cc.springwind.tianziyihao.ui.acitivity.LoginActivity;
import cc.springwind.tianziyihao.ui.acitivity.SharedWeiboActivity;

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

    private static final int WHAT_INTENT2LOGIN = 1;
    private static final int WHAT_INTENT2MAIN = 2;
    private Oauth2AccessToken accessToken;
    private StatusesAPI statusesAPI;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_INTENT2MAIN) {

                Intent intent = new Intent(getActivity(), SharedWeiboActivity.class);
                intent.putExtra("content", "肥猪肉,20块/斤,分享至微博");
                getActivity().startActivityForResult(intent, 1001);
                dismiss();

            }
            if (msg.what == WHAT_INTENT2LOGIN) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                dismiss();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @OnClick({R.id.btn_weibo, R.id.btn_cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_weibo:
                accessToken = AccessTokenKeeper.readAccessToken(getActivity());
                if (accessToken.isSessionValid()) {
                    mHandler.sendEmptyMessage(WHAT_INTENT2MAIN);
                } else {
                    mHandler.sendEmptyMessage(WHAT_INTENT2LOGIN);
                }
                break;
            case R.id.btn_cancle:
                dismiss();
                break;
        }
    }
}
