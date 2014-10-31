package ph.devcon.android.attendee.service;

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
import ph.devcon.android.attendee.api.AttendeeAPI;
import ph.devcon.android.attendee.api.AttendeeBaseResponse;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.attendee.db.AttendeeDao;
import ph.devcon.android.attendee.event.FetchedAttendeeListEvent;
import ph.devcon.android.attendee.event.FetchedAttendeeListFailedEvent;
import ph.devcon.android.attendee.job.FetchAttendeeListJob;
import ph.devcon.android.base.db.OrmliteListLoader;

/**
 * Created by lope on 10/29/14.
 */
public class AttendeeServiceImpl implements AttendeeService {

    AttendeeDao attendeeDao;

    JobManager jobManager;

    EventBus eventBus;

    Context context;

    public AttendeeServiceImpl(Context context, JobManager jobManager, EventBus eventBus, AttendeeDao attendeeDao) {
        this.context = context;
        this.jobManager = jobManager;
        this.eventBus = eventBus;
        this.attendeeDao = attendeeDao;
    }

    @Override
    public List<Attendee> createCacheObjects(AttendeeBaseResponse baseResponse) {
        try {
            attendeeDao.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<AttendeeBaseResponse> baseResponseOptional = Optional.of(baseResponse);
        AttendeeBaseResponse attendeeBaseResponse = baseResponseOptional.get();
        List<Attendee> attendeeDbList = new ArrayList<Attendee>();
        for (AttendeeAPI attendeeAPI : attendeeBaseResponse.getAttendees()) {
            try {
                Attendee attendeeDb = Attendee.toAttendee(attendeeAPI);
                attendeeDao.create(attendeeDb);
                attendeeDbList.add(attendeeDb);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return attendeeDbList;
    }

    public void populateFromCache(LoaderManager loaderManager, Bundle savedInstanceState) {
        loaderManager.initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<List<Attendee>>() {
                    @Override
                    public Loader<List<Attendee>> onCreateLoader(int id, Bundle args) {
                        try {
                            return new OrmliteListLoader(context, attendeeDao, attendeeDao.queryBuilder().prepare());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Attendee>> loader, List<Attendee> data) {
                        if (data != null) {
                            eventBus.post(new FetchedAttendeeListEvent(data));
                        } else {
                            eventBus.post(new FetchedAttendeeListFailedEvent());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Attendee>> loader) {
                    }
                }
        );
    }

    @Override
    public void populateFromAPI() {
        jobManager.addJobInBackground(new FetchAttendeeListJob());
    }

    @Override
    public boolean isCacheValid() {
        try {
            return attendeeDao.isCacheValid();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}