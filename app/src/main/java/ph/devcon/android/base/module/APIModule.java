/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.base.module;

import android.content.Context;
import android.util.Log;

import com.path.android.jobqueue.BaseJob;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.path.android.jobqueue.log.CustomLogger;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

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
    Context mContext;

    public APIModule(Context context) {
        mContext = context;
    }

    @Provides
    public RestAdapter provideRestAdapter() {
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
                .setEndpoint(DevConApplication.API_ENDPOINT)
                .setRequestInterceptor(new ApiRequestInterceptor())
                .build();
    }

    @Provides
    @Singleton
    public JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(mContext)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(BaseJob job) {
                        DevConApplication.injectMembers(job);
                    }
                })
                .build();
        return new JobManager(mContext, configuration);
    }
}
