package ph.devcon.android.speaker.fragment;

import android.os.Bundle;

import com.google.common.base.Optional;

import ph.devcon.android.speaker.event.FetchedAllSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListEvent;
import ph.devcon.android.speaker.event.FetchedSpeakerListFailedEvent;

/**
 * Created by lope on 9/29/14.
 */
public class SpeakerOnlyFragment extends BaseSpeakerFragment {

    @Override
    public void onResume() {
        super.onResume();
        FetchedSpeakerListEvent event = eventBus.getStickyEvent(FetchedSpeakerListEvent.class);
        Optional<FetchedSpeakerListEvent> eventOptional = Optional.fromNullable(event);
        if (eventOptional.isPresent()) {
            setSpeakerList(eventOptional.get().speakers);
        }
    }

    @Override
    protected void executePopulateFromCache(Bundle savedInstanceState) {
        speakerService.getSpeakers(getLoaderManager(), savedInstanceState);
    }

    @Override
    protected void executePopulateFromAPI() {
        speakerService.populateFromAPI();
    }

    public void onEventMainThread(FetchedAllSpeakerListEvent event) {
        speakerService.getSpeakers(getLoaderManager(), getArguments());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
        eventBus.removeStickyEvent(FetchedSpeakerListEvent.class);
        eventBus.removeStickyEvent(FetchedSpeakerListFailedEvent.class);
    }

    @Override
    public boolean shouldDisplayTalkAsTitle() {
        return true;
    }

    public void onEventMainThread(FetchedSpeakerListEvent event) {
        setSpeakerList(event.speakers);
    }

}
