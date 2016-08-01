package cc.springwind.tianziyihao.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseFragment;
import cc.springwind.tianziyihao.ui.acitivity.MainActivity;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.ScreenUtil;

/**
 * Created by HeFan on 2016/7/24 0024.
 */
public class ImageFragment extends BaseFragment {
    @InjectView(R.id.ib_image_back)
    ImageButton ibImageBack;
    @InjectView(R.id.tv_if_title)
    TextView tvIfTitle;
    @InjectView(R.id.iv_if_content)
    ImageView ivIfContent;
    private String title;
    private String content_url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setControllBarVisible(false);
        View view = View.inflate(getContext(), R.layout.fragment_image, null);
        ButterKnife.inject(this, view);
        initData();
        initUI();
        return view;
    }

    private void initUI() {
        tvIfTitle.setText(title);
        ImageLoader.getInstance().loadImage(content_url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                float scale = (float) loadedImage.getHeight() / loadedImage.getWidth();

                int screenWidthPixels = ScreenUtil.getScreenWidth(getContext());
                int screenHeightPixels = ScreenUtil.getScreenHeight(getContext());
                int height = (int) (screenWidthPixels * scale);

                if (height < screenHeightPixels) {
                    height = screenHeightPixels;
                }
                ivIfContent.getLayoutParams();
                ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
                params.height = height;
                params.width = screenWidthPixels;
                ivIfContent.setLayoutParams(params);
                ivIfContent.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        content_url = bundle.getString("content_url");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
