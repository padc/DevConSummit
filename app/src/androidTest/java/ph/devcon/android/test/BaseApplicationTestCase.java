package ph.devcon.android.test;

import android.test.ApplicationTestCase;

import java.util.List;

import ph.devcon.android.DevConApplication;
import ph.devcon.android.test.base.DevConTestModule;

/**
 * Created by lope on 10/30/14.
 */
public abstract class BaseApplicationTestCase extends ApplicationTestCase {
    protected DevConApplication mApplication;

    public BaseApplicationTestCase() {
        super(DevConApplication.class);
    }

    public static void assertFalse() {
        assertEquals(false, true);
    }

    public static void assertTrue() {
        assertEquals(true, true);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        createApplication();
        mApplication = (DevConApplication) getApplication();
        mApplication.setGraph(mApplication.getGraph().plus(getModules().toArray()));
        DevConApplication.injectMembers(this);
    }

    protected List<Object> getModules() {
        List<Object> appModules = mApplication.getModules();
        appModules.add(new DevConTestModule());
        return appModules;
    }
}
