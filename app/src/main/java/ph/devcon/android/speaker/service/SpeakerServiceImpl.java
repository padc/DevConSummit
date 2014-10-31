package ph.devcon.android.speaker.service;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.google.common.base.Optional;
import com.path.android.jobqueue.JobManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.base.db.OrmliteListLoaderSupport;
import ph.devcon.android.speaker.api.SpeakerAPI;
import ph.devcon.android.speaker.api.SpeakerAPIContainer;
import ph.devcon.android.speaker.api.SpeakerBaseResponse;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.db.SpeakerDao;
import ph.devcon.android.speaker.event.FetchedSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListFailedEvent;
import ph.devcon.android.speaker.job.FetchSpeakerJob;

/**
 * Created by lope on 10/6/14.
 */
public class SpeakerServiceImpl implements SpeakerService {

    Context context;

    JobManager jobManager;

    EventBus eventBus;

    SpeakerDao speakerDao;

    public SpeakerServiceImpl(Context context, JobManager jobManager, EventBus eventBus, SpeakerDao speakerDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.speakerDao = speakerDao;
    }

    @Override
    public List<Speaker> createSpeakers(SpeakerBaseResponse baseResponse) {
        try {
            speakerDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<SpeakerBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        SpeakerBaseResponse programBaseResponse = baseResponseOptional.get();
        List<Speaker> speakerDBList = new ArrayList<Speaker>();
        for (SpeakerAPIContainer container : programBaseResponse.getSpeakers()) {
            try {
                SpeakerAPI speakerAPI = container.getSpeaker();
                Speaker speakerDb = Speaker.toSpeaker(speakerAPI);
                speakerDao.create(speakerDb);
                speakerDBList.add(speakerDb);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return speakerDBList;
    }

    @Override
    public List<Speaker> getAll() {
        return null;
    }

    @Override
    public List<Speaker> getSpeakers() {
        return null;
    }

    @Override
    public List<Speaker> getPanels() {
        return null;
    }

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<Speaker>>() {
                    @Override
                    public Loader<List<Speaker>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(context, speakerDao, speakerDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Speaker>> loader, List<Speaker> data) {
                        if (data != null) {
                            eventBus.postSticky(new FetchedSpeakerListEvent(data));
                        } else {
                            eventBus.postSticky(new FetchedSpeakerListFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Speaker>> loader) {
                    }
                }
        );
    }

    /**
     * to slowly move to supporting older versions
     *
     * @param loaderManager
     * @param savedInstanceState
     */
    @Override
    public void populateFromCache(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState, new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Speaker>>() {
            @Override
            public android.support.v4.content.Loader<List<Speaker>> onCreateLoader(int i, Bundle bundle) {
                try {
                    return new OrmliteListLoaderSupport(context, speakerDao, speakerDao.queryBuilder().prepare());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<List<Speaker>> loader, List<Speaker> data) {
                if (data != null) {
                    eventBus.postSticky(new FetchedSpeakerListEvent(data));
                } else {
                    eventBus.postSticky(new FetchedSpeakerListFailedEvent());
                }
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<List<Speaker>> objectLoader) {
            }
        });
    }

    @Override
    public void populateFromAPI() {
        jobManager.addJobInBackground(new FetchSpeakerJob());
    }

    @Override
    public boolean isCacheValid() {
        try {
            return speakerDao.isCacheValid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
