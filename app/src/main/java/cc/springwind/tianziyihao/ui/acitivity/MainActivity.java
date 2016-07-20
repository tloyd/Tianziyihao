package cc.springwind.tianziyihao.ui.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.tianziyihao.R;
import cc.springwind.tianziyihao.bean.Constants;
import cc.springwind.tianziyihao.global.BaseActivity;
import cc.springwind.tianziyihao.ui.fragment.FragmentController;
import cc.springwind.tianziyihao.ui.fragment.LoginFragment;
import cc.springwind.tianziyihao.utils.LogUtil;
import cc.springwind.tianziyihao.utils.SpUtil;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private int preClick = 0;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                arrayCopy(0);
                LogUtil.debug(TAG,"last position is "+array[0]);
                controller.showFragment(0);
                break;
            case R.id.rb_activity:
                arrayCopy(1);
                LogUtil.debug(TAG,"last position is "+array[0]);
                controller.showFragment(1);
                break;
            case R.id.rb_class:
                arrayCopy(2);
                LogUtil.debug(TAG,"last position is "+array[0]);
                controller.showFragment(2);
                break;
            case R.id.rb_cart:
                arrayCopy(3);
                LogUtil.debug(TAG,"last position is "+array[0]);
                if (!SpUtil.getBoolean(getApplicationContext(), Constants.IS_LOGIN, false)) {
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new
                            LoginFragment(), "LoginFragment").addToBackStack("LoginFragment").commit();
                } else {
                    controller.showFragment(3);
                }
                break;
            case R.id.rb_me:
                arrayCopy(4);
                LogUtil.debug(TAG,"last position is "+array[0]);
                if (!SpUtil.getBoolean(getApplicationContext(), Constants.IS_LOGIN, false)) {
                    /*getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new
                            LoginFragment()).commit();*/
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new
                            LoginFragment(), "LoginFragment").addToBackStack("LoginFragment").commit();
                } else {
                    controller.showFragment(4);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (return_flag){
            setRgTabClick(array[0]);
        }
    }

    public void setRgTabClick(int position){
        rgTab.getChildAt(position).performClick();
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

    public void setControllBarVisible(boolean visible) {
        if (visible)
            rgTab.setVisibility(View.VISIBLE);
        else
            rgTab.setVisibility(View.GONE);
    }
}
