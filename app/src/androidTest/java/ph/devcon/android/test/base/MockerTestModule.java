package ph.devcon.android.test.base;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lope on 11/16/14.
 */
@Module(library = true,
        complete = true,
        overrides = true)
public class MockerTestModule {

    @Provides
    @Singleton
    public MockWebServer provideMockWebServer() {
        MockWebServer mockWebServer = new MockWebServer();
        try {
            mockWebServer.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mockWebServer;
    }
}
