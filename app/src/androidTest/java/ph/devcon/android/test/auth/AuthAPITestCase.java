package ph.devcon.android.test.auth;

import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import dagger.ObjectGraph;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.test.BaseApplicationTestCase;
import ph.devcon.android.test.base.Mocker;

/**
 * Created by lope on 11/11/14.
 */
public class AuthAPITestCase extends BaseApplicationTestCase {

    @Inject
    AuthService authService;

    public void testShouldAuthenticateValidAccount() {
        // use real api
        mApplication.setGraph(ObjectGraph.create(getModules().toArray()));
        DevConApplication.injectMembers(this);
        final CountDownLatch signal = new CountDownLatch(1);
        authService.authenticate(Mocker.USERNAME_VALID, Mocker.PASSWORD_VALID,
                new AuthService.AuthCallback() {
                    @Override
                    public void onAuthenticated(String token) {
                        signal.countDown();
                        assertEquals(Mocker.TOKEN, token);
                    }

                    @Override
                    public void onAuthenticationFailed(Integer statusCode, String message) {
                        signal.countDown();
                        assertFalse();
                    }
                });
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testShouldErrorInvalidAccount() {
        // use real api
        mApplication.setGraph(ObjectGraph.create(getModules().toArray()));
        DevConApplication.injectMembers(this);
        final CountDownLatch signal = new CountDownLatch(1);
        authService.authenticate(Mocker.USERNAME_VALID, Mocker.PASSWORD_INVALID,
                new AuthService.AuthCallback() {
                    @Override
                    public void onAuthenticated(String token) {
                        signal.countDown();
                    }

                    @Override
                    public void onAuthenticationFailed(Integer statusCode, String message) {
                        signal.countDown();
                        assertTrue();
                    }
                });
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}