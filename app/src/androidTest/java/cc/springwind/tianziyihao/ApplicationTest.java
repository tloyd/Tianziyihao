package cc.springwind.tianziyihao;

import android.app.Application;
import android.test.ApplicationTestCase;

import cc.springwind.tianziyihao.utils.DateUtil;
import cc.springwind.tianziyihao.utils.LogUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void test() {
        String simpleDate = DateUtil.getSimpleDate();
        LogUtil.log("-->>", this, simpleDate);
    }
}