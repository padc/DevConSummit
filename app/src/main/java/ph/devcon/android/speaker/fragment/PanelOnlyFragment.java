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
