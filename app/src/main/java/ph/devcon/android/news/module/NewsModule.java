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

package ph.devcon.android.news.module;

import android.content.Context;

import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.base.module.EventBusModule;
import ph.devcon.android.news.NewsDetailsActivity;
import ph.devcon.android.news.NewsDetailsFragment;
import ph.devcon.android.news.NewsFragment;
import ph.devcon.android.news.db.NewsDao;
import ph.devcon.android.news.job.FetchNewsJob;
import ph.devcon.android.news.service.NewsService;
import ph.devcon.android.news.service.NewsServiceImpl;

/**
 * Created by lope on 10/29/14.
 */
@Module(injects = {NewsFragment.class, FetchNewsJob.class, NewsDetailsFragment.class,
        NewsDetailsActivity.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class})
public class NewsModule {
    Context mContext;

    public NewsModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public NewsDao provideNewsDao() {
        return DatabaseHelper.getInstance(mContext).getNewsDao();
    }

    @Provides
    @Singleton
    public NewsService provideNewsService(JobManager jobManager, EventBus eventBus, NewsDao newsDao) {
        return new NewsServiceImpl(mContext, jobManager, eventBus, newsDao);
    }
}