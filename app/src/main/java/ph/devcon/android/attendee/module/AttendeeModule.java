package ph.devcon.android.attendee.module;

import android.content.Context;

import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
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
        AttendeeDetailsFragment.class},
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
