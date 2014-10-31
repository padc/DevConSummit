package ph.devcon.android.attendee.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import ph.devcon.android.attendee.api.AttendeeBaseResponse;
import ph.devcon.android.attendee.controlller.AttendeeController;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.attendee.event.FetchedAttendeeListEvent;
import ph.devcon.android.attendee.event.FetchedAttendeeListFailedEvent;
import ph.devcon.android.attendee.service.AttendeeService;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.program.job.Priority;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 10/27/2014.
 */
public class FetchAttendeeListJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    @Inject
    RestAdapter restAdapter;

    @Inject
    AuthService authService;

    @Inject
    AttendeeService attendeeService;

    @Inject
    EventBus eventBus;

    public FetchAttendeeListJob() {
        super(new Params(Priority.HIGH).requireNetwork());
        id = jobCounter.incrementAndGet();
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        if (id != jobCounter.get()) {
            //looks like other fetch jobs has been added after me. no reason to keep fetching
            //many times, cancel me, let the other one fetch tweets.
            return;
        }
        AttendeeController attendeeController = restAdapter.create(AttendeeController.class);
        attendeeController.fetchAttendees(authService.getCachedToken(), new Callback<AttendeeBaseResponse>() {
            @Override
            public void success(AttendeeBaseResponse baseResponse, Response response) {
                List<Attendee> attendeeDbList = attendeeService.createCacheObjects(baseResponse);
                eventBus.post(new FetchedAttendeeListEvent(attendeeDbList));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new FetchedAttendeeListFailedEvent());
            }
        });
    }

    @Override
    protected void onCancel() {
        //TODO show error notification
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        if (throwable instanceof SQLException) {
            // if database error then stop
            return false;
        }
        return true;
    }
}
