package ph.devcon.android.test.profile;

import com.path.android.jobqueue.JobManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.test.BaseApplicationTestCase;
import ph.devcon.android.test.base.DevConTestModule;
import ph.devcon.android.test.base.FakeAPITestModule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by lope on 11/23/14.
 */
public class UserProfileAPITestCase extends BaseApplicationTestCase {
    public static final String VALID_TOKEN = "QqYas3TNzFQRi6wsXfMm";

    @Inject
    ProfileService profileService;

    @Inject
    EventBus eventBus;

    public void testShouldParseAPIProperly() {
        mApplication.setGraph(ObjectGraph.create(getModules().toArray()));
        DevConApplication.injectMembers(this);
        profileService.populateFromAPI();
        final CountDownLatch signal = new CountDownLatch(1);
        Object listener = new Object() {
            public void onEventMainThread(FetchedProfileEvent event) {
                assertNotNull(event.profile);
                signal.countDown();
            }
        };
        eventBus.register(listener);
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Object> getModules() {
        List<Object> objects = super.getModules();
        objects.add(new FakeAPITokenTestModule());
        return objects;
    }

    @Module(injects = {UserProfileAPITestCase.class},
            includes = {DevConTestModule.class},
            library = true,
            complete = true,
            overrides = true)
    static class FakeAPITokenTestModule {
        @Provides
        public AuthService provideAuthService() {
            AuthService authService = mock(AuthService.class);
            try {
                when(authService.getCachedToken()).thenReturn(VALID_TOKEN);
            } catch (AuthService.TokenNotExistsException e) {
                e.printStackTrace();
            }
            try {
                assertEquals(VALID_TOKEN, authService.getCachedToken());
            } catch (AuthService.TokenNotExistsException e) {
                e.printStackTrace();
            }
            return authService;
        }
    }
}
