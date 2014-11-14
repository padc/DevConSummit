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

package ph.devcon.android.profile.module;

import android.content.Context;

import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import ph.devcon.android.attendee.job.UpdateProfileJob;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.base.module.EventBusModule;
import ph.devcon.android.login.LoginActivity;
import ph.devcon.android.navigation.NavigationDrawerFragment;
import ph.devcon.android.profile.EditUserProfileActivity;
import ph.devcon.android.profile.db.ProfileDao;
import ph.devcon.android.profile.job.FetchProfileJob;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.profile.service.ProfileServiceImpl;
import ph.devcon.android.settings.SettingsModule;
import ph.devcon.android.speaker.module.SpeakerModule;
import ph.devcon.android.user.db.UserDao;

/**
 * Created by lope on 11/1/2014.
 */
@Module(injects = {EditUserProfileActivity.class, FetchProfileJob.class,
        UpdateProfileJob.class, LoginActivity.class, NavigationDrawerFragment.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class,
                SpeakerModule.class, SettingsModule.class})
public class ProfileModule {
    Context mContext;

    public ProfileModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public UserDao provideUserDao() {
        return DatabaseHelper.getInstance(mContext).getUserDao();
    }

    @Provides
    @Singleton
    public ProfileDao provideProfileDao() {
        return DatabaseHelper.getInstance(mContext).getProfileDao();
    }

    @Provides
    @Singleton
    public ProfileService provideProfileService(JobManager jobManager, EventBus eventBus, ProfileDao profileDao, UserDao userDao) {
        return new ProfileServiceImpl(mContext, jobManager, eventBus, profileDao, userDao);
    }

}
