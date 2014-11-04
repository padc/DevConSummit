package ph.devcon.android.speaker.service;

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
import ph.devcon.android.base.db.OrmliteListLoaderSupport;
import ph.devcon.android.category.db.Category;
import ph.devcon.android.category.db.CategoryDao;
import ph.devcon.android.speaker.api.SpeakerAPI;
import ph.devcon.android.speaker.api.SpeakerAPIContainer;
import ph.devcon.android.speaker.api.SpeakerBaseResponse;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.db.SpeakerDao;
import ph.devcon.android.speaker.db.Talk;
import ph.devcon.android.speaker.db.TalkDao;
import ph.devcon.android.speaker.event.FetchedAllSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedPanelSpeakerListEvent;
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

    TalkDao talkDao;

    CategoryDao categoryDao;

    public SpeakerServiceImpl(Context context, JobManager jobManager, EventBus eventBus, SpeakerDao speakerDao, TalkDao talkDao, CategoryDao categoryDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.speakerDao = speakerDao;
        this.talkDao = talkDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Speaker> createCacheObject(SpeakerBaseResponse baseResponse) {
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
                ForeignCollection<Category> categories = speakerDao.getEmptyForeignCollection("categories");
                ForeignCollection<Talk> talks = speakerDao.getEmptyForeignCollection("talks");
                Speaker speakerDb = Speaker.toSpeaker(speakerAPI);
                speakerDb.setCategories(categories);
                speakerDb.setTalks(talks);
                for (String categoryName : speakerAPI.getCategory()) {
                    Category categoryDb = Category.toCategory(categoryName);
                    categoryDb.setSpeaker(speakerDb);
                    categories.add(categoryDb);
                }
                for (String talkName : speakerAPI.getTalk()) {
                    Talk talkDb = Talk.toTalk(talkName);
                    talkDb.setSpeaker(speakerDb);
                    talks.add(talkDb);
                }
                speakerDao.create(speakerDb);
                speakerDBList.add(speakerDb);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return speakerDBList;
    }

    @Override
    public void getAll(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState, new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Speaker>>() {
            @Override
            public android.support.v4.content.Loader<List<Speaker>> onCreateLoader(int i, Bundle bundle) {
                try {
                    return new OrmliteListLoaderSupport(context, speakerDao, speakerDao.getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<List<Speaker>> loader, List<Speaker> data) {
                if (data != null) {
                    eventBus.postSticky(new FetchedAllSpeakerListEvent(data));
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
    public void getSpeakers(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState, new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Speaker>>() {
            @Override
            public android.support.v4.content.Loader<List<Speaker>> onCreateLoader(int i, Bundle bundle) {
                try {
                    return new OrmliteListLoaderSupport(context, speakerDao, speakerDao.getSpeakers());
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
    public void getPanels(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState, new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Speaker>>() {
            @Override
            public android.support.v4.content.Loader<List<Speaker>> onCreateLoader(int i, Bundle bundle) {
                try {
                    return new OrmliteListLoaderSupport(context, speakerDao, speakerDao.getPanels());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<List<Speaker>> loader, List<Speaker> data) {
                if (data != null) {
                    eventBus.postSticky(new FetchedPanelSpeakerListEvent(data));
                } else {
                    eventBus.postSticky(new FetchedSpeakerListFailedEvent());
                }
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<List<Speaker>> objectLoader) {
            }
        });
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
    public Speaker getSpeaker(int id) {
        try {
            return speakerDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
