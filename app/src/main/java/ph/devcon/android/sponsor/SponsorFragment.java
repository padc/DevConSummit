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

package ph.devcon.android.sponsor;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ArrayListMultimap;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.navigation.MainActivity;
import ph.devcon.android.sponsor.adapter.SponsorSectionAdapter;
import ph.devcon.android.sponsor.db.Sponsor;
import ph.devcon.android.sponsor.event.FetchedSponsorListEvent;
import ph.devcon.android.sponsor.service.SponsorService;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by lope on 9/16/14.
 */
public class SponsorFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.cont_sponsors)
    SwipeRefreshLayout swipeLayout;

    @InjectView(R.id.lvw_sponsors)
    StickyListHeadersListView lvwSponsors;

    @Inject
    EventBus eventBus;

    @Inject
    SponsorService sponsorService;

    SponsorSectionAdapter sponsorSectionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        initSwipeLayout();
        ArrayListMultimap<String, Sponsor> sponsorMultimap = ArrayListMultimap.create();
        sponsorSectionAdapter = new SponsorSectionAdapter(getActivity(), sponsorMultimap);
        lvwSponsors.setAdapter(sponsorSectionAdapter);
        if (sponsorService.isCacheValid()) {
            sponsorService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            sponsorService.populateFromAPI();
        }
        return rootView;
    }

    protected void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.yellow,
                R.color.orange,
                R.color.purple,
                R.color.blue);
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    public void setSponsorList(List<Sponsor> sponsorList) {
        if (sponsorList != null) {
            sponsorSectionAdapter.setItems(sponsorService.buildMultimap(sponsorList));
            sponsorSectionAdapter.notifyDataSetChanged();
        }
        swipeLayout.setRefreshing(false);
    }

    public void onEventMainThread(FetchedSponsorListEvent event) {
        setSponsorList(event.sponsors);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(BaseDevConActivity.PlaceholderFragment.ARG_SECTION_NUMBER));
    }

    @Override
    public void onRefresh() {
        sponsorService.populateFromAPI();
    }
}