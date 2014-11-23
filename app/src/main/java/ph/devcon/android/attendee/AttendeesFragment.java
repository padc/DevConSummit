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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.util.AsyncExecutor;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.attendee.adapter.AttendeeCursorAdapter;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.attendee.db.FTSAttendee;
import ph.devcon.android.attendee.event.FetchedAttendeeListEvent;
import ph.devcon.android.attendee.event.SearchedAttendeeListEvent;
import ph.devcon.android.attendee.service.AttendeeService;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.navigation.MainActivity;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    static String lastQuery = "";

    @InjectView(R.id.lvw_attendee)
    ListView lvwAttendee;

    @Inject
    AttendeeService attendeeService;

    @Inject
    EventBus eventBus;

    @Inject
    FTSAttendee ftsAttendee;

    @InjectView(R.id.cont_attendee)
    SwipeRefreshLayout swipeLayout;

    AttendeeCursorAdapter attendeeAdapter;

    AlphaInAnimationAdapter animationAdapter;

    @OnItemClick(R.id.lvw_attendee)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), AttendeeDetailsActivity.class);
        intent.putExtra(AttendeeDetailsActivity.POSITION, position);
        intent.putIntegerArrayListExtra(AttendeeDetailsActivity.ID_ITEMS, getIds());
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Util.isNullOrEmpty(lastQuery))
            search(lastQuery);
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
        initSwipeLayout();
        initAnimation();
        if (attendeeService.isCacheValid()) {
            attendeeService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            attendeeService.populateFromAPI();
            new AlertDialog.Builder(getActivity())
                    .setTitle("Whoa!")
                    .setMessage("We've got a lot of attendees." +
                            " Lemme just get them for you this one time!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.ic_launcher)
                    .show();
        }
        return rootView;
    }

    public void search(final String query) {
        AsyncExecutor.Builder builder = AsyncExecutor.builder();
        AsyncExecutor executor = builder.build();
        executor.execute(
                new AsyncExecutor.RunnableEx() {
                    @Override
                    public void run() throws Exception {
                        Cursor cursor = ftsAttendee.search(query);
                        EventBus.getDefault().post(new SearchedAttendeeListEvent(cursor));
                    }
                }
        );
    }

    public void displayAll() {
        AsyncExecutor.Builder builder = AsyncExecutor.builder();
        AsyncExecutor executor = builder.build();
        executor.execute(
                new AsyncExecutor.RunnableEx() {
                    @Override
                    public void run() throws Exception {
                        Cursor cursor = ftsAttendee.queryForAll();
                        EventBus.getDefault().post(new SearchedAttendeeListEvent(cursor));
                    }
                }
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.attendee, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(Html.fromHtml("<font color = #808080>Find by name, address, company, etc..</font>"));
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextColor(Color.DKGRAY);
                searchText.setHintTextColor(Color.DKGRAY);
            }
        }
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                lastQuery = s;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!Util.isNullOrEmpty(s)) {
                    search(s);
                    lastQuery = s;
                }
                return false;
            }
        });
    }

    public void onEventMainThread(SearchedAttendeeListEvent event) {
        attendeeAdapter.changeCursor(event.attendees);
        attendeeAdapter.notifyDataSetChanged();
    }

    protected void initAnimation() {
        attendeeAdapter = new AttendeeCursorAdapter(getActivity(), null, SimpleCursorAdapter.NO_SELECTION);
        animationAdapter = new AlphaInAnimationAdapter(attendeeAdapter);
        animationAdapter.setAbsListView(lvwAttendee);
        lvwAttendee.setAdapter(animationAdapter);
    }

    protected void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.yellow,
                R.color.orange,
                R.color.purple,
                R.color.blue);
        swipeLayout.setRefreshing(true);
    }

    public ArrayList<Integer> getIds() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        Cursor c = attendeeAdapter.getCursor();
        if (Optional.fromNullable(c).isPresent()) {
            Integer ID_INDEX = c.getColumnIndex(FTSAttendee.COL_ID);
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);
                Integer id = Integer.valueOf(c.getString(ID_INDEX));
                ids.add(id);
            }
        }
        return ids;
    }

    public void setAttendeeList(List<Attendee> attendeeList) {
        if (attendeeList != null) {
            displayAll();
        }
        if (lvwAttendee.getFooterViewsCount() == 0)
            lvwAttendee.addFooterView(buildFooterView(getLayoutInflater(getArguments())));
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