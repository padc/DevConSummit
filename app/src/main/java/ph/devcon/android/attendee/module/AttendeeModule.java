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

package ph.devcon.android.attendee.module;

import android.content.Context;

import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import ph.devcon.android.attendee.AttendeeDetailsActivity;
import ph.devcon.android.attendee.AttendeeDetailsFragment;
import ph.devcon.android.attendee.AttendeesFragment;
import ph.devcon.android.attendee.db.AttendeeDao;
import ph.devcon.android.attendee.job.FetchAttendeeListJob;
import ph.devcon.android.attendee.service.AttendeeService;
import ph.devcon.android.attendee.service.AttendeeServiceImpl;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.base.module.EventBusModule;

/**
 * Created by lope on 10/31/2014.
 */
@Module(injects = {AttendeesFragment.class, FetchAttendeeListJob.class,
        AttendeeDetailsFragment.class, AttendeeDetailsActivity.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class})
public class AttendeeModule {
    Context mContext;

    public AttendeeModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public AttendeeDao provideAttendeeDao() {
        return DatabaseHelper.getInstance(mContext).getAttendeeDao();
    }

    @Provides
    @Singleton
    public AttendeeService provideAttendeeService(JobManager jobManager, EventBus eventBus, AttendeeDao attendeeDao) {
        return new AttendeeServiceImpl(mContext, jobManager, eventBus, attendeeDao);
    }
}
