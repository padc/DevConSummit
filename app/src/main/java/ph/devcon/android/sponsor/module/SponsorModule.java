package ph.devcon.android.sponsor.module;

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
import ph.devcon.android.sponsor.SponsorFragment;
import ph.devcon.android.sponsor.db.SponsorDao;
import ph.devcon.android.sponsor.db.SponsorTypeDao;
import ph.devcon.android.sponsor.job.FetchSponsorJob;
import ph.devcon.android.sponsor.service.SponsorService;
import ph.devcon.android.sponsor.service.SponsorServiceImpl;

/**
 * Created by lope on 11/1/2014.
 */
@Module(injects = {SponsorFragment.class, FetchSponsorJob.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class})
public class SponsorModule {
    Context mContext;

    public SponsorModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public SponsorDao provideSponsorDao() {
        return DatabaseHelper.getInstance(mContext).getSponsorDao();
    }

    @Provides
    @Singleton
    public SponsorTypeDao provideSponsorTypeDao() {
        return DatabaseHelper.getInstance(mContext).getSponsorTypeDao();
    }

    @Provides
    @Singleton
    public SponsorService provideSponsorService(JobManager jobManager, EventBus eventBus, SponsorDao sponsorDao) {
        return new SponsorServiceImpl(mContext, jobManager, eventBus, sponsorDao);
    }
}
