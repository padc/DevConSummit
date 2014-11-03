package ph.devcon.android.attendee;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.attendee.adapter.AttendeeAdapter;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.attendee.event.FetchedAttendeeListEvent;
import ph.devcon.android.attendee.service.AttendeeService;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeesFragment extends Fragment {

    @InjectView(R.id.lvw_attendee)
    ListView lvwAttendee;

    @Inject
    AttendeeService attendeeService;

    @Inject
    EventBus eventBus;

    @OnItemClick(R.id.lvw_attendee)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AttendeeDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attendee, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        if (attendeeService.isCacheValid()) {
            attendeeService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            attendeeService.populateFromAPI();
        }
        lvwAttendee.addFooterView(buildFooterView(inflater));
        return rootView;
    }

    public void setAttendeeList(List<Attendee> attendeeList) {
        if (attendeeList != null && !attendeeList.isEmpty()) {
            lvwAttendee.setAdapter(new AttendeeAdapter(getActivity(), attendeeList));
        }
    }

    public void onEventMainThread(FetchedAttendeeListEvent event) {
        setAttendeeList(event.attendees);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}