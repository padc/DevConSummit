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

import android.os.Bundle;

import com.google.common.base.Optional;

import ph.devcon.android.speaker.event.FetchedAllSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedPanelSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListFailedEvent;

/**
 * Created by lope on 9/29/14.
 */
public class PanelOnlyFragment extends BaseSpeakerFragment {

    @Override
    public void onResume() {
        super.onResume();
        FetchedPanelSpeakerListEvent event = eventBus.getStickyEvent(FetchedPanelSpeakerListEvent.class);
        Optional<FetchedPanelSpeakerListEvent> eventOptional = Optional.fromNullable(event);
        if (eventOptional.isPresent()) {
            setSpeakerList(eventOptional.get().speakers);
        }
    }

    @Override
    protected void executePopulateFromCache(Bundle savedInstanceState) {
        speakerService.getPanels(getLoaderManager(), savedInstanceState);
    }

    @Override
    protected void executePopulateFromAPI() {
        speakerService.populateFromAPI();
    }

    public void onEventMainThread(FetchedAllSpeakerListEvent event) {
        speakerService.getPanels(getLoaderManager(), getArguments());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
        eventBus.removeStickyEvent(FetchedPanelSpeakerListEvent.class);
        eventBus.removeStickyEvent(FetchedSpeakerListFailedEvent.class);
    }

    @Override
    public boolean shouldDisplayTalkAsTitle() {
        return false;
    }

    public void onEventMainThread(FetchedPanelSpeakerListEvent event) {
        setSpeakerList(event.speakers);
    }

}
