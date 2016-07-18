package cc.springwind.tianziyihao.ui.acitivity;

import android.content.Intent;
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

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.bean.GoodDetailInfo;
import cc.springwind.tianziyihao.dao.FakeDao;
import cc.springwind.tianziyihao.dialog.ShareDialogFragment;
import cc.springwind.tianziyihao.global.BaseActivity;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1001:

                break;
        }
    }

    private void updataUI() {
        if (goodDetailInfo == null) {
            return;
        }
        if (goodDetailInfo.reveal_img_urls.size() > 0) {
            ImageLoader instance = ImageLoader.getInstance();
            ArrayList<ImageView> imageViews = new ArrayList<>(goodDetailInfo.reveal_img_urls.size());
            for (int i = 0; i < goodDetailInfo.reveal_img_urls.size(); i++) {
                ImageView imageView = new ImageView(getApplicationContext());
                instance.displayImage(goodDetailInfo.reveal_img_urls.get(i), imageView);
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
                params.setMargins(0,0,0,0);
                imageView.setLayoutParams(params);
                imageView.setPadding(0,0,0,0);
                instance.displayImage(goodDetailInfo.detail_img_urls.get(i), imageView);
                llGoodDetails.addView(imageView);
            }
        }
    }

    protected void initData() {
        fakeDao = new FakeDao();
        new Thread() {
            @Override
            public void run() {
                super.run();
                goodDetailInfo = fakeDao.queryGoodDetailWithId(id);
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
                break;
            case R.id.btn_share:
                ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
                shareDialogFragment.show(getFragmentManager(),"shareDialogFragment");
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
