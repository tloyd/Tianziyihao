package cc.springwind.tianziyihao.ui.acitivity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.db.bean.CartBean;
import cc.springwind.tianziyihao.db.dao.CartDao;
import cc.springwind.tianziyihao.db.dao.FakeDao;
import cc.springwind.tianziyihao.db.dao.GoodsDao;
import cc.springwind.tianziyihao.dialog.ShareDialogFragment;
import cc.springwind.tianziyihao.entity.GoodDetailInfo;
import cc.springwind.tianziyihao.global.BaseActivity;
import cc.springwind.tianziyihao.ui.fragment.FragmentController;
import cc.springwind.tianziyihao.utils.ScreenUtil;
import cc.springwind.tianziyihao.utils.ToastUtil;

/**
 * Created by HeFan on 2016/7/14.
 */
public class GoodDetailActivity extends BaseActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.ib_cart)
    ImageButton ibCart;
    @InjectView(R.id.vp_detail_scroll)
    ViewPager vpDetailScroll;
    @InjectView(R.id.ll_good_desc)
    LinearLayout llGoodDesc;
    @InjectView(R.id.btn_share)
    Button btnShare;
    @InjectView(R.id.ll_good_params)
    LinearLayout llGoodParams;
    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;
    @InjectView(R.id.tv_comment)
    TextView tvComment;
    @InjectView(R.id.ll_good_details)
    LinearLayout llGoodDetails;
    @InjectView(R.id.btn_home)
    Button btnHome;
    @InjectView(R.id.btn_like)
    Button btnLike;
    @InjectView(R.id.btn_service)
    Button btnService;
    @InjectView(R.id.tv_add_to_cart)
    TextView tvAddToCart;
    @InjectView(R.id.tv_buy_now)
    TextView tvBuyNow;
    private String id;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updataUI();
        }
    };
    private GoodDetailInfo goodDetailInfo;
    private FakeDao fakeDao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.inject(this);
        id = getIntent().getStringExtra("id");
        updataUI();
        initData();

    }

    private void updataUI() {
        if (goodDetailInfo == null) {
            return;
        }
        if (goodDetailInfo.reveal_img_urls.size() > 0) {
            ImageLoader instance = ImageLoader.getInstance();
            ArrayList<ImageView> imageViews = new ArrayList<>(goodDetailInfo.reveal_img_urls.size());
            for (int i = 0; i < goodDetailInfo.reveal_img_urls.size(); i++) {
                final ImageView imageView = new ImageView(getApplicationContext());
//                instance.displayImage(goodDetailInfo.reveal_img_urls.get(i), imageView);
                instance.loadImage(goodDetailInfo.reveal_img_urls.get(i), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        float scale = (float) loadedImage.getHeight() / loadedImage.getWidth();

                        int screenWidthPixels = ScreenUtil.getScreenWidth(getApplicationContext());
                        int screenHeightPixels = ScreenUtil.getScreenHeight(getApplicationContext());
                        int height = (int) (screenWidthPixels * scale);

                        if (height < screenHeightPixels) {
                            height = screenHeightPixels;
                        }

                        ViewGroup.LayoutParams params = new ViewPager.LayoutParams();
                        params.height = height;
                        params.width = screenWidthPixels;
                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
                imageViews.add(imageView);
            }
            vpDetailScroll.setAdapter(new RevealImageAdapter(imageViews));
            vpDetailScroll.setCurrentItem(0);
        }

        if (goodDetailInfo.good_name != null) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(goodDetailInfo.good_name);
            textView.setTextSize(18);
            textView.setTextColor(getResources().getColor(R.color.tv_Black));
            llGoodDesc.addView(textView);
        }

        if (goodDetailInfo.good_description != null) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(goodDetailInfo.good_description);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.tv_Red));
            llGoodDesc.addView(textView);
        }

        if (goodDetailInfo.price != null) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(goodDetailInfo.price);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.tv_Red));
            llGoodDesc.addView(textView);
        }

        if (goodDetailInfo.price_original != null) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(goodDetailInfo.price_original);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.tv_dark_Gray));
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            llGoodDesc.addView(textView);
        }

        if (goodDetailInfo.notes != null) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(goodDetailInfo.notes);
            textView.setTextSize(14);
            textView.setTextColor(getResources().getColor(R.color.tv_Red));
//            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            textView.getPaint().setFakeBoldText(true);
            llGoodDesc.addView(textView);
        }

        if (goodDetailInfo.comments != null) {
            tvComment.setText(goodDetailInfo.comments);
        }

        if (goodDetailInfo.good_params.size() > 0) {
            for (int i = 0; i < goodDetailInfo.good_params.size(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(goodDetailInfo.good_params.get(i));
                llGoodParams.addView(textView);
            }
        }

        if (goodDetailInfo.detail_img_urls.size() > 0) {
            ImageLoader instance = ImageLoader.getInstance();
            for (int i = 0; i < goodDetailInfo.detail_img_urls.size(); i++) {
                ImageView imageView = new ImageView(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);
                imageView.setLayoutParams(params);
                imageView.setPadding(0, 0, 0, 0);
                instance.displayImage(goodDetailInfo.detail_img_urls.get(i), imageView);
                llGoodDetails.addView(imageView);
            }
        }
    }

    protected void initData() {
        // TODO: 2016/7/26 需要被替换的数据源
        fakeDao = new FakeDao();
        final GoodsDao dao = GoodsDao.getInstance(this);
        new Thread() {
            @Override
            public void run() {
                super.run();
//                goodDetailInfo = fakeDao.queryGoodDetailWithId(id);
                goodDetailInfo = dao.queryGoodDetailById(id);
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.ib_back, R.id.ib_cart, R.id.btn_share, R.id.btn_home, R.id.btn_like, R.id.btn_service, R.id
            .tv_add_to_cart, R.id.tv_buy_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_cart:
                FragmentController controller = FragmentController.getInstance(this, R.id.fl_content);
                controller.showFragment(0);
                finish();
                break;
            case R.id.btn_share:
                ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
                shareDialogFragment.show(getFragmentManager(), "shareDialogFragment");
                break;
            case R.id.btn_home:
                intent2Activity(MainActivity.class);
                finish();
                break;
            case R.id.btn_like:
                break;
            case R.id.btn_service:
                break;
            case R.id.tv_add_to_cart:
                add2Cart();
                break;
            case R.id.tv_buy_now:
                break;
        }
    }

    private void add2Cart() {
        CartDao instance = CartDao.getInstance(getApplicationContext());
        List<CartBean> all = instance.findAll();
        for (CartBean cartBean :
                all) {
            if (id.equals(cartBean.good_id)) {
                instance.update(id, ++cartBean.count);
                ToastUtil.showToast(getApplicationContext(), "添加到購物車:" + cartBean.count++ + "件");
                return;
            }
        }
        CartBean info = new CartBean();
        info.count = 1;
        info.good_id = id;
        info.good_name = goodDetailInfo.good_name;
        info.good_price = goodDetailInfo.price;
        info.good_img_url = goodDetailInfo.thumbnail_img_url;
        instance.insert(info);
        ToastUtil.showToast(getApplicationContext(), "添加到購物車:1件");
    }

    private class RevealImageAdapter extends PagerAdapter {
        ArrayList<ImageView> imageViews;

        public RevealImageAdapter(ArrayList<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            return this.imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(this.imageViews.get(position));
            return this.imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(this.imageViews.get(position));
        }
    }
}
