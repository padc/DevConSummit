/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.program.job;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.program.api.ProgramBaseResponse;
import ph.devcon.android.program.controller.ProgramController;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.event.FetchedProgramListEvent;
import ph.devcon.android.program.event.FetchedProgramListFailedEvent;
import ph.devcon.android.program.service.ProgramService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 10/27/2014.
 */
public class FetchProgramListJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    @Inject
    RestAdapter restAdapter;

    @Inject
    AuthService authService;

    @Inject
    ProgramService programService;

    @Inject
    EventBus eventBus;

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
        ProgramController programController = restAdapter.create(ProgramController.class);
        programController.fetchPrograms(authService.getCachedToken(), new Callback<ProgramBaseResponse>() {
            @Override
            public void success(ProgramBaseResponse baseResponse, Response response) {
                List<Program> programsDBList = programService.createCacheObject(baseResponse);
                eventBus.post(new FetchedProgramListEvent(programsDBList));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                eventBus.post(new FetchedProgramListFailedEvent());
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
