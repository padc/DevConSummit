package ph.devcon.android.test.auth;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import dagger.Module;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.test.BaseApplicationTestCase;
import ph.devcon.android.test.base.DevConTestModule;
import ph.devcon.android.test.base.FakeAPITestModule;
import ph.devcon.android.test.base.Mocker;

/**
 * Created by lope on 11/11/14.
 */
public class AuthTestCase extends BaseApplicationTestCase {

    @Inject
    MockWebServer mockWebServer;

    @Inject
    AuthService authService;

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mockWebServer.shutdown();
    }

    public void testShouldReturnTokenFromResponseString() {
        final CountDownLatch signal = new CountDownLatch(1);
        mockWebServer.enqueue(new MockResponse().setBody("{status_code: 200, message: " +
                "\"Successfully authenticated\", authentication_token: \"" + Mocker.TOKEN + "\"}"));
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
        try {
            RecordedRequest request1 = mockWebServer.takeRequest();
            assertEquals("/tokens", request1.getPath());
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

    @Override
    protected List<Object> getModules() {
        List<Object> objects = super.getModules();
        objects.add(new AuthFakeAPITestModule());
        return objects;
    }

    @Module(injects = {AuthTestCase.class},
            includes = {FakeAPITestModule.class, DevConTestModule.class},
            library = true,
            complete = true,
            overrides = true)
    static class AuthFakeAPITestModule {
    }

}