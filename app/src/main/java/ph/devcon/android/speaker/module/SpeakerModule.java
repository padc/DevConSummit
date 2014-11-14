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

package ph.devcon.android.speaker.module;

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
import ph.devcon.android.category.db.CategoryDao;
import ph.devcon.android.category.module.CategoryModule;
import ph.devcon.android.speaker.SpeakerDetailsActivity;
import ph.devcon.android.speaker.SpeakerDetailsFragment;
import ph.devcon.android.speaker.db.SpeakerDao;
import ph.devcon.android.speaker.db.TalkDao;
import ph.devcon.android.speaker.fragment.AllSpeakerFragment;
import ph.devcon.android.speaker.fragment.PanelOnlyFragment;
import ph.devcon.android.speaker.fragment.SpeakerOnlyFragment;
import ph.devcon.android.speaker.job.FetchSpeakerJob;
import ph.devcon.android.speaker.service.SpeakerService;
import ph.devcon.android.speaker.service.SpeakerServiceImpl;

/**
 * Created by lope on 10/29/14.
 */
@Module(injects = {AllSpeakerFragment.class, SpeakerOnlyFragment.class,
        PanelOnlyFragment.class, FetchSpeakerJob.class, SpeakerDetailsActivity.class,
        SpeakerDetailsFragment.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class,
                CategoryModule.class})
public class SpeakerModule {
    Context mContext;

    public SpeakerModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public SpeakerDao provideSpeakerDao() {
        return DatabaseHelper.getInstance(mContext).getSpeakerDao();
    }

    @Provides
    @Singleton
    public TalkDao provideTalkDao() {
        return DatabaseHelper.getInstance(mContext).getTalkDao();
    }

    @Provides
    @Singleton
    public SpeakerService provideSpeakerService(JobManager jobManager, EventBus eventBus, SpeakerDao speakerDao, TalkDao talkDao, CategoryDao categoryDao) {
        return new SpeakerServiceImpl(mContext, jobManager, eventBus, speakerDao, talkDao, categoryDao);
    }
}