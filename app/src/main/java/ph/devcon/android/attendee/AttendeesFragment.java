package ph.devcon.android.attendee;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import ph.devcon.android.R;
import ph.devcon.android.attendee.adapter.AttendeeAdapter;
import ph.devcon.android.attendee.db.Attendee;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeesFragment extends Fragment {

    @InjectView(R.id.lvw_attendee)
    ListView lvwAttendee;

    @OnItemClick(R.id.lvw_attendee)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AttendeeDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attendee, container, false);
        ButterKnife.inject(this, rootView);
        List<Attendee> attendeeList = new ArrayList<Attendee>();
        attendeeList.add(new Attendee());
        attendeeList.add(new Attendee());
        attendeeList.add(new Attendee());
        attendeeList.add(new Attendee());
        attendeeList.add(new Attendee());
        attendeeList.add(new Attendee());
        lvwAttendee.setAdapter(new AttendeeAdapter(getActivity(), attendeeList));
        lvwAttendee.addFooterView(buildFooterView(inflater));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}