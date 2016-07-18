package cc.springwind.tianziyihao.ui.acitivity;

import android.os.Bundle;
import android.widget.RadioGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.global.BaseActivity;
import cc.springwind.tianziyihao.ui.fragment.FragmentController;
import cc.springwind.tianziyihao.utils.LogUtil;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @InjectView(R.id.rg_tab)
    RadioGroup rgTab;
    private FragmentController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        controller = FragmentController.getInstance(this, R.id.fl_content);
        controller.showFragment(0);

        rgTab.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                LogUtil.debug(TAG, "rb_home");
                controller.showFragment(0);
                break;
            case R.id.rb_activity:
                LogUtil.debug(TAG, "rb_activity");
                controller.showFragment(1);
                break;
            case R.id.rb_class:
                LogUtil.debug(TAG, "rb_class");
                controller.showFragment(2);
                break;
            case R.id.rb_cart:
                LogUtil.debug(TAG, "rb_cart");
                controller.showFragment(3);
                break;
            case R.id.rb_me:
                LogUtil.debug(TAG, "rb_me");
                controller.showFragment(4);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragmentController.onDestroy();
    }
}
