package ph.devcon.android.profile.module;

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
import ph.devcon.android.profile.EditUserProfileActivity;
import ph.devcon.android.profile.db.ProfileDao;
import ph.devcon.android.profile.job.FetchProfileJob;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.profile.service.ProfileServiceImpl;
import ph.devcon.android.speaker.module.SpeakerModule;

/**
 * Created by lope on 11/1/2014.
 */
@Module(injects = {EditUserProfileActivity.class, FetchProfileJob.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class,
                SpeakerModule.class})
public class ProfileModule {
    Context mContext;

    public ProfileModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public ProfileDao provideProfileDao() {
        return DatabaseHelper.getInstance(mContext).getProfileDao();
    }

    @Provides
    @Singleton
    public ProfileService provideProfileService(JobManager jobManager, EventBus eventBus, ProfileDao profileDao) {
        return new ProfileServiceImpl(mContext, jobManager, eventBus, profileDao);
    }

}
