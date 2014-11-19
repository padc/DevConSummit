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

package ph.devcon.android.attendee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.ArrayList;
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
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.navigation.MainActivity;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.lvw_attendee)
    ListView lvwAttendee;

    @Inject
    AttendeeService attendeeService;

    @Inject
    EventBus eventBus;

    @InjectView(R.id.cont_attendee)
    SwipeRefreshLayout swipeLayout;

    AttendeeAdapter attendeeAdapter;

    AlphaInAnimationAdapter animationAdapter;

    @OnItemClick(R.id.lvw_attendee)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AttendeeDetailsActivity.class);
        intent.putExtra(AttendeeDetailsActivity.POSITION, position);
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
        List<Attendee> attendeeList = new ArrayList<Attendee>();
        attendeeAdapter = new AttendeeAdapter(getActivity(), attendeeList);
        if (attendeeService.isCacheValid()) {
            attendeeService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            attendeeService.populateFromAPI();
        }
        lvwAttendee.addFooterView(buildFooterView(inflater));
        animationAdapter = new AlphaInAnimationAdapter(attendeeAdapter);
        animationAdapter.setAbsListView(lvwAttendee);
        lvwAttendee.setAdapter(animationAdapter);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.yellow,
                R.color.orange,
                R.color.purple,
                R.color.blue);
        swipeLayout.setRefreshing(true);
        return rootView;
    }

    public void setAttendeeList(List<Attendee> attendeeList) {
        if (attendeeList != null) {
            attendeeAdapter.setItems(attendeeList);
            attendeeAdapter.notifyDataSetChanged();
        }
        swipeLayout.setRefreshing(false);
    }

    public void onEventMainThread(FetchedAttendeeListEvent event) {
        setAttendeeList(event.attendees);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(BaseDevConActivity.PlaceholderFragment.ARG_SECTION_NUMBER));
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

    @Override
    public void onRefresh() {
        attendeeService.populateFromAPI();
    }
}