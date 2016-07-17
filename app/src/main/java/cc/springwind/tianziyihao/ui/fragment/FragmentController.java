package cc.springwind.tianziyihao.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import cc.springwind.tianziyihao.global.BaseFragment;

/**
 * Created by HeFan on 2016/6/7.
 */
public class FragmentController {
    private static int containerId;
    private FragmentManager manager;
    private ArrayList<BaseFragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int id){
        containerId=id;

        if (controller==null){
            controller=new FragmentController(activity,id);
        }
        return controller;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        manager = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AcitivityFragment());
        fragments.add(new ClassifyFragment());
        fragments.add(new CartFragment());
        fragments.add(new MeFragment());

        FragmentTransaction ft = manager.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = getFragment(position);
        FragmentTransaction ft = manager.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = manager.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    public static void onDestroy() {
        controller = null;
    }
}
