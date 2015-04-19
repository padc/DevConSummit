package ph.devcon.android.test.base;

import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.base.api.ApiRequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by lope on 11/16/14.
 */
@Module(includes = MockerTestModule.class,
        library = true,
        complete = true,
        overrides = true)
public class FakeAPITestModule {

    @Provides
    public RestAdapter provideRestAdapter(MockWebServer mockWebServer) {
        int SIZE_OF_CACHE = 1024;
        OkHttpClient ok = new OkHttpClient();
        ok.setReadTimeout(30, TimeUnit.SECONDS);
        ok.setConnectTimeout(30, TimeUnit.SECONDS);
        try {
            Cache responseCache = new Cache(DevConApplication.getInstance().getCacheDir(), SIZE_OF_CACHE);
            ok.setCache(responseCache);
        } catch (Exception e) {
            Log.d("OkHttp", "Unable to set http cache", e);
        }
        Executor executor = Executors.newCachedThreadPool();
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setClient(new OkClient(ok))
                .setEndpoint(mockWebServer.getUrl("/").toString())
                .setRequestInterceptor(new ApiRequestInterceptor())
                .build();
    }
}
