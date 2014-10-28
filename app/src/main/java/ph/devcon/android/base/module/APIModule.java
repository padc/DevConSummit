package ph.devcon.android.base.module;

import com.squareup.okhttp.OkHttpClient;

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
 * Created by lope on 10/28/14.
 */
@Module(library = true)
public class APIModule {

    @Provides
    public RestAdapter provideRestAdapter() {
        OkHttpClient ok = new OkHttpClient();
        ok.setReadTimeout(30, TimeUnit.SECONDS);
        ok.setConnectTimeout(30, TimeUnit.SECONDS);
        Executor executor = Executors.newCachedThreadPool();
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setClient(new OkClient(ok))
                .setEndpoint(DevConApplication.API_ENDPOINT)
                .setRequestInterceptor(new ApiRequestInterceptor())
                .build();
    }

}
