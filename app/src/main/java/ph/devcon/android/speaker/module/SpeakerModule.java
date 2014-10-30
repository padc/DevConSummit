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
import ph.devcon.android.speaker.db.SpeakerDao;
import ph.devcon.android.speaker.fragment.SpeakerFragment;
import ph.devcon.android.speaker.job.FetchSpeakerJob;
import ph.devcon.android.speaker.service.SpeakerService;
import ph.devcon.android.speaker.service.SpeakerServiceImpl;

/**
 * Created by lope on 10/29/14.
 */
@Module(injects = {SpeakerFragment.class, FetchSpeakerJob.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class})
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
    public SpeakerService provideSpeakerService(JobManager jobManager, EventBus eventBus, SpeakerDao speakerDao) {
        return new SpeakerServiceImpl(mContext, jobManager, eventBus, speakerDao);
    }
}