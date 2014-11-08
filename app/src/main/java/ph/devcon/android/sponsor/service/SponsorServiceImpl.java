package ph.devcon.android.sponsor.service;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.path.android.jobqueue.JobManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ph.devcon.android.base.db.OrmliteListLoader;
import ph.devcon.android.base.db.OrmliteListLoaderSupport;
import ph.devcon.android.sponsor.api.SponsorAPI;
import ph.devcon.android.sponsor.api.SponsorBaseResponse;
import ph.devcon.android.sponsor.api.SponsorContainer;
import ph.devcon.android.sponsor.api.SponsorTypeAPI;
import ph.devcon.android.sponsor.db.Sponsor;
import ph.devcon.android.sponsor.db.SponsorDao;
import ph.devcon.android.sponsor.db.SponsorType;
import ph.devcon.android.sponsor.event.FetchedSponsorListEvent;
import ph.devcon.android.sponsor.event.FetchedSponsorListFailedEvent;
import ph.devcon.android.sponsor.job.FetchSponsorJob;

/**
 * Created by lope on 10/6/14.
 */
public class SponsorServiceImpl implements SponsorService {

    Context context;

    JobManager jobManager;

    EventBus eventBus;

    SponsorDao sponsorDao;

    public SponsorServiceImpl(Context context, JobManager jobManager, EventBus eventBus, SponsorDao sponsorDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.sponsorDao = sponsorDao;
    }

    @Override
    public synchronized List<Sponsor> createCacheObject(SponsorBaseResponse baseResponse) {
        try {
            sponsorDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<SponsorBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        SponsorBaseResponse sponsorBaseResponse = baseResponseOptional.get();
        List<Sponsor> sponsorDBList = new ArrayList<Sponsor>();
        for (SponsorContainer container : sponsorBaseResponse.getSponsors()) {
            try {
                Optional<SponsorAPI> sponsorAPIOptional = Optional.fromNullable(container.getSponsor());
                if (sponsorAPIOptional.isPresent()) {
                    SponsorAPI sponsorAPI = sponsorAPIOptional.get();
                    Sponsor sponsorDb = Sponsor.toSponsor(sponsorAPI);
                    Optional<SponsorTypeAPI> sponsorTypeAPIOptional = Optional.fromNullable(sponsorAPI.getCategory());
                    if (sponsorTypeAPIOptional.isPresent()) {
                        SponsorType sponsorType = SponsorType.toSponsorType(sponsorTypeAPIOptional.get());
                        sponsorDb.setSponsorType(sponsorType);
                    }
                    sponsorDao.create(sponsorDb);
                    sponsorDBList.add(sponsorDb);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sponsorDBList;
    }

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<Sponsor>>() {
                    @Override
                    public Loader<List<Sponsor>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(context, sponsorDao, sponsorDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Sponsor>> loader, List<Sponsor> data) {
                        if (data != null) {
                            eventBus.postSticky(new FetchedSponsorListEvent(data));
                        } else {
                            eventBus.postSticky(new FetchedSponsorListFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Sponsor>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromCache(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new android.support.v4.app.LoaderManager.LoaderCallbacks<List<Sponsor>>() {
                    @Override
                    public android.support.v4.content.Loader<List<Sponsor>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoaderSupport<Sponsor, Integer>(context, sponsorDao, sponsorDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(android.support.v4.content.Loader<List<Sponsor>> loader, List<Sponsor> data) {
                        if (data != null) {
                            eventBus.postSticky(new FetchedSponsorListEvent(data));
                        } else {
                            eventBus.postSticky(new FetchedSponsorListFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(android.support.v4.content.Loader<List<Sponsor>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromAPI() {
        jobManager.addJobInBackground(new FetchSponsorJob());
    }

    @Override
    public boolean isCacheValid() {
        try {
            return sponsorDao.isCacheValid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayListMultimap<String, Sponsor> buildMultimap(List<Sponsor> sponsors) {
        ArrayListMultimap<String, Sponsor> sponsorMultimap = ArrayListMultimap.create();
        for (Sponsor sponsor : sponsors) {
            Optional<SponsorType> sponsorTypeOptional = Optional.fromNullable(sponsor.getSponsorType());
            if (sponsorTypeOptional.isPresent()) {
                SponsorType sponsorType = sponsorTypeOptional.get();
                sponsorMultimap.put(sponsorType.getName(), sponsor);
            }
        }
        return sponsorMultimap;
    }
}
