package ph.devcon.android.sponsor.service;

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
import ph.devcon.android.news.job.FetchNewsJob;
import ph.devcon.android.sponsor.api.SponsorAPI;
import ph.devcon.android.sponsor.api.SponsorBaseResponse;
import ph.devcon.android.sponsor.api.SponsorContainer;
import ph.devcon.android.sponsor.db.Sponsor;
import ph.devcon.android.sponsor.db.SponsorDao;
import ph.devcon.android.sponsor.event.FetchedSponsorListEvent;
import ph.devcon.android.sponsor.event.FetchedSponsorListFailedEvent;

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
    public List<Sponsor> createCacheObject(SponsorBaseResponse baseResponse) {
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
    public void populateFromAPI() {
        jobManager.addJobInBackground(new FetchNewsJob());
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
}
