package ph.devcon.android.speaker.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.program.job.Priority;
import ph.devcon.android.speaker.api.SpeakerBaseResponse;
import ph.devcon.android.speaker.controller.SpeakerController;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.event.FetchedSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListFailedEvent;
import ph.devcon.android.speaker.service.SpeakerService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 10/29/14.
 */
public class FetchSpeakerJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    @Inject
    RestAdapter restAdapter;

    @Inject
    AuthService authService;

    @Inject
    SpeakerService speakerService;

    @Inject
    EventBus eventBus;

    public FetchSpeakerJob() {
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
        SpeakerController speakerController = restAdapter.create(SpeakerController.class);
        speakerController.fetchSpeakers(authService.getCachedToken(), new Callback<SpeakerBaseResponse>() {
            @Override
            public void success(SpeakerBaseResponse baseResponse, Response response) {
                List<Speaker> speakerDbList = speakerService.createSpeakers(baseResponse);
                eventBus.post(new FetchedSpeakerListEvent(speakerDbList));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new FetchedSpeakerListFailedEvent());
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
