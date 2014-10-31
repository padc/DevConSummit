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