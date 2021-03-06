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

package ph.devcon.android.profile.service;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.google.common.base.Optional;
import com.path.android.jobqueue.JobManager;

import java.sql.SQLException;
import java.util.List;

import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.attendee.job.UpdateProfileJob;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.base.db.OrmliteListLoaderSupport;
import ph.devcon.android.base.event.NetworkUnavailableEvent;
import ph.devcon.android.profile.api.EditProfileBaseResponse;
import ph.devcon.android.profile.api.ProfileAPI;
import ph.devcon.android.profile.db.Profile;
import ph.devcon.android.profile.db.ProfileDao;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.event.FetchedProfileFailedEvent;
import ph.devcon.android.profile.job.FetchProfileJob;
import ph.devcon.android.technology.db.TechnologyDao;
import ph.devcon.android.user.api.UserAPI;
import ph.devcon.android.user.db.User;
import ph.devcon.android.user.db.UserDao;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 11/1/2014.
 */
public class ProfileServiceImpl implements ProfileService {

    ProfileDao profileDao;

    TechnologyDao technologyDao;

    UserDao userDao;

    JobManager jobManager;

    EventBus eventBus;

    Context context;

    public ProfileServiceImpl(Context context, JobManager jobManager, EventBus eventBus,
                              ProfileDao profileDao, UserDao userDao, TechnologyDao technologyDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.profileDao = profileDao;
        this.userDao = userDao;
        this.technologyDao = technologyDao;
    }

    @Override
    public synchronized Profile createCacheObject(EditProfileBaseResponse baseResponse) {
        try {
            profileDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<EditProfileBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        EditProfileBaseResponse profileBaseResponse = baseResponseOptional.get();
        ProfileAPI profileAPI = profileBaseResponse.getProfile();
        Optional<UserAPI> userAPIOptional = Optional.of(profileAPI.getUser());
        Profile profile = new Profile();
        if (userAPIOptional.isPresent()) {
            User userDb = User.toUser(userAPIOptional.get());
            profile.setUser(userDb);
            try {
                profileDao.create(profile);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return profile;
    }

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<Profile>>() {
                    @Override
                    public Loader<List<Profile>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(context, profileDao, profileDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Profile>> loader, List<Profile> data) {
                        if ((data != null) && (data.size() > 0)) {
                            eventBus.post(new FetchedProfileEvent(data.get(0)));
                        } else {
                            eventBus.post(new FetchedProfileFailedEvent("Cache was empty.."));
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Profile>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromCache(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Profile>>() {
                    @Override
                    public android.support.v4.content.Loader<List<Profile>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoaderSupport<Profile, Integer>(context, profileDao, profileDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(android.support.v4.content.Loader<List<Profile>> loader, List<Profile> data) {
                        if ((data != null) && (data.size() > 0)) {
                            eventBus.post(new FetchedProfileEvent(data.get(0)));
                        } else {
                            eventBus.post(new FetchedProfileFailedEvent("Cache was empty.."));
                        }
                    }

                    @Override
                    public void onLoaderReset(android.support.v4.content.Loader<List<Profile>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromAPI() {
        jobManager.addJobInBackground(new FetchProfileJob());
    }

    @Override
    public void updateAPI(Profile profile) {
        try {
            if (Optional.fromNullable(profile.getUser().getPrimaryTechnology()).isPresent()) {
                technologyDao.updateOrCreateUserTechnologies(profile.getUser());
            }
            userDao.update(profile.getUser());
            profileDao.update(profile);
            jobManager.addJob(new UpdateProfileJob(profile.getId()));
            if (!Util.isNetworkAvailable(DevConApplication.getInstance()))
                eventBus.post(new NetworkUnavailableEvent());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh(Profile profile) {
        try {
            profileDao.refresh(profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCacheValid() {
        try {
            return profileDao.isCacheValid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Profile getUserProfile(int id) {
        try {
            return profileDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}