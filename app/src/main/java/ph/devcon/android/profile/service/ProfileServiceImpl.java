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
import ph.devcon.android.attendee.job.UpdateProfileJob;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.profile.api.EditProfileBaseResponse;
import ph.devcon.android.profile.api.ProfileAPI;
import ph.devcon.android.profile.db.Profile;
import ph.devcon.android.profile.db.ProfileDao;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.event.FetchedProfileFailedEvent;
import ph.devcon.android.profile.job.FetchProfileJob;
import ph.devcon.android.user.api.UserAPI;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 11/1/2014.
 */
public class ProfileServiceImpl implements ProfileService {

    ProfileDao profileDao;

    JobManager jobManager;

    EventBus eventBus;

    Context context;

    public ProfileServiceImpl(Context context, JobManager jobManager, EventBus eventBus, ProfileDao profileDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.profileDao = profileDao;
    }

    @Override
    public Profile createCacheObject(EditProfileBaseResponse baseResponse) {
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
                            eventBus.post(new FetchedProfileFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Profile>> loader) {
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
            profileDao.update(profile);
            jobManager.addJobInBackground(new UpdateProfileJob(profile.getUser().getId()));
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