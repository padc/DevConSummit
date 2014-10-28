package ph.devcon.android.program.job;

import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.program.api.ProgramAPI;
import ph.devcon.android.program.controller.ProgramController;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.db.ProgramDao;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by lope on 10/27/2014.
 */
public class FetchProgramListJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    public FetchProgramListJob() {
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
        int SIZE_OF_CACHE = 1024;
        OkHttpClient ok = new OkHttpClient();
        try {
            Cache responseCache = new Cache(DevConApplication.getInstance().getCacheDir(), SIZE_OF_CACHE);
            ok.setCache(responseCache);
        } catch (Exception e) {
            Log.d("OkHttp", "Unable to set http cache", e);
        }
        ok.setReadTimeout(30, TimeUnit.SECONDS);
        ok.setConnectTimeout(30, TimeUnit.SECONDS);
        Executor executor = Executors.newCachedThreadPool();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setClient(new OkClient(ok))
                .setEndpoint("http://api.devcon.ph/api/v1/")
                .build();
        final ProgramDao programDao = DatabaseHelper.getInstance(DevConApplication.getInstance()).getProgramDao();
        ProgramController programService = restAdapter.create(ProgramController.class);
        programService.listPrograms("test", new Callback<List<ProgramAPI>>() {
            @Override
            public void success(List<ProgramAPI> programAPIs, Response response) {
                List<ph.devcon.android.program.db.Program> programsDBList = new ArrayList<ph.devcon.android.program.db.Program>();
                for (ProgramAPI programAPIApi : programAPIs) {
                    try {
                        Program programDb = Program.toProgram(programAPIApi);
                        programDao.create(programDb);
                        programsDBList.add(programDb);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                EventBus.getDefault().post(new FetchedProgramListEvent(programsDBList));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
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

    public static class FetchedProgramListEvent {
        public List<ph.devcon.android.program.db.Program> programs;

        public FetchedProgramListEvent(List<ph.devcon.android.program.db.Program> programs) {
            this.programs = programs;
        }
    }
}
