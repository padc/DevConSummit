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

package ph.devcon.android.program.module;

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
import ph.devcon.android.program.ProgramFragment;
import ph.devcon.android.program.db.ProgramDao;
import ph.devcon.android.program.job.FetchProgramListJob;
import ph.devcon.android.program.service.ProgramService;
import ph.devcon.android.program.service.ProgramServiceImpl;
import ph.devcon.android.speaker.db.SpeakerDao;
import ph.devcon.android.speaker.module.SpeakerModule;

/**
 * Created by lope on 10/29/14.
 */
@Module(injects = {ProgramFragment.class, FetchProgramListJob.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class,
                SpeakerModule.class})
public class ProgramModule {
    Context mContext;

    public ProgramModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public ProgramDao provideProgramDao() {
        return DatabaseHelper.getInstance(mContext).getProgramDao();
    }

    @Provides
    @Singleton
    public ProgramService provideProgramService(JobManager jobManager, EventBus eventBus, ProgramDao programDao, SpeakerDao speakerDao) {
        return new ProgramServiceImpl(mContext, jobManager, eventBus, programDao, speakerDao);
    }

}