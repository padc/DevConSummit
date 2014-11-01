package ph.devcon.android.profile.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.profile.api.EditProfileBaseResponse;
import ph.devcon.android.profile.controller.ProfileController;
import ph.devcon.android.profile.db.Profile;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.event.FetchedProfileFailedEvent;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.program.job.Priority;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 11/1/2014.
 */
public class FetchProfileJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    @Inject
    RestAdapter restAdapter;

    @Inject
    AuthService authService;

    @Inject
    ProfileService programService;

    @Inject
    EventBus eventBus;

    public FetchProfileJob() {
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
        ProfileController programController = restAdapter.create(ProfileController.class);
        programController.fetchProfile(authService.getCachedToken(), new Callback<EditProfileBaseResponse>() {
            @Override
            public void success(EditProfileBaseResponse baseResponse, Response response) {
                Profile programsDB = programService.createCacheObject(baseResponse);
                eventBus.post(new FetchedProfileEvent(programsDB));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new FetchedProfileFailedEvent());
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
