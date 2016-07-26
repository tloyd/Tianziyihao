package cc.springwind.tianziyihao;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.List;

import cc.springwind.tianziyihao.db.dao.GoodsDao;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void test() {
        GoodsDao goodsDao = GoodsDao.getInstance(getContext());
        List<GoodsDao.HomeLimitPurchaseGood> homeLimitPurchaseList = goodsDao.getHomeLimitPurchaseList();
    }
}