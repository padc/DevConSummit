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

package ph.devcon.android.speaker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.speaker.SpeakerDetailsActivity;
import ph.devcon.android.speaker.adapter.SpeakerAdapter;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.event.FetchedAllSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListFailedEvent;
import ph.devcon.android.speaker.service.SpeakerService;

/**
 * Created by lope on 9/29/14.
 */
public abstract class BaseSpeakerFragment extends Fragment {
    @InjectView(R.id.lvw_speakers)
    ListView lvwSpeaker;

    @InjectView(R.id.pbr_loading)
    ProgressBar pbrLoading;

    @Inject
    SpeakerService speakerService;

    @Inject
    EventBus eventBus;

    SpeakerAdapter speakerAdapter;

    @OnItemClick(R.id.lvw_speakers)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), SpeakerDetailsActivity.class);
        intent.putExtra(SpeakerDetailsActivity.POSITION, position);
        eventBus.postSticky(new FetchedSpeakerListEvent(speakerAdapter.getItems()));
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speakers_all, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        if (!eventBus.isRegistered(this)) {
            eventBus.registerSticky(this);
        }
        lvwSpeaker.addFooterView(buildFooterView(inflater));
        if (speakerService.isCacheValid()) {
            executePopulateFromCache(savedInstanceState);
        } else {
            executePopulateFromAPI();
        }
        lvwSpeaker.setEmptyView(pbrLoading);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected abstract void executePopulateFromCache(Bundle savedInstanceStat);

    protected abstract void executePopulateFromAPI();

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
        eventBus.removeStickyEvent(FetchedAllSpeakerListEvent.class);
        eventBus.removeStickyEvent(FetchedSpeakerListFailedEvent.class);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void setSpeakerList(List<Speaker> speakerList) {
        if (speakerList != null && !speakerList.isEmpty()) {
            speakerAdapter = new SpeakerAdapter(getActivity(), speakerList, shouldDisplayTalkAsTitle());
            lvwSpeaker.setAdapter(speakerAdapter);
        }
    }

    public abstract boolean shouldDisplayTalkAsTitle();

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}
