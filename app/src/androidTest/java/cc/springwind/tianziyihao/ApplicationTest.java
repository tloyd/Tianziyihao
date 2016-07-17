package cc.springwind.tianziyihao;

import android.app.Application;
import android.test.ApplicationTestCase;

import cc.springwind.tianziyihao.dao.FakeDao;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void test(){
        FakeDao fakeDao = new FakeDao();
        fakeDao.getClassifyGroupList();
    }
}