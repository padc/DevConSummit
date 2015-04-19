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

package ph.devcon.android.sponsor.module;

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
import ph.devcon.android.sponsor.SponsorFragment;
import ph.devcon.android.sponsor.db.SponsorDao;
import ph.devcon.android.sponsor.db.SponsorTypeDao;
import ph.devcon.android.sponsor.job.FetchSponsorJob;
import ph.devcon.android.sponsor.service.SponsorService;
import ph.devcon.android.sponsor.service.SponsorServiceImpl;

/**
 * Created by lope on 11/1/2014.
 */
@Module(injects = {SponsorFragment.class, FetchSponsorJob.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class},
        library= true)
public class SponsorModule {
    Context mContext;

    public SponsorModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public SponsorDao provideSponsorDao() {
        return DatabaseHelper.getInstance(mContext).getSponsorDao();
    }

    @Provides
    @Singleton
    public SponsorTypeDao provideSponsorTypeDao() {
        return DatabaseHelper.getInstance(mContext).getSponsorTypeDao();
    }

    @Provides
    @Singleton
    public SponsorService provideSponsorService(JobManager jobManager, EventBus eventBus, SponsorDao sponsorDao) {
        return new SponsorServiceImpl(mContext, jobManager, eventBus, sponsorDao);
    }
}
