package cc.springwind.tianziyihao.global;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class BaseActivity extends AppCompatActivity{
    public String TAG;
    protected BaseApplication application;
    protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG=this.getClass().getSimpleName();//得到当前类的简单类名
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.application= (BaseApplication) getApplication();
        sp=getSharedPreferences(CommonConstants.SP_NAME,MODE_PRIVATE);
    }

    public void intent2Activity(Class <? extends Activity> targetActivity){
        Intent intent=new Intent(this,targetActivity);
        startActivity(intent);
    }
}
