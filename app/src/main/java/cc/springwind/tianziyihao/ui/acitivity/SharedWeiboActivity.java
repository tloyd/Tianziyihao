package cc.springwind.tianziyihao.ui.acitivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.Constants;
import cc.springwind.tianziyihao.global.BaseActivity;
import cc.springwind.tianziyihao.token.AccessTokenKeeper;

/**
 * Created by HeFan on 2016/7/17.
 */
public class SharedWeiboActivity extends BaseActivity {

    @InjectView(R.id.ib_share_back)
    ImageButton ibShareBack;
    @InjectView(R.id.share_title)
    TextView shareTitle;
    @InjectView(R.id.btn_get_share)
    Button btnGetShare;
    @InjectView(R.id.et_share)
    EditText etShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_weibo);
        ButterKnife.inject(this);

        String content = getIntent().getStringExtra("content");
        etShare.setText(content);
    }

    @OnClick({R.id.ib_share_back, R.id.btn_get_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_share_back:
                finish();
                break;
            case R.id.btn_get_share:
                share2Weibo();
                finish();
                break;
        }
    }

    private StatusesAPI statusesAPI;

    private void share2Weibo() {
        statusesAPI = new StatusesAPI(this, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(this));
        statusesAPI.update(etShare.getText().toString(), "0.0", "0.0", new RequestListener() {
            @Override
            public void onComplete(String s) {
                Toast.makeText(SharedWeiboActivity.this, "分享成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                Toast.makeText(SharedWeiboActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
