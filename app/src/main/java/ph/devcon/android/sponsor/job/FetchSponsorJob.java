package ph.devcon.android.sponsor.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.program.job.Priority;
import ph.devcon.android.sponsor.api.SponsorBaseResponse;
import ph.devcon.android.sponsor.controller.SponsorController;
import ph.devcon.android.sponsor.db.Sponsor;
import ph.devcon.android.sponsor.event.FetchedSponsorListEvent;
import ph.devcon.android.sponsor.event.FetchedSponsorListFailedEvent;
import ph.devcon.android.sponsor.service.SponsorService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 11/1/2014.
 */
public class FetchSponsorJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    @Inject
    RestAdapter restAdapter;

    @Inject
    AuthService authService;

    @Inject
    SponsorService sponsorService;

    @Inject
    EventBus eventBus;

    public FetchSponsorJob() {
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
        SponsorController sponsorController = restAdapter.create(SponsorController.class);
        sponsorController.fetchSponsors(authService.getCachedToken(), new Callback<SponsorBaseResponse>() {
            @Override
            public void success(SponsorBaseResponse baseResponse, Response response) {
                List<Sponsor> sponsorDbList = sponsorService.createCacheObject(baseResponse);
                eventBus.post(new FetchedSponsorListEvent(sponsorDbList));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new FetchedSponsorListFailedEvent());
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
