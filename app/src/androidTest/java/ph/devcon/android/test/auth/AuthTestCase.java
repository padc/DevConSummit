package ph.devcon.android.test.auth;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import dagger.ObjectGraph;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.test.BaseApplicationTestCase;
import ph.devcon.android.test.base.APITestModule;

/**
 * Created by lope on 11/11/14.
 */
public class AuthTestCase extends BaseApplicationTestCase {

    @Inject
    MockWebServer mockWebServer;

    @Inject
    AuthService authService;

    public void testShouldReturnTokenFromResponseString() {
        // use fake api
        mApplication.setGraph(ObjectGraph.create(getFakeAPIModule().toArray()));
        DevConApplication.injectMembers(this);
        final CountDownLatch signal = new CountDownLatch(1);
        mockWebServer.enqueue(new MockResponse().setBody("{status_code: 200, message: " +
                "“Successfully authenticated”, authentication_token: “" + Mocker.TOKEN + "“}"));
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

    public void testShouldCacheTokenResponse() {
        testShouldReturnTokenFromResponseString();
        try {
            assertEquals(authService.getCachedToken(), Mocker.TOKEN);
        } catch (AuthService.TokenNotExistsException e) {
            e.printStackTrace();
            assertFalse();
        }
    }

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

    public List<Object> getFakeAPIModule() {
        List<Object> objects = super.getModules();
        objects.add(new APITestModule());
        return objects;
    }

}