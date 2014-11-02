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
        PanelOnlyFragment.class, FetchSpeakerJob.class},
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