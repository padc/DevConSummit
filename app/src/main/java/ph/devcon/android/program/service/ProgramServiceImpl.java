package ph.devcon.android.program.service;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.google.common.base.Optional;
import com.j256.ormlite.dao.ForeignCollection;
import com.path.android.jobqueue.JobManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.program.api.ProgramAPI;
import ph.devcon.android.program.api.ProgramAPIContainer;
import ph.devcon.android.program.api.ProgramBaseResponse;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.db.ProgramDao;
import ph.devcon.android.program.event.FetchedProgramListEvent;
import ph.devcon.android.program.event.FetchedProgramListFailedEvent;
import ph.devcon.android.program.job.FetchProgramListJob;
import ph.devcon.android.speaker.api.SpeakerAPI;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.db.SpeakerDao;

/**
 * Created by lope on 10/29/14.
 */
public class ProgramServiceImpl implements ProgramService {

    ProgramDao programDao;

    SpeakerDao speakerDao;

    JobManager jobManager;

    EventBus eventBus;

    Context context;

    public ProgramServiceImpl(Context context, JobManager jobManager, EventBus eventBus, ProgramDao programDao, SpeakerDao speakerDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.programDao = programDao;
        this.speakerDao = speakerDao;
    }

    public synchronized List<Program> createCacheObject(ProgramBaseResponse baseResponse) {
        try {
            programDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<ProgramBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        ProgramBaseResponse programBaseResponse = baseResponseOptional.get();
        List<Program> programsDBList = new ArrayList<Program>();
        for (ProgramAPIContainer container : programBaseResponse.getPrograms()) {
            try {
                ProgramAPI programAPI = container.getProgram();
                ForeignCollection<Speaker> speakers = programDao.getEmptyForeignCollection("speakers");
                Program programDb = Program.toProgram(programAPI);
                programDb.setSpeakers(speakers);
                programDao.create(programDb);
                for (SpeakerAPI speakerAPI : programAPI.getSpeakers()) {
                    Speaker speaker = Speaker.toSpeaker(speakerAPI);
                    speaker.setProgram(programDb);
                    speakers.add(speaker);
                }
                programsDBList.add(programDb);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return programsDBList;
    }

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<Program>>() {
                    @Override
                    public Loader<List<Program>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(context, programDao, programDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Program>> loader, List<Program> data) {
                        if (data != null) {
                            eventBus.post(new FetchedProgramListEvent(data));
                        } else {
                            eventBus.post(new FetchedProgramListFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Program>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromAPI() {
        jobManager.addJobInBackground(new FetchProgramListJob());
    }

    @Override
    public boolean isCacheValid() {
        try {
            return programDao.isCacheValid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}